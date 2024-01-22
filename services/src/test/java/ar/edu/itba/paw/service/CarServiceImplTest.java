package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.services.CarServiceImpl;
import ar.edu.itba.paw.services.ImageServiceImpl;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static ar.edu.itba.paw.models.CarBrand.VOLKSWAGEN;
import static ar.edu.itba.paw.models.FeatureCar.AIR;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {

    private static final String PLATE = "ABC123";
    private static final String INFO_CAR = "INFO";
    private static final User USER = new User(1, "USER", "SURNAME", "EMAIL", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final long IMAGE_ID = 1L;
    private static final int SEATS = 5;
    private static final long CAR_ID = 2L;
    private static final List<FeatureCar> FEATURES = new ArrayList<>(AIR.ordinal());
    private static final CarBrand BRAND_ID = VOLKSWAGEN;
    private static final Image IMAGEN = new Image(0L, null);

    @Mock
    private CarDao carDao;
    @InjectMocks
    private CarServiceImpl carService;
    @Mock
    private ImageServiceImpl imageService;
    @Mock
    private UserServiceImpl userService;


//    @Test
//    public void TestCreateCar() throws UserNotFoundException {
//        when(carDao.create(eq(PLATE), eq(INFO_CAR), eq(USER), anyLong(), anyInt(), eq(BRAND_ID), eq(FEATURES)))
//                .thenReturn(new Car(PLATE, INFO_CAR, USER, IMAGE_ID, SEATS, BRAND_ID, FEATURES));
////        when(imageService.createImage(any())).thenReturn(IMAGEN);
//        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
//
//        Car newCar = carService.createCar(PLATE, INFO_CAR, null, SEATS, BRAND_ID, FEATURES);
//
//        Assert.assertNotNull(newCar);
//        Assert.assertEquals(PLATE, newCar.getPlate());
//        Assert.assertEquals(INFO_CAR, newCar.getInfoCar());
//        Assert.assertEquals(USER, newCar.getUser());
//        Assert.assertEquals(IMAGE_ID, newCar.getImageId());
//        Assert.assertEquals(SEATS, newCar.getSeats());
//        Assert.assertEquals(FEATURES, newCar.getFeatures());
//    }


    @Test(expected = UserNotFoundException.class)
    public void TestNotUserCreateCar() throws UserNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.empty());
        carService.createCar(PLATE, INFO_CAR, null, SEATS, BRAND_ID, FEATURES);
        Assert.fail();
    }

    @Test
    public void TestModifyCar() throws CarNotFoundException {
        when(carDao.modifyCar(eq(CAR_ID), eq(INFO_CAR), anyInt(), eq(FEATURES),anyLong()))
                .thenReturn(new Car(PLATE, INFO_CAR, USER, IMAGE_ID, SEATS, BRAND_ID, FEATURES));
        //when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(carService.findById(CAR_ID)).thenReturn(Optional.of(new Car(PLATE, INFO_CAR, USER, IMAGE_ID, SEATS, BRAND_ID, FEATURES)));
        //doNothing().when(imageService).replaceImage(anyLong(), any(byte[].class));

        Car newCar = carService.modifyCar(CAR_ID, INFO_CAR, SEATS, FEATURES, null);

        Assert.assertNotNull(newCar);
        Assert.assertEquals(PLATE, newCar.getPlate());
        Assert.assertEquals(INFO_CAR, newCar.getInfoCar());
        Assert.assertEquals(USER, newCar.getUser());
        Assert.assertEquals(IMAGE_ID, newCar.getImageId());
    }

    @Test(expected = CarNotFoundException.class)
    public void TestNotHAveCarModifyCar() throws CarNotFoundException {
        when(carService.findById(CAR_ID)).thenReturn(Optional.empty());
        carService.modifyCar(CAR_ID, INFO_CAR, SEATS, FEATURES, null);
        Assert.fail();
    }

}
