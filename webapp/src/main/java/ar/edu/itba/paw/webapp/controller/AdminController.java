package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportAdminForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ReportService reportService;

    @Autowired
    public AdminController(ReportService reportService){
        this.reportService = reportService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminView(@RequestParam(value = "acceptReport", required = false, defaultValue = "false") final Boolean acceptReport,
                                  @RequestParam(value = "rejectReport", required = false, defaultValue = "false") final Boolean rejectReport,
                                  @RequestParam(value = "page", required = true, defaultValue = "1") final int page) {
        LOGGER.debug("GET request to /admin");
        final PagedContent<Report> reports = reportService.getReports(page-1, DEFAULT_PAGE_SIZE);
        final ModelAndView mav = new ModelAndView("admin/main");
        mav.addObject("reports", reports);
        mav.addObject("acceptReport", acceptReport);
        mav.addObject("rejectReport", rejectReport);
        return mav;
    }


    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}", method = RequestMethod.GET)
    public ModelAndView detailsReport(@ModelAttribute("reportId") final long reportId) {
        LOGGER.debug("GET request to /admin/reports/{}", reportId);
        final ModelAndView mav = new ModelAndView("admin/details");
        Report report = reportService.findById(reportId).orElseThrow(() -> new ReportNotFoundException(reportId));
        mav.addObject("report", report);
        return mav;
    }

    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}/accept", method = RequestMethod.GET)
    public ModelAndView acceptReport(@ModelAttribute("reportId") final long reportId, @ModelAttribute("reportAdminForm") final ReportAdminForm form) {
        LOGGER.debug("POST request to /admin/{}/accept", reportId);
        reportService.acceptReport(reportId, form.getComment());
        return new ModelAndView("redirect:/admin?acceptReport=true");
    }

        @RequestMapping(value = "/admin/reports/{reportId:\\d+$}/reject", method = RequestMethod.GET)
    public ModelAndView rejectReport(@ModelAttribute("reportId") final long reportId, @ModelAttribute("reportAdminForm") final ReportAdminForm form) {
        LOGGER.debug("POST request to /admin/{}/reject", reportId);
        reportService.rejectReport(reportId, form.getComment());
        return new ModelAndView("redirect:/admin?rejectReport=true");
    }
}
