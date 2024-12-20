public class RealBookingService implements BookingService {
    @Override
    public void bookTicket(String movieTitle, String userName) {
        System.out.println("Ticket booked for " + movieTitle + " by " + userName);
    }
}
