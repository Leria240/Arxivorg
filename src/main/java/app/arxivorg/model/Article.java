package app.arxivorg.model;

import java.util.Date;
import java.util.List;

public class Article {
    private String id;
    private Date updated;
    private Date published;
    private String title;
    private String summary;
    private Authors authors;
    private String comment;
    private String URL_pageArxiv;
    private String URL_PDF;
    private List<String> category;
    private String primaryCategory;
    private boolean favoriteItem;

    public Article(String id, Date updated, Date published, String title,
                   String summary, Authors authors, String comment,
                   String URL_pageArxiv, String URL_PDF, List<String> category, String primaryCategory) {
        this.id = id;
        this.updated = updated;
        this.published = published;
        this.title = title;
        this.summary = summary;
        this.authors = authors;
        this.comment = comment;
        this.URL_pageArxiv = URL_pageArxiv;
        this.URL_PDF = URL_PDF;
        this.category = category;
        this.primaryCategory = primaryCategory;
        this.favoriteItem = false;
    }
}
