package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.webapp.dto.validation.annotations.EndDateIsAfterStartDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@EndDateIsAfterStartDate(start = "startDate",end = "endDate")
public class AddPassengerDto {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "{dto.validation.dateFormat}")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @NotNull(message = "{dto.validation.timeFormat}")
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
