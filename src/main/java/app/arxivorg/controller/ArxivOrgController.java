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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

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
        getPreviousFavorites();

    }

    //Creation XML document
    static Element racine = new Element("user");
    static org.jdom2.Document document = new Document(racine);
    static Map<String, String> favoritesArticles = new HashMap<>();

    public void getPreviousFavorites() {
        //Cette méthode ajoute les favoris de la session précédente à la Map favoritesArticles
        //afin de pouvoir les conserver chaque fois qu'on utilise l'interface graphique
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File("userData.xml");
            org.w3c.dom.Document document = builder.parse(file);
            NodeList article = document.getElementsByTagName("favouriteArticle");

            for (int i = 0; i < article.getLength(); i++){
                org.w3c.dom.Element articlei = (org.w3c.dom.Element) article.item(i);
                String string = articlei.getElementsByTagName("id").item(0).getTextContent();
                favoritesArticles.put("id "+i, string);
            }
            //On ajoute les articles dans userData.xml au favoris
            Collection<String> values = favoritesArticles.values();
            Iterator<String> it = values.iterator();
            for (; it.hasNext(); ) {
                String s = it.next();
                for (int i = 0; i< archive.getSelectedArticles().size(); i++) {
                    if (archive.getSelectedArticle(i).getId().substring(0, 31).equals(s)) {
                        archive.getSelectedArticle(i).changeFavoriteItem();
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void addToFavorites(int i) {
        if (!favoritesArticles.containsValue(archive.getSelectedArticle(i).getId().substring(0, 31))){
            favoritesArticles.put("id" + i, archive.getSelectedArticle(i).getId().substring(0, 31));
        }
    }

    @FXML
    public void updateXMLDocumentUserData(){
        racine.removeContent();
        //The last connexion date
        LocalDate theDate = LocalDate.now();

        //Save the last connection date in the XML file
        Element lastConnexionDate = new Element("LastConnexionDate");
        lastConnexionDate.setText(String.valueOf(theDate));
        racine.addContent(lastConnexionDate);

        for (int i = 0; i< archive.getSelectedArticles().size(); i++){
            if (archive.getSelectedArticle(i).isFavoriteItem()){
                Element id_articles = new Element("id");
                Element article = new Element("favouriteArticle");
                Element articleTitle = new Element("title");
                Element url_pdf = new Element("link");

                id_articles.addContent(archive.getSelectedArticle(i).getId().substring(0,31));
                articleTitle.addContent(archive.getSelectedArticle(i).getTitle());
                url_pdf.addContent(String.valueOf(archive.getSelectedArticle(i).getURL_PDF()));
                article.addContent(id_articles);
                article.addContent(articleTitle);
                article.addContent(url_pdf);

                racine.addContent(article);
            }
        }
        save();
    }

    static void save() {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream("userData.xml"));
            System.out.println("File updated !");

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
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
        metadata.setText(archive.getSelectedArticle(index).toString());
        favorite.setSelected(archive.getSelectedArticle(index).isFavoriteItem());
    }

    @FXML
    public void updateFavoriteItem(){
        int index = listView.getSelectionModel().getSelectedIndex();;
        archive.getSelectedArticle(index).changeFavoriteItem();
        addToFavorites(index);
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
