package ar.edu.itba.paw.interfaces.exceptions;

public class ReportNotFoundException extends CustomException {
    private static final long serialVersionUID = 3213155749078647781L;

    private static final String MESSAGE_CODE = "exceptions.report_not_found";

    public ReportNotFoundException(){
        super(MESSAGE_CODE);
    }

    public ReportNotFoundException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }
}
