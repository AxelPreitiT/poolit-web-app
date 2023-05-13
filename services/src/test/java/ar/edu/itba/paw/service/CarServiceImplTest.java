package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.CarServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {

    private static final String plate = "ABC123";
    private static final String infoCar = "INFO";
    private static final User user = new User(1, "USER", "SURNAME", "EMAIL", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final long image_id = 1L;
    private static final long carId = 2L;

    @Mock
    private CarDao carDao;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testCreateCar() {
        // precondiciones
        when(carDao.create(eq(plate), eq(infoCar), any(), anyLong()))
                .thenReturn(new Car(carId, plate, infoCar, user, image_id));

        // ejercitar la clase
        Car newCar = carService.createCar(plate, infoCar, user, image_id);

        // assertions
        Assert.assertNotNull(newCar);
        Assert.assertEquals(carId, newCar.getCarId());
        Assert.assertEquals(plate, newCar.getPlate());
        Assert.assertEquals(user, newCar.getUser());
        Assert.assertEquals(image_id, newCar.getImage_id());
    }

}
