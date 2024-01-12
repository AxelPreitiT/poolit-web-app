package ar.edu.itba.paw.webapp.spring;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.interfaces.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.form.ReportAdminForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class AdminController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ReportService reportService;

    @Autowired
    public AdminController(ReportService reportService){
        this.reportService = reportService;
    }


    //Obtiene todos los reportes para un administrador
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

    //obtiene los detalles para un reporte
    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}", method = RequestMethod.GET)
    public ModelAndView detailsReport(
            @PathVariable("reportId") final long reportId,
            @ModelAttribute("reportAdminForm") final ReportAdminForm form,
            @RequestParam(value = "acceptReportFailed", required = false, defaultValue = "false") final Boolean acceptReportFailed,
            @RequestParam(value = "rejectReportFailed", required = false, defaultValue = "false") final Boolean rejectReportFailed
    ) throws ReportNotFoundException {
        LOGGER.debug("GET request to /admin/reports/{}", reportId);
        final ModelAndView mav = new ModelAndView("admin/report-details");
        Report report = reportService.findById(reportId).orElseThrow(ReportNotFoundException::new);
        mav.addObject("report", report);
        mav.addObject("acceptReportFailed", acceptReportFailed);
        mav.addObject("rejectReportFailed", rejectReportFailed);
        return mav;
    }

    //acepta un reporte
    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}/accept", method = RequestMethod.POST)
    public ModelAndView acceptReport(@PathVariable("reportId") final long reportId, @Valid @ModelAttribute("reportAdminForm") final ReportAdminForm form, final BindingResult errors) throws TripNotFoundException, ReportNotFoundException, UserNotFoundException, PassengerNotFoundException {
        LOGGER.debug("POST request to /admin/{}/accept", reportId);
        if(errors.hasErrors()) {
            LOGGER.warn("Errors found in ReportAdminForm: {}", errors.getAllErrors());
            return detailsReport(reportId, form, true, false);
        }
        reportService.acceptReport(reportId, form.getReason());
        return new ModelAndView("redirect:/admin?acceptReport=true");
    }


    //rechaza un reporte
    @RequestMapping(value = "/admin/reports/{reportId:\\d+$}/reject", method = RequestMethod.POST)
    public ModelAndView rejectReport(@PathVariable("reportId") final long reportId, @Valid @ModelAttribute("reportAdminForm") final ReportAdminForm form, final BindingResult errors) throws ReportNotFoundException {
        LOGGER.debug("POST request to /admin/{}/reject", reportId);
        if(errors.hasErrors()) {
            LOGGER.warn("Errors found in ReportAdminForm: {}", errors.getAllErrors());
            return detailsReport(reportId, form, false, true);
        }
        reportService.rejectReport(reportId, form.getReason());
        return new ModelAndView("redirect:/admin?rejectReport=true");
    }
}
