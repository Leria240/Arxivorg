package app.arxivorg.model;

import java.util.ArrayList;
import java.util.List;

public class Archive {

    private List<Article> articles;

    public Archive() {
        this.articles = new ArrayList<>();

    }

    public List<Article> getArticles() {
        return articles;
    }
}
