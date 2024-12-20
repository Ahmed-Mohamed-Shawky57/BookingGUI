public abstract class MoviePrototype implements Cloneable {
    protected String title;
    protected String genre;

    public abstract void displayDetails();

    @Override
    public MoviePrototype clone() {
        try {
            return (MoviePrototype) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
