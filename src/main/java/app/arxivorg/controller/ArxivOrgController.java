package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private Button helloWorldButton;
    @FXML private Button goodByeWorldButton;
    @FXML private Label label;
    @FXML private ListView<String> listView;
    @FXML private TextArea metadata;
    @FXML private CheckBox favorite;
    @FXML private Button download;
    @FXML private ChoiceBox<String> categories;
    @FXML private DatePicker period;
    @FXML private TextArea authors;
    @FXML private TextArea TitleKeyword;
    @FXML private TextArea SummaryKeyword;
    @FXML private Button results;
    @FXML private Button downloadAll;
    private Archive archive =  Archive.archiveFile2;


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        initListOfArticles();
        displayFilter();
    }

    @FXML
    private void displayHelloWorld() {
        label.setText("Hello World");
        helloWorldButton.setVisible(false);
        if (!goodByeWorldButton.isVisible())
            goodByeWorldButton.setVisible(true);
    }

    @FXML
    private void goodByeWorld() {
        label.setText("");
        goodByeWorldButton.setVisible(false);
        if (!helloWorldButton.isVisible())
            helloWorldButton.setVisible(true);
    }

    @FXML
    public void initListOfArticles(){
        metadata.setText("Click on one of the articles above to see more details");
        displayArticles();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedIndexProperty().addListener(observable ->
                displayMetadata(listView.getSelectionModel().getSelectedIndex()));
    }

    @ FXML
    public void displayArticles(){
        listView.getItems().clear();
        for(Article article : archive.getSelectedArticles()){
            listView.getItems().add(article.mainInformations());
        }
    }

    @FXML
    public void displayFilter(){
        categories.setValue(" All categories");
        categories.getItems().addAll(archive.getPossibleCategories());
        period.setValue(LocalDate.now().minusYears(50));
        results.setOnAction(actionEvent -> applyFilter());
    }


    @FXML
    public void displayMetadata(int index) {
        metadata.setText(archive.getSelectedArticle(index).toString());
        favorite.setSelected(archive.getSelectedArticle(index).isFavoriteItem());
        favorite.setOnAction(updateFavoriteItem(index));
        download.setOnAction(downloadArticle(index));
    }

    public EventHandler<ActionEvent> updateFavoriteItem(int index){
        return actionEvent -> archive.getSelectedArticle(index).changeFavoriteItem();
    }

    public EventHandler<ActionEvent> downloadArticle(int index){
        return actionEvent -> archive.getArticle(index).download();
    }

    @FXML
    public void downloadSelectedArticles(){
        List<Integer> indices = listView.getSelectionModel().getSelectedIndices();
        List<Article> articles = new ArrayList<>();
        for(Integer index: indices){
            articles.add(archive.getSelectedArticle(index));
        }
        archive.downloadArticles(articles);
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
