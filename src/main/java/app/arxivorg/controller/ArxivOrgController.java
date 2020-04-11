package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<String> listView;
    @FXML private TextArea metadata;
    @FXML private CheckBox favorite;
    @FXML private ChoiceBox<String> categories;
    @FXML private DatePicker period;
    @FXML private TextArea authors;
    @FXML private TextArea TitleKeyword;
    @FXML private TextArea SummaryKeyword;
    private Archive archive =  Archive.archiveFile2;


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayGUI();
    }

    public void displayGUI(){
        metadata.setText("Click on one of the articles above to see more details");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        categories.setValue(" All categories");
        categories.getItems().addAll(archive.getPossibleCategories());
        period.setValue(LocalDate.now().minusYears(50));
        displayArticles();
    }

    public void displayArticles(){
        listView.getItems().clear();
        for(Article article : archive.getSelectedArticles()){
            listView.getItems().add(article.mainInformations());
        }
    }


    @FXML
    public void displayMetadata() {
        int index = listView.getSelectionModel().getSelectedIndex();
        metadata.setText(archive.getSelectedArticle(index).toString());
        favorite.setSelected(archive.getSelectedArticle(index).isFavoriteItem());
    }

    @FXML
    public void updateFavoriteItem(){
        int index = listView.getSelectionModel().getSelectedIndex();
        archive.getSelectedArticle(index).changeFavoriteItem();
    }

    @FXML
    public void downloadArticle(){
        int index = listView.getSelectionModel().getSelectedIndex();
        archive.getArticle(index).download();
    }

    @FXML
    public void downloadSelectedArticles(){
        archive.downloadArticles(archive.getSelectedArticles());
    }


    @FXML
    public void applyFilter(){
        metadata.setText("Click on one of the articles above to see more details");
        archive.selectAll();
        archive.categoryFilter(categories.getValue());
        archive.keyWordFilter(TitleKeyword.getText(), "title");
        archive.keyWordFilter(SummaryKeyword.getText(), "summary");
        archive.authorFilter(authors.getText());
        archive.dateFilter(period.getValue().toString());
        displayArticles();
    }
}
