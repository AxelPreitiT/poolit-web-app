package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.webapp.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportAdminForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);


    private final ReportService reportService;

    @Autowired
    public AdminController(ReportService reportService){
        this.reportService = reportService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminView(@RequestParam(value = "acceptReport", required = false, defaultValue = "false") final Boolean acceptReport,
                                  @RequestParam(value = "rejectReport", required = false, defaultValue = "false") final Boolean rejectReport) {
        LOGGER.debug("GET request to /admin");
        final ModelAndView mav = new ModelAndView("admin/main");
        mav.addObject("reports", reportService.getAllReports());
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

    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}/accept", method = RequestMethod.POST)
    public ModelAndView acceptReport(@ModelAttribute("reportId") final long reportId, @ModelAttribute("reportAdminForm") final ReportAdminForm form) {
        LOGGER.debug("POST request to /admin/{}/accept", reportId);
        reportService.acceptReport(reportId, form.getComment());
        return new ModelAndView("redirect:/admin?acceptReport=true");
    }

    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}/reject", method = RequestMethod.POST)
    public ModelAndView rejectReport(@ModelAttribute("reportId") final long reportId, @ModelAttribute("reportAdminForm") final ReportAdminForm form) {
        LOGGER.debug("POST request to /admin/{}/reject", reportId);
        reportService.rejectReport(reportId, form.getComment());
        return new ModelAndView("redirect:/admin?rejectReport=true");
    }
}
