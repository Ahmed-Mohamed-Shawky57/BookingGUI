
public class ComedyMoviePrototype extends MoviePrototype {
    public ComedyMoviePrototype(String title) {
        this.title = title;
        this.genre = "Comedy";
    }

    @Override
    public void displayDetails() {
        System.out.println("Movie Title: " + title + ", Genre: " + genre);
    }
}