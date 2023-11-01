package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserNotFoundMapper implements ExceptionMapper<UserNotFoundException> {
    @Override
    public Response toResponse(UserNotFoundException exception){
//        TODO: change for bad request?
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
