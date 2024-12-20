import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class BookingGUI {
    private JFrame frame;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JTextField txtMovieTitle, txtMovieGenre, txtBookingDate, txtUserName;
    private JComboBox<String> cmbTheater;
    private DatabaseAdapter databaseAdapter;

    public BookingGUI() {
                // الحصول على النسخة الوحيدة من SessionManager
        SessionManager session = SessionManager.getInstance();

        // ضبط المستخدم الحالي
        session.setCurrentUser("Ahmed");

        // عرض اسم المستخدم الحالي
        System.out.println("Current User: " + session.getCurrentUser());

        // التحقق من أن الكائن نفسه
        SessionManager anotherSession = SessionManager.getInstance();
        System.out.println("Current User from another instance: " + anotherSession.getCurrentUser());
          Movie actionMovie = MovieFactory.createMovie("Action");
        actionMovie.show(); // النتيجة: Showing Action Movie

        // إنشاء فيلم من نوع Comedy
        Movie comedyMovie = MovieFactory.createMovie("Comedy");
        comedyMovie.show(); // النتيجة: Showing Comedy Movie

        // إنشاء مسرح من نوع IMAX
        Theater imaxTheater = MovieFactory.createTheater("IMAX");
        imaxTheater.displayScreen(); // النتيجة: Displaying in IMAX Theater

        // إنشاء مسرح من نوع CinemaHall
        Theater cinemaHall = MovieFactory.createTheater("CinemaHall");
        cinemaHall.displayScreen(); // النتيجة: Displaying in Cinema Hall
        // إنشاء فيلم كوميدي
        ComedyMoviePrototype originalComedyMovie = new ComedyMoviePrototype("Funny Movie");
        originalComedyMovie.displayDetails(); 
        // النتيجة: Movie Title: Funny Movie, Genre: Comedy

        // إنشاء نسخة (Clone) من فيلم كوميدي
        ComedyMoviePrototype clonedComedyMovie = (ComedyMoviePrototype) originalComedyMovie.clone();
        clonedComedyMovie.displayDetails(); 
        // النتيجة: Movie Title: Funny Movie, Genre: Comedy

        // إنشاء فيلم أكشن
        ActionMoviePrototype originalActionMovie = new ActionMoviePrototype("Thrilling Movie");
        originalActionMovie.displayDetails(); 
        // النتيجة: Movie Title: Thrilling Movie, Genre: Action

        // إنشاء نسخة (Clone) من فيلم أكشن
        ActionMoviePrototype clonedActionMovie = (ActionMoviePrototype) originalActionMovie.clone();
        clonedActionMovie.displayDetails(); 
        // النتيجة: Movie Title: Thrilling Movie, Genre: Action
          // إنشاء كائن من BookingProxy
        BookingService proxy = new BookingProxy();

        // حالة: اسم المستخدم صحيح
        proxy.bookTicket("Inception", "John Doe");
        // النتيجة: Ticket booked for Inception by John Doe

        // حالة: اسم المستخدم فارغ
        proxy.bookTicket("Inception", "");
        // النتيجة: User is not authenticated.

        // حالة: اسم المستخدم غير موجود (null)
        proxy.bookTicket("Inception", null);
        // النتيجة: User is not authenticated.
           
       
       
       
    
        
        
        
        
        
        
        
        
        
        
        databaseAdapter = new DatabaseAdapter();


        frame = new JFrame("Movie Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("User Name:"));
        txtUserName = new JTextField();
        panel.add(txtUserName);

        panel.add(new JLabel("Movie Title:"));
        txtMovieTitle = new JTextField();
        panel.add(txtMovieTitle);

        panel.add(new JLabel("Movie Genre:"));
        txtMovieGenre = new JTextField();
        panel.add(txtMovieGenre);

        panel.add(new JLabel("Theater:"));
        cmbTheater = new JComboBox<>(new String[]{"CinemaHall", "IMAX"});
        panel.add(cmbTheater);

        panel.add(new JLabel("Booking Date (yyyy-MM-dd):"));
        txtBookingDate = new JTextField();
        panel.add(txtBookingDate);

        JButton btnAdd = new JButton("Add Booking");
        btnAdd.addActionListener(e -> addBookingAction());
        panel.add(btnAdd);

        JButton btnShow = new JButton("Show Bookings");
        btnShow.addActionListener(e -> showBookingsWindow());
        panel.add(btnShow);

        frame.add(panel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private void addBookingAction() {
        String userName = txtUserName.getText(); // استخدام txtUserName بدلاً من cmbUsers
        String movieTitle = txtMovieTitle.getText();
        String movieGenre = txtMovieGenre.getText();
        String theater = (String) cmbTheater.getSelectedItem();
        String bookingDateStr = txtBookingDate.getText();

        if (userName.isEmpty() || movieTitle.isEmpty() || movieGenre.isEmpty() || bookingDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all the fields!");
            return;
        }

        java.sql.Date bookingDate = convertStringToDate(bookingDateStr);
        if (bookingDate == null) {
            JOptionPane.showMessageDialog(frame, "Invalid date format! Please use yyyy-MM-dd.");
            return;
        }

        Booking newBooking = new Booking(userName, movieTitle, movieGenre, theater, bookingDate);
        boolean success = databaseAdapter.addBooking(newBooking);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Booking added successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to add booking. Please try again.");
        }
    }

    private java.sql.Date convertStringToDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = sdf.parse(dateStr);
            return new java.sql.Date(parsedDate.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    private void showBookingsWindow() {
        JFrame bookingsFrame = new JFrame("All Bookings");
        bookingsFrame.setSize(800, 600);

        String[] columns = {"User", "Movie Title", "Movie Genre", "Theater", "Booking Date"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        bookingsFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnUpdate = new JButton("Update Booking");
        btnUpdate.addActionListener(e -> updateBookingAction());
        panel.add(btnUpdate);

        JButton btnDelete = new JButton("Delete Booking");
        btnDelete.addActionListener(e -> deleteBookingAction());
        panel.add(btnDelete);

        bookingsFrame.add(panel, BorderLayout.SOUTH);
        bookingsFrame.setVisible(true);

        getBookingData();
    }

    private void updateBookingAction() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a booking to update.");
            return;
        }

        // استخراج بيانات الحجز المحدد من الجدول
        String userName = (String) bookingsTable.getValueAt(selectedRow, 0);
        String movieTitle = (String) bookingsTable.getValueAt(selectedRow, 1);
        String movieGenre = (String) bookingsTable.getValueAt(selectedRow, 2);
        String theater = (String) bookingsTable.getValueAt(selectedRow, 3);
        java.sql.Date bookingDate = (java.sql.Date) bookingsTable.getValueAt(selectedRow, 4);

        // إظهار نافذة لتعديل الحجز
        JPanel updatePanel = new JPanel(new GridLayout(5, 2, 5, 5));
        updatePanel.add(new JLabel("User Name:"));
        JTextField txtUpdateUserName = new JTextField(userName);
        updatePanel.add(txtUpdateUserName);

        updatePanel.add(new JLabel("Movie Title:"));
        JTextField txtUpdateMovieTitle = new JTextField(movieTitle);
        updatePanel.add(txtUpdateMovieTitle);

        updatePanel.add(new JLabel("Movie Genre:"));
        JTextField txtUpdateMovieGenre = new JTextField(movieGenre);
        updatePanel.add(txtUpdateMovieGenre);

        updatePanel.add(new JLabel("Theater:"));
        JComboBox<String> cmbUpdateTheater = new JComboBox<>(new String[]{"CinemaHall", "IMAX"});
        cmbUpdateTheater.setSelectedItem(theater);
        updatePanel.add(cmbUpdateTheater);

        updatePanel.add(new JLabel("Booking Date (yyyy-MM-dd):"));
        JTextField txtUpdateBookingDate = new JTextField(bookingDate.toString());
        updatePanel.add(txtUpdateBookingDate);

        int option = JOptionPane.showConfirmDialog(frame, updatePanel, "Update Booking", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String updatedUserName = txtUpdateUserName.getText();
            String updatedMovieTitle = txtUpdateMovieTitle.getText();
            String updatedMovieGenre = txtUpdateMovieGenre.getText();
            String updatedTheater = (String) cmbUpdateTheater.getSelectedItem();
            String updatedBookingDateStr = txtUpdateBookingDate.getText();

            if (updatedMovieTitle.isEmpty() || updatedMovieGenre.isEmpty() || updatedBookingDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            java.sql.Date updatedBookingDate = convertStringToDate(updatedBookingDateStr);
            if (updatedBookingDate == null) {
                JOptionPane.showMessageDialog(frame, "Invalid date format! Please use yyyy-MM-dd.");
                return;
            }

            // تحديث الحجز في قاعدة البيانات
            Booking updatedBooking = new Booking(updatedUserName, updatedMovieTitle, updatedMovieGenre, updatedTheater, updatedBookingDate);
            boolean success = databaseAdapter.updateBooking(movieTitle, updatedBooking); // حجز الفيلم القديم لتحديد الحجز الذي سيتم تحديثه
            if (success) {
                JOptionPane.showMessageDialog(frame, "Booking updated successfully!");
                getBookingData(); // تحديث الجدول لعرض الحجز المحدث
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to update booking.");
            }
        }
    }

    private void deleteBookingAction() {
        int selectedRow = bookingsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a booking to delete.");
            return;
        }

        // Extracting data from the table
        String userName = (String) bookingsTable.getValueAt(selectedRow, 0);
        String movieTitle = (String) bookingsTable.getValueAt(selectedRow, 1);

        boolean success = databaseAdapter.deleteBooking(movieTitle, userName);
        if (success) {
            JOptionPane.showMessageDialog(frame, "Booking deleted successfully!");
            getBookingData();
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to delete booking.");
        }
    }

    private void getBookingData() {
        List<Booking> bookings = databaseAdapter.getBookings();
        tableModel.setRowCount(0);
        for (Booking booking : bookings) {
            Object[] rowData = {
                    booking.getUserName(),
                    booking.getMovieTitle(),
                    booking.getMovieGenre(),
                    booking.getTheater(),
                    booking.getBookingDate()
            };
            tableModel.addRow(rowData);
        }
    }
}