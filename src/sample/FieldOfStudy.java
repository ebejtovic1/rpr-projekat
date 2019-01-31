package sample;

public class FieldOfStudy {
    private int id;
    private String title;

    public FieldOfStudy(int ID, String title) {
        this.id = ID;
        this.title = title;
    }

    public FieldOfStudy() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
