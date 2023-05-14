package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.annotations.LastDateIsAfterDate;
import ar.edu.itba.paw.webapp.form.annotations.SameWeekDay;
import ar.edu.itba.paw.webapp.form.annotations.NowOrLater;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@SameWeekDay
@LastDateIsAfterDate
@NowOrLater
public class RecurrentTripForm {

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private boolean multitrip;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastDate;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public boolean isMultitrip() {
        return multitrip;
    }

    public void setMultitrip(boolean multitrip) {
        this.multitrip = multitrip;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }
}
