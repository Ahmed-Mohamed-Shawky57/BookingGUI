public class ActionMoviePrototype extends MoviePrototype {
    public ActionMoviePrototype(String title) {
        this.title = title;
        this.genre = "Action";
    }

    @Override
    public void displayDetails() {
        System.out.println("Movie Title: " + title + ", Genre: " + genre);
    }
}