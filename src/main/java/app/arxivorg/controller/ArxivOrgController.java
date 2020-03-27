package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private Button helloWorldButton;
    @FXML private Button goodByeWorldButton;
    @FXML private Label label;
    @FXML private ListView<String> listView;
    @FXML private TextArea articleDetails;
    @FXML private TextField select;
    @FXML private CheckBox favorite;
    @FXML private Button download;
    @FXML private ChoiceBox<String> categories;
    @FXML private ChoiceBox<String> period;
    @FXML private TextArea authors;
    @FXML private TextArea keywords;
    @FXML private Button results;
    @FXML private Button downloadAll;
    private Archive archive =  new Archive();


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        initListOfArticles();
        selectArticles();
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
        archive.addArticles(new File("atomFile2.xml"));
        displayArticles();
        results.setOnAction(actionEvent -> applyFilter());
    }

    @ FXML
    public void displayArticles(){
        for(Article article : archive.getArticles()){
            if(article.isSelected()) {
                String title = article.getTitle();
                String authors = "Authors: " + article.getAuthors().toString();
                String id = "ArXiv: " + article.getId().substring(21);
                listView.getItems().add("- " + title + "\n\t" + authors + "\n\t" + id);
            }
        }
        System.out.println("Number of article: " + listView.getItems().size());
    }

    @FXML
    private void displayFilter(){
        categories.setValue(" All categories");
        period.setValue(" All period");
        categories.getItems().addAll(archive.possibleCategories());
        period.getItems().addAll(archive.possiblePeriod());
    }

    @FXML
    public void selectArticles(){
        select.setText("Click on one of the articles above to see more detail");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedIndexProperty().addListener(observable ->
                displayDetails(listView.getSelectionModel().getSelectedIndex()));
    }

    @FXML
    private void displayDetails(int index) {
        select.setVisible(false);
        articleDetails.setText(archive.getArticle(index).toString());
        favorite.setSelected(archive.getArticle(index).isFavoriteItem());
        favorite.setOnAction(updateFavoriteItem(index));
        download.setOnAction(downloadArticle(index));
    }

    public EventHandler<ActionEvent> updateFavoriteItem(int index){
        return actionEvent -> archive.getArticle(index).changeFavoriteItem();
    }

    public EventHandler<ActionEvent> downloadArticle(int index){
        return actionEvent -> archive.getArticle(index).download();
    }


    @FXML
    private void applyFilter(){
        archive.selectAll();
        archive.categoryFilter(categories.getValue());
        listView.getItems().clear();
        displayArticles();
    }


}
