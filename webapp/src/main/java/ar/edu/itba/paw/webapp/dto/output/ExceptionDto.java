package ar.edu.itba.paw.webapp.dto.output;

public class ExceptionDto {
    private String message;

    public static ExceptionDto fromMessage(final String message){
        ExceptionDto ans = new ExceptionDto();
        ans.message = message;
        return ans;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
