package app.arxivorg.model;


import javafx.stage.DirectoryChooser;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class Article {
    private String id;
    private String updated;
    private String published;
    private String title;
    private String summary;
    private Authors authors;
    private URL URL_pageArxiv;
    private URL URL_PDF;
    private List<String> category;
    private boolean favoriteItem;
    private boolean selected;

    public Article(String id, String updated, String published, String title,
                   String summary, Authors authors,
                   URL URL_pageArxiv, URL URL_PDF, List<String> category) {
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
        this.selected = true;
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

    public URL getURL_pageArxiv() {
        return URL_pageArxiv;
    }

    public URL getURL_PDF() {
        return URL_PDF;
    }

    public List<String> getCategory() {
        return category;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String mainInformations() {
        String authors = "Authors: " + getAuthors().toString();
        String id = "ArXiv: " + getId().substring(21);
        return "- " + title + "\n\t" + authors + "\n\t" + id;
    }

    public String toString() {
        return "Title: " + this.getTitle() + "\n\t" +
                "Authors: " + this.getAuthors().toString() + "\n\n" +
                "Summary: " + this.getSummary() + "\n\n" +
                "A link to the ArXiv page: " + getURL_pageArxiv();
    }

    public boolean isFavoriteItem() {
        return favoriteItem;
    }

    public void changeFavoriteItem() {
        favoriteItem = !favoriteItem;
    }



    public void download (){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select some directory");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir = directoryChooser.showDialog(null);

        if (dir == null) return;
        String title_syntaxValid = getTitle().replaceAll(":", " ");
        String destination = dir.getAbsolutePath() + "\\" + title_syntaxValid + ".pdf";

        InputStream in = null;
        String urlString = "https://" + URL_PDF.toString().substring(7);

        try {
            in = new URL(urlString).openStream();
            Files.copy(in, Paths.get(destination),StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



