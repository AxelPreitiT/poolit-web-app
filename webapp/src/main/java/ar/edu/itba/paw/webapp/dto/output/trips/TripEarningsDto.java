package ar.edu.itba.paw.webapp.dto.output.trips;

public class TripEarningsDto {
    private double tripEarnings;

    public static TripEarningsDto fromTripEarnings(final double tripEarnings){
        TripEarningsDto ans = new TripEarningsDto();
        ans.tripEarnings = tripEarnings;
        return ans;
    }

    public double getTripEarnings() {
        return tripEarnings;
    }

    public void setTripEarnings(double tripEarnings) {
        this.tripEarnings = tripEarnings;
    }
}
