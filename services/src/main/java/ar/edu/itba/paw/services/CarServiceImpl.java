package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarDao carDao;

    private final ImageService imageService;

    private final UserService userService;

    //TODO: cambiar la imagen a la default para el auto
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
//        if (imgData== null || imgData.length==0){
//            //TODO CAMBIAR EN PRODUCCION A LA DEFAULT
//            imageId = 1;
//        } else {
//            imageId = imageService.createImage(imgData).getImageId();
//        }
        return carDao.create(plate, infoCar, user, imageId, seats, brand, features);
    }

    @Transactional
    @Override
    public Car modifyCar(long carId, String infoCar, int seats, List<FeatureCar> features, byte[] imgData) throws CarNotFoundException {
        Car car=findById(carId).orElseThrow(CarNotFoundException::new);

        if(imgData== null || imgData.length<=0){
            return carDao.modifyCar(carId, infoCar, seats, features, car.getImageId());
        }
        final long newImageId = imageService.createImage(imgData).getImageId();
        return carDao.modifyCar(carId, infoCar, seats, features, newImageId);


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
        //TODO Chequear si esta funcion esta hecha para solo ser usada por el usuario principal
        User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        return carDao.findByUser(user);
    }

    @Transactional
    @Override
    public Optional<Car> findByUserAndPlate(User user, String plate){
        return carDao.findByPlateAndUser(plate,user);
    }

//    @Transactional
//    @Override
//    public boolean currentUserIsCarOwner(Car car){
//        Optional<User> user = userService.getCurrentUser();
//        return user.isPresent() && car.getUser().getUserId() == user.get().getUserId();
//    }

    @Transactional
    @Override
    public byte[] getCarImage(final long carId) throws CarNotFoundException, ImageNotFoundException {
        final Car car = findById(carId).orElseThrow(CarNotFoundException::new);
        try {
            return imageService.getImageByteaOrDefault(car.getImageId(),defaultImg.getInputStream());
        }catch (IOException e){
            return new byte[0];
        }
    }

    @Transactional
    @Override
    public void updateCarImage(final long carId, final byte[] content) throws CarNotFoundException, ImageNotFoundException{
        final Car car = findById(carId).orElseThrow(CarNotFoundException::new);
        if(car.getImageId() == DEFAULT_IMAGE_ID){
            //creamos una imagen para no pisar la default
            final long imageId = imageService.createImage(content).getImageId();
            carDao.modifyCar(carId, car.getInfoCar(), car.getSeats(), car.getFeatures(), imageId);
            return;
        }
        imageService.updateImage(content,car.getImageId());
    }
}
