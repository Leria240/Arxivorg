package app.arxivorg.model;

import java.util.Date;
import java.util.List;

public class Article {
    private String id;
    private Date published;
    private String title;
    private String summary;
    private List<Authors> authors;
    private String comment;
    private String URL_pageArxiv;
    private String URL_PDF;
    private String category;
    private String primaryCategory;
    private boolean favoriteItem;

    public Article(String id, Date published, String title,
                   String summary, List<Authors> authors, String comment,
                   String URL_pageArxiv, String URL_PDF, String category, String primaryCategory) {
        this.id = id;
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
