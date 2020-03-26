package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
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


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayArticles();
        selectArticles();
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
    public Archive displayArticles(){
        Archive archive =  new Archive();
        archive.addArticles(new File("atomFile2.xml"));
        for(Article article : archive.getArticles()){
            String title = article.getTitle();
            String authors = "Authors: " + article.getAuthors().toString();
            String id = "ArXiv: " + article.getId().substring(21);
            listView.getItems().add("- " + title + "\n\t" + authors + "\n\t" + id);
        }
        return archive;
    }

    @FXML
    public void selectArticles(){
        select.setText("Click on one of the articles above to see more detail");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedIndexProperty().addListener(observable -> {
            displayDetails(listView.getSelectionModel().getSelectedIndex());
        });
    }

    @FXML
    private void displayDetails(int index) {
        select.setVisible(false);
        Archive archive =displayArticles();
        articleDetails.setText(archive.getArticles().get(index).toString());
    }
}
