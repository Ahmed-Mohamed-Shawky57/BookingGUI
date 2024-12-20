public class BookingProxy implements BookingService {
    private RealBookingService realBookingService;

    public BookingProxy() {
        realBookingService = new RealBookingService();
    }

    @Override
    public void bookTicket(String movieTitle, String userName) {
        if (userName != null && !userName.isEmpty()) {
            realBookingService.bookTicket(movieTitle, userName);
        } else {
            System.out.println("User is not authenticated.");
        }
    }
}
