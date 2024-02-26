package ar.edu.itba.paw.webapp.dto.output.trips;

public class TripSeatCountDto {

    private int occupiedSeats;

    public static TripSeatCountDto fromSeatCount(final int count){
        TripSeatCountDto ans = new TripSeatCountDto();
        ans.occupiedSeats = count;
        return ans;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }
}
