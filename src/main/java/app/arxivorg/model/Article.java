package app.arxivorg.model;

import java.util.List;

public class Article {
    private String id;
    private String updated;
    private String published;
    private String title;
    private String summary;
    private Authors authors;
    private String URL_pageArxiv;
    private String URL_PDF;
    private List<String> category;
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

    public String getId() {
        return id;
    }

    public String getUpdated() {
        return updated;
    }

    public String getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public Authors getAuthors() {
        return authors;
    }

    public String getURL_pageArxiv() {
        return URL_pageArxiv;
    }

    public String getURL_PDF() {
        return URL_PDF;
    }

    public List<String> getCategory() {
        return category;
    }

    public String toString(){
        return  "Title: " + this.getTitle() + "\n\t" +
                "Authors: " + this.getAuthors().toString() + "\n\n" +
                "Summary: " + this.getSummary() + "\n\n" +
                "un lien vers la page ArXiv: " + getURL_pageArxiv();
    }

    public boolean isFavoriteItem(){
        return favoriteItem;
    }

    public void changeFavoriteItem(){
        favoriteItem = !favoriteItem;
    }

}
