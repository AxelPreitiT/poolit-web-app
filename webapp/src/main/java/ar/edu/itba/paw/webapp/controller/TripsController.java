package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/api/trips")
public class TripsController {

//    @POST
//    public Response createTrip(@Valid CreateTripDto dto){
//
//    }

//    @GET
////    Usar los parámetros de esto para el url de las recomendadas
////    Tambien para las creadas por un usuario o donde un usuario es participante
//    public Response getTrips(){
//
//    }
//

//
//    @GET
//    @Path("/{id}")
//    public Response getById(@PathParam("id") final long id){
//
//    }
//
//    @DELETE
//    @Path("/{id}")
//    //TODO: check creator of trip
//    public Response deleteTrip(@PathParam("id") final long id){
//
//    }
//
//
//    @POST
//    @Path("/{id}/passengers")
//    public Response addPassenger(@PathParam("id") final long id){
//        //TODO: preguntar si está bien tomar el contexto de auth acá
//    }
//
////    @Patch
//    @Path("/{id}/passengers")
////    TODO: check creator of trip
//    public Response acceptOrRejectPassenger(@PathParam("id") final long id){
//
//    }
//
//    @GET
//    @Path("/{id}/passengers")
////    TODO: ver cómo limitamos los pasajeros por cada usuario (en el url del trip se pasan los query params, pero está bien que eso cambie dependiendo del usuario?)
//    public Response getPassengers(@PathParam("id") final long id){
//
//    }


}
