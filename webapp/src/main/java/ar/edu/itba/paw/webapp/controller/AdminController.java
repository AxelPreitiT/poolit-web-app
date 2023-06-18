package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.ReportService;
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
        LOGGER.debug("GET admin view");
        final ModelAndView mav = new ModelAndView("admin/main");
        mav.addObject("reports", reportService.getAllReports());
        return mav;
    }


    @RequestMapping(value = "/admin/{id:\\d+$}", method = RequestMethod.GET)
    public ModelAndView detailsReport(@ModelAttribute("id") final long id) {
        LOGGER.debug("GET request to /admin/{}", id);
        final ModelAndView mav = new ModelAndView("admin/details");
        mav.addObject("report", reportService.findById(id));
        return mav;
    }

    @RequestMapping(value = "/admin/{id:\\d+$}/accept", method = RequestMethod.POST)
    public ModelAndView acceptReport(@ModelAttribute("id") final long id, @ModelAttribute("reportAdminForm") final ReportAdminForm form) {
        LOGGER.debug("POST request to /admin/{}/accept", id);
        reportService.acceptReport(id, form.getComment());
        return new ModelAndView("redirect:/admin?acceptReport=true");
    }

    @RequestMapping(value = "/admin/{id:\\d+$}/reject", method = RequestMethod.POST)
    public ModelAndView rejectReport(@ModelAttribute("id") final long id, @ModelAttribute("reportAdminForm") final ReportAdminForm form) {
        LOGGER.debug("POST request to /admin/{}/reject", id);
        reportService.rejectReport(id, form.getComment());
        return new ModelAndView("redirect:/admin?rejectReport=true");
    }
}
