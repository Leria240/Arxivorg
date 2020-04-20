package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import app.arxivorg.model.Authors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ArxivOrgController implements Initializable {

    @FXML private ListView<VBox> listView;
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
            listView.getItems().add(constructCell(article));
        }
    }

    public VBox constructCell(Article article){
        VBox cell = new VBox();

        GridPane titleGridPane = new GridPane();
        Label title = new Label("- " + article.getTitle());
        titleGridPane.add(title,0,0);

        Authors authors = article.getAuthors();
        GridPane authorsGridPane = new GridPane();
        Label tabulation = new Label("\t");
        authorsGridPane.add(tabulation,0,1);
        int nbOfAuthors = authors.getData().size();
        for(int i=0; i<nbOfAuthors;i++){
            Hyperlink author = authors.getDataLink().get(i);
            author.setOnAction(actionEvent -> displayAuthorArticles(author.getText()));
            authorsGridPane.add(author,i+1,1);
        }

        GridPane idGridPane = new GridPane();
        Label id = new Label("\tArXiv: " + article.getId().substring(21));
        idGridPane.add(id,0,2);

        cell.getChildren().add(titleGridPane);
        cell.getChildren().add(authorsGridPane);
        cell.getChildren().add(idGridPane);

        return cell;
    }


    public void displayAuthorArticles(String authorName){
        try {
            System.out.println(authorName+" ok");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/arxivorg/view/authorsArticles.fxml"));
            Parent root = loader.load();
            AuthorsArticlesController secController = loader.getController();
            secController.displayArticles(authorName);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public EventHandler handleLinks() {
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                Parent parent = null;
                try {
                    parent = FXMLLoader.load(getClass().getResource("AuthorsArticles.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(parent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();

            }
        };
        return eventHandler;
    }

     */


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
    public void downloadSelectedArticles() throws IOException {
        archive.downloadArticles(archive.getSelectedArticles(), "");
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
