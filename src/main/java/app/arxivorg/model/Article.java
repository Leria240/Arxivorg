package app.arxivorg.model;

import java.util.Date;
import java.util.List;

public class Article {
    private String id;
    private String updated;
    private String published;
    private String title;
    private String summary;
    private Authors authors;
    private String comment;
    private String URL_pageArxiv;
    private String URL_PDF;
    private List<String> category;
    private String primaryCategory;
    private boolean favoriteItem;

    public Article(String id, String updated, String published, String title,
                   String summary, Authors authors,
                   String URL_pageArxiv, String URL_PDF, List<String> category) {
        this.id = id;
        this.updated = updated;
        this.published = published;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.URL_pageArxiv = URL_pageArxiv;
        this.URL_PDF = URL_PDF;
        this.category = category;
        this.favoriteItem = false;
    }

    public Authors getAuthors() {
        return authors;
    }

    public String getTitle() {
        return title;
    }
}
