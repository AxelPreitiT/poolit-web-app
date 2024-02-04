package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TripServiceImpl implements TripService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TripServiceImpl.class);

    private static final int OFFSET_MINUTES = 30;

    private static final double DEFAULT_EARNINGS = 0.0;

    private final EmailService emailService;

    private final TripDao tripDao;

    private final UserService userService;

    private final CityService cityService;

    private final CarService carService;

    @Autowired
    public TripServiceImpl(final TripDao tripDao, EmailService emailService, UserService userService,
                           CityService cityService, CarService carService){
        this.tripDao = tripDao;
        this.emailService = emailService;
        this.userService = userService;
        this.cityService = cityService;
        this.carService = carService;
    }

    @Transactional
    @Override
    public Trip createTrip(final long originCityId, final String originAddress, final long destinationCityId, final String destinationAddress, final long carId, final LocalDate startDate, final LocalTime startTime,final BigDecimal price, final int maxSeats, final LocalDate endDate, final LocalTime endTime)
    throws CityNotFoundException, CarNotFoundException, UserNotFoundException{
        final City originCity = cityService.findCityById(originCityId).orElseThrow(CityNotFoundException::new);
        final City destinationCity = cityService.findCityById(destinationCityId).orElseThrow(CityNotFoundException::new);
        final Car car = carService.findById(carId).orElseThrow(CarNotFoundException::new);
        final User driver = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        LocalDateTime startDateTime = startDate.atTime(startTime);
        //If Trip is not recurrent, then endDateTime is the same as startDateTime
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(endTime) : startDateTime;
        if(!startDateTime.getDayOfWeek().equals(endDateTime.getDayOfWeek())
        || startDateTime.isAfter(endDateTime)
        || originCity == null || destinationCity == null
        || car == null || driver == null
        || maxSeats<=0 || price.doubleValue()<0){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip with originCity with id {}, originAddress '{}', destinationCity with id {}, destinationAddress '{}', car with id {}, startDateTime '{}', endDateTime '{}', price ${}, maxSeats {} and driver with id {} has invalid values",
                    originCity.getId(), originAddress, destinationCity.getId(), destinationAddress, car.getCarId(), startDateTime, endDateTime, price, maxSeats, driver.getUserId(), e);
            throw e;
        }
        Trip newTrip = tripDao.create(
                originCity,
                originAddress,
                destinationCity,
                destinationAddress,
                car,
                startDateTime,
                endDateTime,
                !startDateTime.equals(endDateTime),
                price.doubleValue(),
                maxSeats,
                driver
        );
        emailService.sendMailNewTrip(newTrip);
        return newTrip;
    }
    private static void validatePageAndSize(int page, int pageSize){
        if(page<0 || pageSize<0) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Page {} and pageSize {} must be positive", page, pageSize, e);
            throw e;
        }
    }

    @Transactional
    @Override
    public boolean deleteTrip(final long tripId) throws TripNotFoundException{
        final Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        //Si el viaje ya termino o ya fue eliminado
        if(trip.getEndDateTime().isBefore(LocalDateTime.now()) || trip.isDeleted()){
            RuntimeException e = new IllegalStateException();
            LOGGER.error("Driver {} tried deleting the trip with id {} after it ended or has been deleted", trip.getDriver().getUserId(), trip.getTripId(), e);
            throw e;
        }
        List<Passenger> tripPassengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        //Si el viaje todavia no empezo
        if(!LocalDateTime.now().isAfter(trip.getStartDateTime())){
            for(Passenger passenger : tripPassengers){
                //Solo notifico a los pasajeros que siguen en el viaje despues de esa ultima fecha donde ocurrio
                emailService.sendMailTripDeletedToPassenger(trip, passenger);
                //Los marcamos como rechazados para el viaje
                tripDao.rejectPassenger(passenger);
            }
            emailService.sendMailTripDeletedToDriver(trip);
            //Marcamos el viaje como eliminado para ser consistentes siempre
            return tripDao.markTripAsDeleted(trip,null);
        }
        //El viaje ya empezo
        //Notificamos a los pasajeros que siguen en el viaje despues de eliminarse
        LocalDateTime lastOccurrence = LocalDate.now().atTime(trip.getStartDateTime().toLocalTime());
        //Voy a la ultima ocurrencia del viaje
        int i = 0; //seguridad, no deberia ser necesario
        while (!lastOccurrence.getDayOfWeek().equals(trip.getStartDateTime().getDayOfWeek()) && i<8){
            lastOccurrence = lastOccurrence.minusDays(1);
            i++;
        }
        for(Passenger passenger : tripPassengers){
            //Solo notifico a los pasajeros que siguen en el viaje despues de esa ultima fecha donde ocurrio
            if(passenger.getEndDateTime().isAfter(lastOccurrence)) {
                //El pasajero terminaba después de la última fecha
                if(!passenger.getStartDateTime().isAfter(lastOccurrence)){
                    //El pasajero ya habia empezado su periodo -> el viaje tiene que quedar en el historial
                    emailService.sendMailTripTruncatedToPassenger(trip, passenger, lastOccurrence.plusDays(7));
                    tripDao.truncatePassengerEndDateTime(passenger,lastOccurrence);
                }else{
                    //El pasajero no habia empezado su periodo -> se lo marca como rechazado para el historial
                    //Se cancelo la totalidad del viaje que ocupaba
                    emailService.sendMailTripDeletedToPassenger(trip, passenger);
                    //Lo sacamos de los pasajeros para
                    tripDao.rejectPassenger(passenger);
                }

            }
        }
        emailService.sendMailTripDeletedToDriver(trip);
        return tripDao.markTripAsDeleted(trip, lastOccurrence);
    }

    @Transactional
    @Override
    public Passenger addCurrentUserAsPassenger(final long tripId, LocalDate startDate, LocalDate endDate) throws TripAlreadyStartedException, TripNotFoundException, UserNotFoundException, NotAvailableSeatsException {
        final Trip trip = tripDao.findById(tripId).orElseThrow(TripNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final LocalTime tripTime = trip.getStartDateTime().toLocalTime();
        final LocalDateTime startDateTime = startDate.atTime(tripTime);
        final LocalDateTime endDateTime = endDate==null?startDateTime:endDate.atTime(tripTime);
        if(trip==null || user==null || startDateTime == null || endDateTime == null){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip {} or User {} or startDateTime '{}' or endDateTime '{}' cannot be null", trip, user, startDateTime, endDateTime, e);
            throw e;
        }
        Passenger passenger = new Passenger(user,trip,startDateTime,endDateTime);
        List<Passenger> passengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        if(passengers.contains(passenger)){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} is already in trip with id {}", passenger.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(tripDao.getTripSeatCount(trip.getTripId(),startDateTime,endDateTime)>=trip.getMaxSeats()){
            NotAvailableSeatsException e = new NotAvailableSeatsException();
            LOGGER.error("Trip with id {} is full", trip.getTripId(), e);
            throw e;
        }
        if(startDateTime.isBefore(LocalDateTime.now())){
            TripAlreadyStartedException e = new TripAlreadyStartedException();
            LOGGER.error("Trip with id {} already started", trip.getTripId(), e);
            throw e;
        }
        if(     startDateTime.isAfter(endDateTime) || trip.getStartDateTime().isAfter(startDateTime)
                || trip.getEndDateTime().isBefore(endDateTime) || !trip.getStartDateTime().getDayOfWeek().equals(startDateTime.getDayOfWeek())
                || !trip.getEndDateTime().getDayOfWeek().equals(endDateTime.getDayOfWeek()) || endDateTime.isBefore(startDateTime)
                || trip.getDriver().equals(user)
                || !startDateTime.toLocalTime().equals(trip.getStartDateTime().toLocalTime()) || !endDateTime.toLocalTime().equals(trip.getEndDateTime().toLocalTime())){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("{}, startDateTime '{}' or endDateTime '{}' have invalid values", trip, startDateTime, endDateTime, e);
            throw e;
        }
        emailService.sendMailNewPassengerRequest(trip, passenger);
        emailService.sendMailTripRequest(trip, passenger);
        return tripDao.addPassenger(trip,user,startDateTime,endDateTime);
    }



    @Transactional
    @Override
    public int getTripSeatCount(long tripId, LocalDateTime startDateTime, LocalDateTime endDateTime) throws TripNotFoundException {
        final Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        return tripDao.getTripSeatCount(tripId,
                startDateTime!=null?startDateTime:trip.getStartDateTime(),
                endDateTime!=null?endDateTime:trip.getEndDateTime());
    }

    @Transactional
    @Override
    public boolean removePassenger(final long tripId, final long userId) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        final User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        if(trip == null || user == null){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip {} or User {} cannot be null", trip, user, e);
            throw e;
        }
        final Optional<Passenger> passengerOptional = tripDao.getPassenger(trip,user);
        if(!passengerOptional.isPresent()) {//this will occur in multiple invocations of the method for the same passenger
            PassengerNotFoundException e = new PassengerNotFoundException();
            LOGGER.error("Passenger with id {} is not in trip with id {}", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(passengerOptional.get().isTripStarted()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to get out of trip {} after the period has started", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(passengerOptional.get().getPassengerState().equals(Passenger.PassengerState.REJECTED)){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to get out of trip {} after it has been rejected", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(passengerOptional.get().getEndDateTime().isBefore(LocalDateTime.now())){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to get out of trip {} after the period has ended", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        final Passenger passenger = passengerOptional.get();
        emailService.sendMailTripCancelledToDriver(trip,passenger);
        return tripDao.removePassenger(trip,passenger);
    }

    @Transactional
    @Override
    public Optional<Trip> findById(long id) {
        return tripDao.findById(id);
    }

    @Transactional
    @Override
    public Optional<Trip> findById(long id,LocalDateTime start, LocalDateTime end){
        return tripDao.findById(id,start,end);
    }

    @Transactional
    @Override
    public boolean userIsDriver(final long tripId, final User user){
        final Optional<Trip> trip = tripDao.findById(tripId);
        if(!trip.isPresent()){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Trip with id {} not found", tripId, e);
            throw e;
        }
        return trip.get().getDriver().equals(user);
    }

    @Transactional
    @Override
    public boolean userIsPassenger(final long tripId, final User user){
        return tripDao.getPassenger(tripId,user).isPresent();
    }

    @Transactional
    @Override
    public boolean userIsAcceptedPassengerOfDriver(final User user, final User driver){
        return tripDao.userIsAcceptedPassengerOfDriver(user,driver);
    }

    @Transactional
    @Override
    public double getTotalTripEarnings(final long tripId) throws TripNotFoundException{
        final Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        List<Passenger> acceptedPassengers = tripDao.getAcceptedPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        return acceptedPassengers.stream().map(Passenger::getTotalPrice).reduce(Double::sum).orElse(DEFAULT_EARNINGS);
    }

    @Transactional
    @Override
    public boolean checkIfUserCanGetPassengers(final long tripId, final User user, final LocalDateTime startDateTime, final LocalDateTime endDateTime, Passenger.PassengerState passengerState) throws TripNotFoundException {
        final Trip trip = tripDao.findById(tripId).orElseThrow(TripNotFoundException::new);
        if(trip.getDriver().getUserId() == user.getUserId()){
            //The trip driver can access all passengers
            return true;
        }
        Optional<Passenger> passenger = getPassenger(trip,user);
        if(!passenger.isPresent() || !passenger.get().getPassengerState().equals(Passenger.PassengerState.ACCEPTED)){
            return false;
        }
//        passengerState.equals(Passenger.PassengerState.ACCEPTED) && startDateTime.compareTo(passenger.get().getStartDateTime())>=0 && endDateTime.compareTo(passenger.get().getEndDateTime())<=0
        return passengerState.equals(Passenger.PassengerState.ACCEPTED) && !startDateTime.isBefore(passenger.get().getStartDateTime()) && !endDateTime.isAfter(passenger.get().getEndDateTime());
    }

    @Transactional
    @Override
    public Optional<Passenger> getPassenger(final long tripId, final long userId){
        final Optional<User> user = userService.findById(userId);
        if(!user.isPresent()){
            return Optional.empty();
        }
        return tripDao.getPassenger(tripId,user.get());
    }

    @Transactional
    @Override
    public Optional<Passenger> getPassenger(final Trip trip, final User user){
        return tripDao.getPassenger(trip,user);
    }

    @Transactional
    @Override
    public Optional<Passenger> getPassenger(final long tripId, final User user){
        return tripDao.getPassenger(tripId,user);
    }


    @Transactional
    @Override
    public PagedContent<Passenger> getPassengers(final long tripId, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final Passenger.PassengerState passengerState,final List<Integer> excludedList,final int page, final int pageSize) throws TripNotFoundException {
        validatePageAndSize(page,pageSize);
        final Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        if(startDateTime==null || endDateTime == null){
            return tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime(),Optional.ofNullable(passengerState),excludedList,page,pageSize);
        }
        return tripDao.getPassengers(trip,startDateTime,endDateTime,Optional.ofNullable(passengerState),excludedList,page,pageSize);
    }


    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByUserFuture(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.of(LocalDateTime.now()),Optional.empty(),true,page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByUser(final long userId, final boolean pastTrips, int page, int pageSize){
        final Optional<User> user = userService.findById(userId);
        if (!user.isPresent()){
            //avoid saying if a user exists or not
            return PagedContent.emptyPagedContent();
        }
        validatePageAndSize(page,pageSize);
        return pastTrips?tripDao.getTripsCreatedByUser(user.get(),Optional.empty(),Optional.of(LocalDateTime.now()),false,page,pageSize):tripDao.getTripsCreatedByUser(user.get(),Optional.of(LocalDateTime.now()),Optional.empty(),true,page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassenger(final long userId, final boolean pastTrips, int page, int pageSize){
        final Optional<User> user = userService.findById(userId);
        if (!user.isPresent()){
            //avoid saying if a user exists or not
            return PagedContent.emptyPagedContent();
        }
        validatePageAndSize(page,pageSize);
        return pastTrips?tripDao.getTripsWhereUserIsPassenger(user.get(),Optional.empty(), Optional.of(LocalDateTime.now()),null,false, page,pageSize):tripDao.getTripsWhereUserIsPassenger(user.get(), Optional.of(LocalDateTime.now()),Optional.empty(),null,true, page,pageSize);
    }


    @Transactional
    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassengerFuture(User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.of(LocalDateTime.now()),Optional.empty(), null,true, page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getRecommendedTripsForUser(final long userId, final int page, final int pageSize){
        validatePageAndSize(page,pageSize);
        Optional<User> user = userService.findById(userId);
        if(!user.isPresent()){
            return PagedContent.emptyPagedContent();
        }
        LocalDateTime start = LocalDateTime.now();
        return tripDao.getTripsByOriginAndStart(user.get().getBornCity().getId(),start,page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> findTrips(
            long originCityId, long destinationCityId, final LocalDateTime startDateTime,
            final LocalDateTime endDateTimeValue, final BigDecimal minPriceValue, final BigDecimal maxPriceValue,
            final Trip.SortType sortType, final boolean descending, final List<FeatureCar> carFeaturesValue,
            final int page, final int pageSize
            ){
        validatePageAndSize(page,pageSize);
        final Optional<BigDecimal> minPrice = Optional.ofNullable(minPriceValue);
        final Optional<BigDecimal> maxPrice = Optional.ofNullable(maxPriceValue);
        final LocalDateTime endDateTime = endDateTimeValue!=null?endDateTimeValue:startDateTime;
        final List<FeatureCar> carFeatures = carFeaturesValue!=null?carFeaturesValue: Collections.emptyList();
        return tripDao.getTripsWithFilters(originCityId,destinationCityId,startDateTime,startDateTime.getDayOfWeek(),endDateTime,OFFSET_MINUTES,minPrice,maxPrice,sortType,descending,carFeatures,page,pageSize);

    }

    private boolean acceptPassenger(final long tripId, final long userId) throws NotAvailableSeatsException {
        User user = userService.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
        Passenger pass = tripDao.getPassenger(tripId, user).orElseThrow(()->new IllegalArgumentException("Passenger not found"));
        if(LocalDateTime.now().compareTo(pass.getStartDateTime())>=0){
            throw new IllegalArgumentException();//no debe poder aceptar o rechazar a pasajeros cuyo perdiodo ya empezo;
        }
        if(tripDao.getTripSeatCount(tripId,pass.getStartDateTime(),pass.getEndDateTime())>=pass.getTrip().getMaxSeats()){
            //No hay asientos disponibles
            throw new NotAvailableSeatsException();
        }
        emailService.sendMailTripConfirmed(pass.getTrip(), pass);
        return tripDao.acceptPassenger(pass);
    }

    private boolean rejectPassenger(final long tripId, final long userId){
        User user = userService.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));
        Passenger passenger = tripDao.getPassenger(tripId, user).orElseThrow(()-> new IllegalArgumentException("Passanger not found"));
        if(LocalDateTime.now().compareTo(passenger.getStartDateTime())>=0){
            throw new IllegalStateException();//no debe poder aceptar o rechazar a pasajeros cuyo perdiodo ya empezo;
        }
        emailService.sendMailTripRejected(passenger.getTrip(), passenger);
        return tripDao.rejectPassenger(passenger);
    }

    @Transactional
    @Override
    public boolean acceptOrRejectPassenger(final long tripId, final long userId, Passenger.PassengerState passengerState) throws UserNotFoundException, PassengerNotFoundException, PassengerAlreadyProcessedException, NotAvailableSeatsException {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        Passenger passenger = tripDao.getPassenger(tripId,user).orElseThrow(PassengerNotFoundException::new);
        //Passenger was already accepted or rejected
        if(passenger.isRejected() || passenger.isAccepted()){
            throw new PassengerAlreadyProcessedException();
        }
        switch (passengerState){
            case PENDING: throw new IllegalArgumentException();
            case ACCEPTED: return acceptPassenger(tripId,userId);
            case REJECTED: return rejectPassenger(tripId,userId);
        }
        //Lamentablemente no podemos usar switch expressions
        return false;
    }

}
