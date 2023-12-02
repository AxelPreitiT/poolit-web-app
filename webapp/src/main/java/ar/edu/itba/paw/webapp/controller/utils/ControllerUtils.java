package ar.edu.itba.paw.webapp.controller.utils;

import ar.edu.itba.paw.models.PagedContent;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ControllerUtils {

    public static final String PAGE_QUERY_PARAM = "page";

    private static final String TOTAL_PAGES_HEADER = "X-Total-Pages";

    public static <T> Supplier<T> notFoundExceptionOf(Function<Integer,T> constructor){
        return () -> constructor.apply(Response.Status.NOT_FOUND.getStatusCode());
    }

    //page query param starts in 0
    public static <T,E> Response getPaginatedResponse(final UriInfo uriInfo, final PagedContent<T> pagedContent, final long page, final BiFunction<UriInfo,T,E> dtoMapper, final Type type){
        if(pagedContent.getElements().isEmpty()){
            return Response.noContent().build();
        }
        //We use ParameterizedListType class to pass the type of List<E> and not E (defined by "type" argument)
        Response.ResponseBuilder aux  = Response.ok(new GenericEntity<>(pagedContent.getElements().stream().map(t -> dtoMapper.apply(uriInfo,t)).collect(Collectors.toList()),new ParameterizedListType(type)))
                                            .link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,pagedContent.getFirst()).build(),"first")
                                            .link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,pagedContent.getLast()).build(),"last");
        if(!pagedContent.isFirst()){
            aux.link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,page-1).build(),"prev");
        }
        if(!pagedContent.isLast()){
            aux.link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,page+1).build(),"next");
        }
        aux.header(TOTAL_PAGES_HEADER,pagedContent.getTotalPages());
        return aux.build();
    }

    //We use this class because we need to get the type of List<E>

    private static class ParameterizedListType implements ParameterizedType{

        private final Type elemType;

        private ParameterizedListType(final Type type){
            this.elemType = type;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{elemType};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
