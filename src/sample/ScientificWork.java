package sample;

public class ScientificWork {
    private int id;
    private String title;
    private String author;
    private FieldOfStudy fieldOfStudy;
    private String journal;
    private PublicationType publicationType;
    private int yearOfIssue;
    private int citations;
    private String affiliation;

    public ScientificWork(int id, String title, String author, FieldOfStudy fieldOfStudy, String journal, PublicationType publicationType, int yearOfIssue, int citations, String affiliation) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.fieldOfStudy = fieldOfStudy;
        this.journal = journal;
        this.publicationType = publicationType;
        this.yearOfIssue = yearOfIssue;
        this.citations = citations;
        this.affiliation = affiliation;
    }

    public ScientificWork() {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public PublicationType getPublicationType() {
        return publicationType;
    }

    public void setPublicationType(PublicationType publicationType) {
        this.publicationType = publicationType;
    }

    public int getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(int yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

}
