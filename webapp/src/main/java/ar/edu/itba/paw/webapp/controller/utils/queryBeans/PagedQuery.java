package ar.edu.itba.paw.webapp.controller.utils.queryBeans;

import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;

import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

public class PagedQuery {
    @QueryParam(ControllerUtils.PAGE_QUERY_PARAM)
    @DefaultValue(ControllerUtils.DEFAULT_PAGE)
    @Min(0)
    private int page;

    @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM)
    @Min(1)
    @DefaultValue(ControllerUtils.DEFAULT_PAGE_SIZE)
    private int pageSize;

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }
}
