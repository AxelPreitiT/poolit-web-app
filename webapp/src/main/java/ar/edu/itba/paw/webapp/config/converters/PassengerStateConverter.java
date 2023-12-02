package ar.edu.itba.paw.webapp.config.converters;

import ar.edu.itba.paw.models.Passenger;


import javax.ws.rs.ext.ParamConverter;

public class PassengerStateConverter implements ParamConverter<Passenger.PassengerState> {

    @Override
    public Passenger.PassengerState fromString(String value) {
        if(value==null){
            return null;
        }
        try {
            return Passenger.PassengerState.valueOf(value);
        }catch (Exception e){
            throw new ConversionException("conversions.passengerState");
        }
    }

    @Override
    public String toString(Passenger.PassengerState value) {
        return value.toString();
    }
}
