package ar.edu.itba.paw.webapp.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3213155749078647781L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportNotFoundException.class);


    public ReportNotFoundException(final long reportId) {
        super();
        LOGGER.error("Report not found with id {}", reportId);
    }
}
