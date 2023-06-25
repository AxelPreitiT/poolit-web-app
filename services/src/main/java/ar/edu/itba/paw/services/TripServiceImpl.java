package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    public TripServiceImpl(final TripDao tripDao, EmailService emailService1, UserService userService,
                           CityService cityService1, CarService carService1){
        this.tripDao = tripDao;
        this.emailService = emailService1;
        this.userService = userService;
        this.cityService = cityService1;
        this.carService = carService1;
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
        try {
            emailService.sendMailNewTrip(newTrip);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email for the new trip with id {} to the driver with id {}", newTrip.getTripId(), driver.getUserId(), e);
        }
        return newTrip;
    }
    private Optional<LocalDateTime> getLocalDateTime(final String date, final String time){
        if(date == null || time == null || date.length()==0 || time.length()==0){
            return Optional.empty();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime ans;
        try{
            String[] timeTokens = time.split(":");
            ans = LocalDate.parse(date, formatter).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        }catch (Exception e){
            LOGGER.error("Error parsing date '{}' and time '{}'", date, time, e);
            return Optional.empty();
        }
        LOGGER.debug("Parsed date '{}' and time '{}' to LocalDateTime '{}'", date, time, ans);
        return Optional.of(ans);
    }
    private Optional<LocalDateTime> getIsoLocalDateTime(final String date, final String time){
        if(date == null || time == null || date.length()==0 || time.length()==0){
            return Optional.empty();
        }
        LocalDateTime ans;
        try{
            String[] timeTokens = time.split(":");
            ans = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        }catch (Exception e){
            LOGGER.error("Error parsing date '{}' and time '{}'", date, time, e);
            return Optional.empty();
        }
        LOGGER.debug("Parsed date '{}' and time '{}' to LocalDateTime '{}'", date, time, ans);
        return Optional.of(ans);
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
        //Si el viaje ya termino
        if(trip.getEndDateTime().isBefore(LocalDateTime.now())){
            RuntimeException e = new IllegalStateException();
            LOGGER.error("Driver {} tried deleting the trip with id {} after it ended", trip.getDriver().getUserId(), trip.getTripId(), e);
            throw e;
        }
        List<Passenger> tripPassengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        //Si el viaje todavia no empezo
        if(!LocalDateTime.now().isAfter(trip.getStartDateTime())){
            for(Passenger passenger : tripPassengers){
                //Solo notifico a los pasajeros que siguen en el viaje despues de esa ultima fecha donde ocurrio
                try {
                    emailService.sendMailTripDeletedToPassenger(trip, passenger);
                } catch (Exception e) {
                    LOGGER.error("There was an error sending the email for the deleted trip with id {} to the passenger with id {}", trip.getTripId(), passenger.getUserId(), e);
                    throw new IllegalStateException();
                }
                //Lo sacamos del historial del pasajero, el viaje nunca ocurrio
                tripDao.removePassenger(trip,passenger);
            }
            try{
                emailService.sendMailTripDeletedToDriver(trip);
            }catch (Exception e){
                LOGGER.error("There was an error sending the email for the deleted trip with id {} to the driver with id {}", trip.getTripId(), trip.getDriver().getUserId(), e);
                throw new IllegalStateException();
            }
            //Directamente eliminamos al viaje de la BD, no va a tener reseñas asociadas
            return tripDao.deleteTrip(trip);
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
                //Si ya empezo su periodo en el viaje
                if(!passenger.getStartDateTime().isAfter(lastOccurrence)){
                    //El pasajero ya habia empezado su periodo -> el viaje tiene que quedar en el historial
                    try {
                        emailService.sendMailTripTruncatedToPassenger(trip, passenger, lastOccurrence.plusDays(7));
                    } catch (Exception e) {
                        LOGGER.error("There was an error sending the email for the deleted trip with id {} to the passenger with id {}", trip.getTripId(), passenger.getUserId(), e);
                        throw new IllegalStateException();
                    }
                    tripDao.truncatePassengerEndDateTime(passenger,lastOccurrence);
                }else{
                    //El pasajero no habia empezado su periodo -> el viaje se elemina del historial
                    try {
                        //Se cancelo la totalidad del viaje que ocupaba
                        emailService.sendMailTripDeletedToPassenger(trip, passenger);
                    } catch (Exception e) {
                        LOGGER.error("There was an error sending the email for the deleted trip with id {} to the passenger with id {}", trip.getTripId(), passenger.getUserId(), e);
                        throw new IllegalStateException();
                    }
                    //Lo sacamos de los pasajeros para
                    tripDao.removePassenger(trip,passenger);
                }

            }
        }
        try{
            emailService.sendMailTripDeletedToDriver(trip);
        }catch (Exception e){
            LOGGER.error("There was an error sending the email for the deleted trip with id {} to the driver with id {}", trip.getTripId(), trip.getDriver().getUserId(), e);
            throw new IllegalStateException();
        }
        return tripDao.markTripAsDeleted(trip, lastOccurrence);
    }

    @Transactional
    @Override
    public boolean addCurrentUser(final long tripId, String startDate, String startTime, String endDate) throws TripAlreadyStartedException, TripNotFoundException, UserNotFoundException {
        final Trip trip = tripDao.findById(tripId).orElseThrow(TripNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        LocalDateTime startDateTime = getIsoLocalDateTime(startDate,startTime).get();
        LocalDateTime endDateTime = getIsoLocalDateTime(endDate,startTime).orElse(startDateTime);
        if(trip==null || user==null || startDateTime == null || endDateTime == null){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip {} or User {} or startDateTime '{}' or endDateTime '{}' cannot be null", trip, user, startDateTime, endDateTime, e);
            throw e;
        }
        Passenger passenger = new Passenger(user,startDateTime,endDateTime);
        List<Passenger> passengers = tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        if(passengers.contains(passenger)){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} is already in trip with id {}", passenger.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(tripDao.getTripSeatCount(trip.getTripId(),startDateTime,endDateTime)>=trip.getMaxSeats()){
            IllegalStateException e = new IllegalStateException();
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
        try{
            emailService.sendMailNewPassengerRequest(trip, passenger);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email for the new passenger with id {} added to the trip with id {} to the driver with id {}", passenger.getUserId(), trip.getTripId(), trip.getDriver().getUserId(), e);
        }
        try {
            emailService.sendMailTripRequest(trip, passenger);
        }
        catch (Exception e) {
            LOGGER.error("There was an error sending the email for the new passenger with id {} added to the trip with id {} to the passenger with id {}", passenger.getUserId(), trip.getTripId(), passenger.getUserId(), e);
        }
        return tripDao.addPassenger(trip,user,startDateTime,endDateTime);
    }

    @Transactional
    @Override
    public boolean removeCurrentUserAsPassenger(final long tripId) throws UserNotFoundException, TripNotFoundException{
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return removePassenger(tripId, user.getUserId());
    }

    @Transactional
    @Override
    public boolean removePassenger(final long tripId, final long userId) throws UserNotFoundException, TripNotFoundException {
        Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        final User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        if(trip == null || user == null){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Trip {} or User {} cannot be null", trip, user, e);
            throw e;
        }
        final Optional<Passenger> passengerOptional = tripDao.getPassenger(trip,user);
        if(!passengerOptional.isPresent()) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} is not in trip with id {}", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(passengerOptional.get().getEndDateTime().isBefore(LocalDateTime.now())){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to get out of trip {} after the period has ended", user.getUserId(), trip.getTripId(), e);
            throw e;
        }
        final Passenger passenger = passengerOptional.get();
        try{
            emailService.sendMailTripCancelledToDriver(trip,passenger);
        }catch (Exception e){
            LOGGER.error("There was an error sending the email for the cancelled trip with id {} by the passenger with id {} to the driver with id {}", trip.getTripId(), passenger.getUserId(), trip.getDriver().getUserId(), e);
        }
        return tripDao.removePassenger(trip,passenger);
    }

    @Transactional
    @Override
    public Optional<Trip> findById(long id) {
        return tripDao.findById(id);
    }

    @Transactional
    @Override
    public Optional<Trip> findById(long id, String startDate, String startTime, String endDate){
        Optional<LocalDateTime> start = getIsoLocalDateTime(startDate,startTime);
        if(!start.isPresent()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("StartDate '{}' or startTime '{}' have invalid values", startDate, startTime, e);
            throw e;
        }
        Optional<LocalDateTime> end = getIsoLocalDateTime(endDate,startTime);
        if(!end.isPresent()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("EndDate '{}' or endTime '{}' have invalid values", endDate, startTime, e);
            throw e;
        }
        return findById(id,start.get(),end.get());
    }

    @Transactional
    @Override
    public Optional<Trip> findById(long id,LocalDateTime start, LocalDateTime end){
        return tripDao.findById(id,start,end);
    }

    @Transactional
    @Override
    public Optional<Trip> findById(long id, LocalDateTime dateTime){
        return tripDao.findById(id,dateTime,dateTime);
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
    public double getTotalTripEarnings(final long tripId) throws TripNotFoundException{
        final Trip trip = findById(tripId).orElseThrow(TripNotFoundException::new);
        List<Passenger> acceptedPassengers = getAcceptedPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        return acceptedPassengers.stream().map(Passenger::getTotalPrice).reduce(Double::sum).orElse(DEFAULT_EARNINGS);
    }

    @Transactional
    @Override
    public Optional<Passenger> getPassenger(final long tripId, final long userId) throws UserNotFoundException{
        final User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return tripDao.getPassenger(tripId,user);
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
    public List<Passenger> getPassengers(Trip trip, LocalDateTime dateTime){
        if( trip.getStartDateTime().isAfter(dateTime)
                || trip.getEndDateTime().isBefore(dateTime)
                || trip.getStartDateTime().until(dateTime, ChronoUnit.DAYS) % 7 != 0
                || dateTime.until(trip.getEndDateTime(), ChronoUnit.DAYS) % 7 != 0
        ){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("{} or dateTime '{}' have invalid values", trip, dateTime, e);
            throw e;
        }
        return tripDao.getPassengers(trip,dateTime);
    }

    @Transactional
    @Override
    public List<Passenger> getAcceptedPassengers(Trip trip, LocalDateTime startDate, LocalDateTime endDate){
        return tripDao.getAcceptedPassengers(trip,startDate,endDate);
    }

    @Transactional
    @Override
    public List<Passenger> getPassengersRecurrent(Trip trip, LocalDateTime startDate, LocalDateTime endDate){
        if( trip.getStartDateTime().isAfter(startDate)
                || trip.getEndDateTime().isBefore(endDate)
                || trip.getStartDateTime().until(startDate, ChronoUnit.DAYS) % 7 != 0
                || startDate.until(endDate, ChronoUnit.DAYS) % 7 != 0
        ){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("{} or startDate '{}' or endDate '{}' have invalid values", trip, startDate, endDate, e);
            throw e;
        }
        return tripDao.getPassengers(trip,startDate,endDate);
    }
    private Optional<Passenger.PassengerState> getPassengersState(String status){
        if(status.equals("accept")){
            return Optional.of(Passenger.PassengerState.ACCEPTED);
        }
        if(status.equals("waiting")){
            return Optional.of(Passenger.PassengerState.PENDING);
        }
        if(status.equals("reject")){
            return Optional.of(Passenger.PassengerState.REJECTED);
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public PagedContent<Passenger> getPassengersPaged(Trip trip, String passengerState, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime(),getPassengersState(passengerState),page,pageSize);
    }

    @Transactional
    @Override
    public List<Passenger> getPassengers(Trip trip){
        return tripDao.getPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
    }
    @Transactional
    @Override
    public List<Passenger> getPassengers(TripInstance tripInstance){
        return tripDao.getPassengers(tripInstance);
    }

    @Transactional
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripInstances(trip,page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end){
        validatePageAndSize(page,pageSize);
        if(start.isBefore(trip.getStartDateTime()) || end.isAfter(trip.getEndDateTime())
         || !start.getDayOfWeek().equals(end.getDayOfWeek()) || !start.getDayOfWeek().equals(trip.getStartDateTime().getDayOfWeek())){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("StartDateTime '{}' or endDateTime '{}' have invalid values", start, end, e);
            throw e;
        }
        return tripDao.getTripInstances(trip,page,pageSize,start,end);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByCurrentUserFuture(int page, int pageSize) throws UserNotFoundException{
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.of(LocalDateTime.now()),Optional.empty(),page,pageSize);
    }
    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByUserFuture(final User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.of(LocalDateTime.now()),Optional.empty(),page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByCurrentUserPast(int page, int pageSize)throws UserNotFoundException{
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.empty(),Optional.of(LocalDateTime.now()),page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByUserPast(final User user,int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.empty(),Optional.of(LocalDateTime.now()),page,pageSize);
    }

    @Transactional
    @Override
    //Da todos los viajes creados por el usuario
    public PagedContent<Trip> getTripsCreatedByCurrentUser(int page, int pageSize) throws UserNotFoundException{
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.empty(),Optional.empty(),page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsCreatedByUser(final User user, int page, int pageSize) {
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsCreatedByUser(user,Optional.empty(),Optional.empty(),page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsWhereCurrentUserIsPassengerFuture(int page, int pageSize) throws UserNotFoundException{
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.of(LocalDateTime.now()),Optional.empty(), null, page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassengerFuture(User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.of(LocalDateTime.now()),Optional.empty(), null, page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsWhereCurrentUserIsPassengerPast(int page, int pageSize) throws UserNotFoundException{
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.empty(),Optional.of(LocalDateTime.now()), Passenger.PassengerState.ACCEPTED, page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassengerPast(User user, int page, int pageSize){
        validatePageAndSize(page,pageSize);
        return tripDao.getTripsWhereUserIsPassenger(user,Optional.empty(),Optional.of(LocalDateTime.now()), Passenger.PassengerState.ACCEPTED, page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<Trip> getRecommendedTripsForCurrentUser(int page, int pageSize){
        validatePageAndSize(page,pageSize);
        Optional<User> user = userService.getCurrentUser();
        if(!user.isPresent()){
            return PagedContent.emptyPagedContent();
        }
        LocalDateTime start = LocalDateTime.now();
        return tripDao.getTripsByOriginAndStart(user.get().getBornCity().getId(),start,user.get().getUserId(),page,pageSize);
    }
    private Trip.SortType getTripSortType(final String sortType){
        try{
            return Trip.SortType.valueOf(sortType.toUpperCase());
        }catch (Exception e){
            return Trip.SortType.PRICE;
        }
    }

    @Transactional
    @Override
    public PagedContent<Trip> getTripsByDateTimeAndOriginAndDestinationAndPrice(
            long origin_city_id, long destination_city_id, final LocalDate startDate,
            final LocalTime startTime, final LocalDate endDate, final LocalTime endTime,
            final BigDecimal minPriceValue, final BigDecimal maxPriceValue, final String sortType, final boolean descending,
            List<FeatureCar> carFeatures,final int page, final int pageSize){
        Optional<BigDecimal> minPrice = Optional.ofNullable(minPriceValue);
        Optional<BigDecimal> maxPrice = Optional.ofNullable(maxPriceValue);
        validatePageAndSize(page,pageSize);
        LocalDateTime startDateTime = startDate.atTime(startTime);
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(endTime) : startDateTime;
        Optional<User> user = userService.getCurrentUser();
        carFeatures = carFeatures == null ? new ArrayList<>() : carFeatures;
        long userId = -1;
        if(user.isPresent()){
            userId=user.get().getUserId();
        }
        return tripDao.getTripsWithFilters(origin_city_id,destination_city_id,startDateTime,Optional.of(startDateTime.getDayOfWeek()),Optional.of(endDateTime),OFFSET_MINUTES,minPrice,maxPrice,getTripSortType(sortType),descending,userId,carFeatures,page,pageSize);
    }

    @Transactional
    @Override
    public boolean acceptPassenger(final long tripId, final long userId) throws NotAvailableSeatsException {
        User user = userService.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
        Passenger pass = tripDao.getPassenger(tripId, user).orElseThrow(()->new IllegalArgumentException("Passenger not found"));
        if(LocalDateTime.now().compareTo(pass.getStartDateTime())>=0){
            throw new IllegalStateException();//no debe poder aceptar o rechazar a pasajeros cuyo perdiodo ya empezo;
        }
        if(tripDao.getTripSeatCount(tripId,pass.getStartDateTime(),pass.getEndDateTime())>=pass.getTrip().getMaxSeats()){
            //No hay asientos disponibles
            throw new NotAvailableSeatsException();
        }
        try{
            emailService.sendMailTripConfirmed(pass.getTrip(), pass);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email for the new passenger with id {} added to the trip with id {}", pass.getUserId(), pass.getTrip().getTripId(), e);
        }
        return tripDao.acceptPassenger(pass);
    }

    @Transactional
    @Override
    public boolean rejectPassenger(final long tripId, final long userId){
        User user = userService.findById(userId).orElseThrow(()-> new IllegalArgumentException("User not found"));
        Passenger passenger = tripDao.getPassenger(tripId, user).orElseThrow(()-> new IllegalArgumentException("Passanger not found"));
        if(LocalDateTime.now().compareTo(passenger.getStartDateTime())>=0){
            throw new IllegalStateException();//no debe poder aceptar o rechazar a pasajeros cuyo perdiodo ya empezo;
        }
        try{
            emailService.sendMailTripRejected(passenger.getTrip(), passenger);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email for the new passenger with id {} added to the trip with id {}", passenger.getUserId(), passenger.getTrip().getTripId(), e);
        }
        return tripDao.rejectPassenger(passenger);
    }

}
