public class SessionManager {
    private static SessionManager instance;
    private String currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(String userName) {
        this.currentUser = userName;
    }

    public String getCurrentUser() {
        return currentUser;
    }
}
