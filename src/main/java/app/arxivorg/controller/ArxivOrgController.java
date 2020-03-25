package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private Button helloWorldButton;
    @FXML private Button goodByeWorldButton;
    @FXML private Label label;
    @FXML private ListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayArticles();
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
    public  void displayArticles(){
        Archive archive =  new Archive();
        archive.addArticles(new File("atomFile2.xml"));
        for(Article article : archive.getArticles()){
            String title = article.getTitle();
            String authors = "Authors: " + article.getAuthors().toString();
            String id = "ArXiv: " + article.getId().substring(21);
            listView.getItems().add("- " + title + "\n\t" + authors + "\n\t" + id);
        }
    }
}
