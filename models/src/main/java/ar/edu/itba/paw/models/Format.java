package ar.edu.itba.paw.models;

import java.time.format.DateTimeFormatter;

public class Format {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static DateTimeFormatter getDateFormatter() {
        return DATE_FORMATTER;
    }

    public static DateTimeFormatter getTimeFormatter() {
        return TIME_FORMATTER;
    }
}
