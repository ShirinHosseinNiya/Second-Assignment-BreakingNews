package AP;

public class News {
    private String title;
    private String description;
    private String sourceName;
    private String author;
    private String url;
    private String publishedAt;

    //constructor
    public News(String title, String description, String sourceName, String author, String url, String publishedAt) {
        this.title = title;
        this.description = description;
        this.sourceName = sourceName;
        this.author = author;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    //getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSourceName() { return sourceName; }
    public String getAuthor() { return author; }
    public String getUrl() { return url; }
    public String getPublishedAt() { return publishedAt; }

    @Override //display news
    public String toString() {
        return "Title: " + title +
                "\nDescription: " + description +
                "\nSource: " + sourceName +
                "\nAuthor: " + author +
                "\nURL: " + url +
                "\nPublished At: " + publishedAt;
    }
}