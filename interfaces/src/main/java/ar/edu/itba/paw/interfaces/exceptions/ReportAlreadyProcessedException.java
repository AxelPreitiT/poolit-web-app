package ar.edu.itba.paw.interfaces.exceptions;

public class ReportAlreadyProcessedException extends CustomException{
    private static final long serialVersionUID = 341673732231763748L;

    private static final String MESSAGE_CODE = "exceptions.report_already_processed";

    //TODO: 409?
    public ReportAlreadyProcessedException(){
        super(MESSAGE_CODE);
    }
}
