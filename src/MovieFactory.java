public class MovieFactory {
    public static Movie createMovie(String type) {
        switch (type) {
            case "Action":
                return new ActionMovie();
            case "Comedy":
                return new ComedyMovie();
            case "Drama":
                return new DramaMovie();
            default:
                return null;
        }
    }

    public static Theater createTheater(String type) {
        switch (type) {
            case "IMAX":
                return new IMAXTheater();
            case "CinemaHall":
                return new CinemaHall();
            default:
                return null;
        }
    }
}