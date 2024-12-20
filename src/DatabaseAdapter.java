import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseAdapter {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/movie_booking";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Establish connection with the database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Add a new booking to the database
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings(user_name, movie_title, movie_genre, theater, booking_date) VALUES(?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getUserName());
            pstmt.setString(2, booking.getMovieTitle());
            pstmt.setString(3, booking.getMovieGenre());
            pstmt.setString(4, booking.getTheater());
            pstmt.setDate(5, booking.getBookingDate());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all bookings from the database
    public List<Booking> getBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String movieTitle = rs.getString("movie_title");
                String movieGenre = rs.getString("movie_genre");
                String theater = rs.getString("theater");
                Date bookingDate = rs.getDate("booking_date");
                bookings.add(new Booking(userName, movieTitle, movieGenre, theater, bookingDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Update a booking in the database
 public boolean updateBooking(String oldMovieTitle, Booking updatedBooking) {
    String sql = "UPDATE bookings SET user_name=?, movie_title=?, movie_genre=?, theater=?, booking_date=? WHERE movie_title=?";
    try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, updatedBooking.getUserName());
        pstmt.setString(2, updatedBooking.getMovieTitle());
        pstmt.setString(3, updatedBooking.getMovieGenre());
        pstmt.setString(4, updatedBooking.getTheater());
        pstmt.setDate(5, updatedBooking.getBookingDate());
        pstmt.setString(6, oldMovieTitle); // حجز الفيلم القديم لتحديد الحجز الذي سيتم تحديثه
        pstmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    // Delete a booking from the database
    public boolean deleteBooking(String movieTitle, String userName) {
        String sql = "DELETE FROM bookings WHERE movie_title=? AND user_name=?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movieTitle);
            pstmt.setString(2, userName);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean deleteBooking(String movieTitle) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<Booking> getAllBookings() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
