package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AuthorsArticlesController implements Initializable {


    @FXML private ListView<String> listView;
    @FXML private TextArea metadata;
    @FXML private CheckBox favorite;
    @FXML public Label name;
    private List<Article> articles = new ArrayList<>();
    private Archive archive =  Archive.archiveFile2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void displayArticles(String authorName){
        metadata.setText("Click on one of the articles above to see more details");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        articles = archive.articlesPublishedBy(authorName.trim());
        name.setText(authorName);
        for(Article article : articles){
            listView.getItems().add(article.mainInformations());
        }
    }

    @FXML
    public void displayMetadata() {
        int index = listView.getSelectionModel().getSelectedIndex();
        metadata.setText(articles.get(index).toString());
        favorite.setSelected(articles.get(index).isFavoriteItem());
    }

    @FXML
    public void updateFavoriteItem(){
        int index = listView.getSelectionModel().getSelectedIndex();
        articles.get(index).changeFavoriteItem();
    }

    @FXML
    public void downloadArticle(){
        int index = listView.getSelectionModel().getSelectedIndex();
        articles.get(index).download();
    }

}
