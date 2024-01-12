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

    public static final String PAGE_SIZE_QUERY_PARAM = "pageSize";

    private static final String TOTAL_PAGES_HEADER = "X-Total-Pages";

    public static <T> Supplier<T> notFoundExceptionOf(Function<Integer,T> constructor){
        return () -> constructor.apply(Response.Status.NOT_FOUND.getStatusCode());
    }


    private static <T> Response.ResponseBuilder addLinks(Response.ResponseBuilder responseBuilder, PagedContent<T> pagedContent, final UriInfo uriInfo, final long page){
        responseBuilder.link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,pagedContent.getFirst()).build(),"first")
                .link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,pagedContent.getLast()).build(),"last");
        if(!pagedContent.isFirst()){
            responseBuilder.link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,page-1).build(),"prev");
        }
        if(!pagedContent.isLast()){
            responseBuilder.link(uriInfo.getRequestUriBuilder().replaceQueryParam(PAGE_QUERY_PARAM,page+1).build(),"next");
        }
        return responseBuilder.header(TOTAL_PAGES_HEADER,pagedContent.getTotalPages());
    }
    public static <T,E> Response getPaginatedResponse(final UriInfo uriInfo, final PagedContent<T> pagedContent, final long page, final Function<T,E> dtoMapper, final Type eType){
        if(pagedContent.getElements().isEmpty()){
            return Response.noContent().build();
        }
        Response.ResponseBuilder aux = Response.ok(new GenericEntity<>(pagedContent.getElements().stream().map(dtoMapper).collect(Collectors.toList()), new ParameterizedListType(eType)));
        return addLinks(aux,pagedContent,uriInfo,page).build();
    }

    //page query param starts in 0
    public static <T,E> Response getPaginatedResponse(final UriInfo uriInfo, final PagedContent<T> pagedContent, final long page, final BiFunction<UriInfo,T,E> dtoMapper, final Type eType){
        return getPaginatedResponse(uriInfo,pagedContent,page,t -> dtoMapper.apply(uriInfo,t),eType);
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
