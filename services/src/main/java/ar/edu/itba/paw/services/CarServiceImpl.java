package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    private final ImageService imageService;

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

    @Value("classpath:images/car.jpeg")
    private Resource defaultImg;

    //Para no pisar a las que ya estan subidas en pawserver, el default es ese
    private static final long DEFAULT_IMAGE_ID = 1;

    @Autowired
    public CarServiceImpl(final CarDao carDao, final ImageService imageService1, final UserService userService1){
        this.carDao = carDao;
        this.imageService = imageService1;
        this.userService = userService1;
    }

    @Transactional
    @Override
    public Car createCar(String plate, String infoCar, byte[] imgData, int seats, CarBrand brand, List<FeatureCar> features) throws UserNotFoundException {
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final long imageId = imageService.createImage(imgData).getImageId();
        return carDao.create(plate, infoCar, user, imageId, seats, brand, features);
    }

    @Transactional
    @Override
    public Car modifyCar(long carId, String infoCar, Integer seats, List<FeatureCar> features, byte[] imgData) throws CarNotFoundException {
        Car car=findById(carId).orElseThrow(CarNotFoundException::new);

        if(imgData== null || imgData.length<=0){
            return carDao.modifyCar(carId,
                    infoCar!=null?infoCar:car.getInfoCar(),
                    seats!=null?seats:car.getSeats(),
                    features!=null?features:car.getFeatures(),
                    car.getImageId());
        }
        final long newImageId = imageService.createImage(imgData).getImageId();
        return carDao.modifyCar(carId,
                infoCar!=null?infoCar:car.getInfoCar(),
                seats!=null?seats:car.getSeats(),
                features!=null?features:car.getFeatures(),
                newImageId);


    }

    @Transactional
    @Override
    public Optional<Car> findById(long carId) {
        return carDao.findById(carId);
    }


    @Transactional
    @Override
    public List<Car> findUserCars(final long userId) throws UserNotFoundException {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return carDao.findByUser(user);
    }

    @Transactional
    @Override
    public List<Car> findCurrentUserCars() throws UserNotFoundException {
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return carDao.findByUser(user);
    }

    @Transactional
    @Override
    public Optional<Car> findByUserAndPlate(User user, String plate){
        return carDao.findByPlateAndUser(plate,user);
    }


    @Transactional
    @Override
    public Image getCarImage(final long carId, final Image.Size size) throws CarNotFoundException, ImageNotFoundException {
        final Car car = findById(carId).orElseThrow(CarNotFoundException::new);
        try {
            return imageService.getImageOrDefault(car.getImageId(),size,defaultImg.getInputStream());
        }catch (IOException e){
            throw new ImageNotFoundException();
        }
    }

    @Transactional
    @Override
    public void updateCarImage(final long carId, final byte[] content) throws CarNotFoundException, ImageNotFoundException{
        final Car car = findById(carId).orElseThrow(CarNotFoundException::new);
        final long oldImageId = car.getImageId();
        final long imageId = imageService.createImage(content).getImageId();
        if(oldImageId != DEFAULT_IMAGE_ID){
            try {
                imageService.deleteImage(oldImageId);
            }catch (Exception e){
                LOGGER.error("There was an error trying to delete image with id {} for car {}",oldImageId,car.getCarId(),e);
            }
        }
        carDao.modifyCar(carId, car.getInfoCar(), car.getSeats(), car.getFeatures(), imageId);
//        if(car.getImageId() == DEFAULT_IMAGE_ID){
//            //creamos una imagen para no pisar la default
//            final long imageId = imageService.createImage(content).getImageId();
//            carDao.modifyCar(carId, car.getInfoCar(), car.getSeats(), car.getFeatures(), imageId);
//            return;
//        }
//        imageService.updateImage(content,car.getImageId());
    }
}
