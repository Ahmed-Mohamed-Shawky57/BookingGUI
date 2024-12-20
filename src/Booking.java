import java.sql.Date;

public class Booking {
    private String userName;
    private String movieTitle;
    private String movieGenre;
    private String theater;
    private Date bookingDate;

    public Booking(String userName, String movieTitle, String movieGenre, String theater, Date bookingDate) {
        this.userName = userName;
        this.movieTitle = movieTitle;
        this.movieGenre = movieGenre;
        this.theater = theater;
        this.bookingDate = bookingDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public String getTheater() {
        return theater;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "userName='" + userName + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieGenre='" + movieGenre + '\'' +
                ", theater='" + theater + '\'' +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
