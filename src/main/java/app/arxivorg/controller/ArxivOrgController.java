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
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Archive archive = new Archive();


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        displayGUI();
        createXMLDocumentUserData();
    }

    //Creation XML document
    static Element racine = new Element("user");
    static org.jdom2.Document document = new Document(racine);

    public void createXMLDocumentUserData(){

        //The last connexion date
        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.now();
        String theDate = date.format(currentDate);

        //Save the last connection date in the XML file
        Element lastConnexionDate = new Element("LastConnexionDate");
        lastConnexionDate.setText(theDate);
        racine.addContent(lastConnexionDate);

        //The archive containing all the articles

        //Save the favorites articles in the XML file
        Element Favorites = new Element("Favorites");
        for(int i = 0; i < archive.getAllArticles().size(); i++){
            if(archive.getSelectedArticle(i).isFavoriteItem()){
                Element article = new Element("article");
                Element articleTitle = new Element("title");
                Element url_pdf = new Element("link");

                articleTitle.addContent(archive.getArticle(i).getTitle());
                url_pdf.addContent(String.valueOf(archive.getArticle(i).getURL_PDF()));
                article.addContent(articleTitle);
                article.addContent(url_pdf);

                Favorites.addContent(article);
            }
        }
        racine.addContent(Favorites);

        display();
        save();

        System.out.println("File updated !");
    }

    static void display() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    static void save() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream("userData.xml"));

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void displayGUI(){
        metadata.setText("Click on one of the articles above to see more details");
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        categories.setValue(" All categories");
        categories.getItems().addAll(archive.getAllCategories());
        period.setValue(LocalDate.now().minusYears(50));
        displayArticles();
    }

    public void displayArticles(){
        listView.getItems().clear();
        for(Article article : archive.getAllArticles()){
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

    public void displayStatistics(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/arxivorg/view/statistic.fxml"));
            Parent root = loader.load();
           StatisticController secController = loader.getController();
            secController.archive = archive;
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void displayMetadata() {
        int index = listView.getSelectionModel().getSelectedIndex();
        metadata.setText(archive.getArticle(index).toString());
        favorite.setSelected(archive.getArticle(index).isFavoriteItem());
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
