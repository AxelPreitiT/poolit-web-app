package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.webapp.controller.mediaType.VndType;
import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;
import ar.edu.itba.paw.webapp.controller.utils.queryBeans.PagedQuery;
import ar.edu.itba.paw.webapp.dto.input.CreateReportDto;
import ar.edu.itba.paw.webapp.dto.input.DecideReportDto;
import ar.edu.itba.paw.webapp.dto.output.reports.PrivateReportDto;
import ar.edu.itba.paw.webapp.dto.output.reports.PublicReportDto;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
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
    public Response getByIdPublic(@PathParam("id") final long id) {
        LOGGER.debug("GET request for public report with id {}",id);
        final Report ans = reportService.findById(id).orElseThrow(ResourceNotFoundException::new);
        return Response.ok(PublicReportDto.fromReport(uriInfo,ans)).build();
    }

    @GET
    @Path("{id}")
    @Produces(value = VndType.APPLICATION_REPORT_PRIVATE)
    @PreAuthorize("hasRole('ADMIN')")
    public Response getByIdPrivate(@PathParam("id") final long id){
        LOGGER.debug("GET request for private report with id {}",id);
        final Report ans = reportService.findById(id).orElseThrow(ResourceNotFoundException::new);
        return Response.ok(PrivateReportDto.fromReport(uriInfo,ans)).build();
    }

    @GET
    @Produces(value = VndType.APPLICATION_REPORT_PRIVATE)
    @PreAuthorize("hasRole('ADMIN')")
    public Response getReportsForAdmin(@Valid @BeanParam final PagedQuery query){
        LOGGER.debug("GET request for private reports");
        final PagedContent<Report> ans = reportService.getReports(query.getPage(),query.getPageSize());
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),PrivateReportDto::fromReport,PrivateReportDto.class);
    }

    @GET
    @Produces(value = VndType.APPLICATION_REPORT_PUBLIC)
    @PreAuthorize("@authValidator.checkIfWantedIsSelf(#reporterUserId)")
    public Response getReports(@QueryParam("madeBy") final @Valid @NotNull Integer reporterUserId,
                               @QueryParam("forTrip") final @Valid @NotNull Integer tripId,
                               @QueryParam("forUser") final Integer reportedUserId,
                               @Valid @BeanParam final PagedQuery query) throws UserNotFoundException, TripNotFoundException {
        final PagedContent<Report> ans;
        if(reportedUserId!=null){
            LOGGER.debug("GET request for public reports made by user {} for trip {} and user {}",reporterUserId, tripId, reportedUserId);
            ans = reportService.getReport(reporterUserId,reportedUserId,tripId);
        }else{
            LOGGER.debug("GET request for public reports made by user {} for trip {}",reporterUserId, tripId);
            ans = reportService.getReportsMadeByUserOnTrip(reporterUserId,tripId,query.getPage(),query.getPageSize());
        }
        return ControllerUtils.getPaginatedResponse(uriInfo,ans,query.getPage(),PublicReportDto::fromReport,PrivateReportDto.class);
    }

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
