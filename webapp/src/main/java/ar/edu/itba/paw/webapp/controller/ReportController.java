package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reports.ReportState;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.dto.input.CreateReportDto;
import ar.edu.itba.paw.webapp.dto.input.DecideReportDto;
import ar.edu.itba.paw.webapp.dto.output.reports.PrivateReportDto;
import ar.edu.itba.paw.webapp.dto.output.reports.PublicReportDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.Page;
import ar.edu.itba.paw.webapp.dto.validation.annotations.PageSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path(UrlHolder.REPORT_BASE)
@Component
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    private final ReportService reportService;

    private static final String PAGE_SIZE = "10";

    @Context
    private UriInfo uriInfo;

    @Inject
    @Autowired
    public ReportController(final ReportService reportService){
        this.reportService = reportService;
    }

    @POST
    @Consumes(value = VndType.APPLICATION_REPORT)
    public Response createReport(@Valid final CreateReportDto createReportDto) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        LOGGER.debug("POST request to create report for user {} in trip {}",createReportDto.getReportedId(), createReportDto.getTripId());
        final Report ans = reportService.createReport(createReportDto.getReportedId(), createReportDto.getTripId(), createReportDto.getComment(), createReportDto.getRelation(), createReportDto.getReason());
        return Response.created(uriInfo.getBaseUriBuilder().path(UrlHolder.REPORT_BASE).path(String.valueOf(ans.getReportId())).build()).build();
    }

    @GET
    @Path("{id}")
    @Produces(value = VndType.APPLICATION_REPORT_PUBLIC)
    public Response getByIdPublic(@PathParam("id") final long id) throws ReportNotFoundException {
        LOGGER.debug("GET request for public report with id {}",id);
        final Report ans = reportService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(ReportNotFoundException::new));
        return Response.ok(PublicReportDto.fromReport(uriInfo,ans)).build();
    }

    @GET
    @Path("{id}")
    @Produces(value = VndType.APPLICATION_REPORT_PRIVATE)
    @PreAuthorize("hasRole('ADMIN')")//TODO: intentar hacer en WebAuthConfig
    public Response getByIdPrivate(@PathParam("id") final long id) throws ReportNotFoundException {
        LOGGER.debug("GET request for private report with id {}",id);
        final Report ans = reportService.findById(id).orElseThrow(ControllerUtils.notFoundExceptionOf(ReportNotFoundException::new));
        return Response.ok(PrivateReportDto.fromReport(uriInfo,ans)).build();
    }

    @GET
    @Produces(value = VndType.APPLICATION_REPORT_PRIVATE)
    //TODO: admin only
    public Response getReportsForAdmin(@QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Page final int page,
                                       @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM) @DefaultValue(PAGE_SIZE) @Valid @PageSize final int pageSize){
        LOGGER.debug("GET request for private reports");
        final PagedContent<Report> ans = reportService.getReports(page,pageSize);
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,PrivateReportDto::fromReport,PrivateReportDto.class);
    }
    @GET
    @Produces(value = VndType.APPLICATION_REPORT_PUBLIC)
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#reporterUserId)")
    public Response getReports(@QueryParam("madeBy") final @Valid @NotNull Integer reporterUserId,
                               @QueryParam("forTrip") final @Valid @NotNull Integer tripId,
                               @QueryParam(ControllerUtils.PAGE_QUERY_PARAM) @DefaultValue("0") @Valid @Page final int page,
                               @QueryParam(ControllerUtils.PAGE_SIZE_QUERY_PARAM) @DefaultValue(PAGE_SIZE) @Valid @PageSize final int pageSize) throws UserNotFoundException, TripNotFoundException {
        LOGGER.debug("GET request for public reports made by user {} for trip {}",reporterUserId, tripId);
        final PagedContent<Report> ans = reportService.getReportsMadeByUserOnTrip(reporterUserId,tripId,page,pageSize);
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,page,PublicReportDto::fromReport,PrivateReportDto.class);
    }

    //TODO: preguntar por content type asi con distintos campos
    @PATCH
    @Consumes(value = VndType.APPLICATION_REPORT_DECISION)
    @Path("{id}")
    public Response acceptOrRejectReport(@PathParam("id") final long id,
                                         @Valid final DecideReportDto decideReportDto) throws UserNotFoundException, PassengerNotFoundException, ReportAlreadyProcessedException, TripNotFoundException, ReportNotFoundException {
        LOGGER.debug("PATCH request to accept or reject report {}",id);
        reportService.acceptOrRejectReport(id,decideReportDto.getReportState(), decideReportDto.getReason());
        return Response.noContent().build();
    }

}
