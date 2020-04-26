package app.arxivorg.Controller;

import app.arxivorg.controller.ArxivOrgController;
import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
class ArxivOrgControllerTest {
    public Archive archive = new Archive();
    int numberOfArticles = archive.getAllArticles().size();



    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().
                getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }
/*
    @Test
    public void displayGUITest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea metadata = robot.lookup("#metadata").query();
        ChoiceBox<String> categories = robot.lookup("#categories").query();
        DatePicker period = robot.lookup("#period").query();
        int nbOfCategories = archive.getPossibleCategories().size();

        Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more details");
        Assertions.assertThat(listView.getSelectionModel().getSelectionMode()).isEqualTo(SelectionMode.MULTIPLE);
        Assertions.assertThat(categories.getValue()).isEqualTo(" All categories");
        Assertions.assertThat(categories.getItems().size()).isEqualTo(nbOfCategories);
        Assertions.assertThat(period.getValue()).isEqualTo(LocalDate.now().minusYears(50));
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);
    }



    @Test
    public void displayArticlesTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        Button results = robot.lookup("#results").queryButton();
        String selectedArticle1_info = archive.getSelectedArticle(0).mainInformations();
        String selectedArticle2_info = archive.getSelectedArticle(1).mainInformations();
        String selectedArticle7_info = archive.getSelectedArticle(7).mainInformations();

        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);

        archive.getArticle(7).setSelected(false);
        archive.getArticle(34).setSelected(false);
        robot.clickOn(results);

        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles - 2);
        Assertions.assertThat(listView.getItems().get(0)).isEqualTo(selectedArticle1_info);
        Assertions.assertThat(listView.getItems().get(1)).isEqualTo(selectedArticle2_info);
        Assertions.assertThat(listView.getItems().get(7)).isNotEqualTo(selectedArticle7_info);

        archive.selectAll();
        robot.clickOn(results);
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);

    }

/*
    @Test
    public void displayMetadataTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea metadata = robot.lookup("#metadata").query();
        CheckBox favorite = robot.lookup("#favorite").query();


        listView.getSelectionModel().select(12);
        Assertions.assertThat(listView.getSelectionModel().getSelectedIndex()).isEqualTo(12);
        Assertions.assertThat(metadata).hasText(archive.getArticle(12).toString());
        Assertions.assertThat(favorite.isIndeterminate()).isNotEqualTo(archive.getArticle(12).isSelected());

        listView.getSelectionModel().select(0);
        Assertions.assertThat(listView.getSelectionModel().getSelectedIndex()).isEqualTo(0);
        Assertions.assertThat(metadata).hasText(archive.getArticle(0).toString());
        //robot.clickOn(favorite);
        Assertions.assertThat(favorite.isIndeterminate()).isNotEqualTo(archive.getArticle(0).isSelected());

    }


    @Test
    public void updateFavoriteItemTest(){

    }


    @Test
    public void downloadArticleTest(){

    }


    @Test
    public void applyFilterTest(FxRobot robot){
        TextArea metadata = robot.lookup("#metadata").query();
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea keywords = robot.lookup("#keywords").query();
        Button results = robot.lookup("#results").queryButton();

        keywords.setText("void");
        //robot.clickOn(results);

        List<Article> articleSelected = new ArrayList<>();
        for(Article article: archive.getAllArticles()){
            if(article.isSelected()){
                articleSelected.add(article);
            }
        }
        Assertions.assertThat(listView).hasExactlyNumItems(articleSelected.size());
        Assertions.assertThat(listView.getItems().get(0)).
                isEqualTo(articleSelected.get(0).mainInformations());
        Assertions.assertThat(listView.getItems().get(articleSelected.size()-1)).
                isEqualTo(articleSelected.get(articleSelected.size()-1).mainInformations());
        //Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more details");

        keywords.clear();
        //robot.clickOn(results);
    }

 */

    @Test
    public void updateXMLDocumentUserDataTest(FxRobot robot) {
        ArxivOrgController arxivOrgController = new ArxivOrgController();
        arxivOrgController.addToFavorites(0);
        arxivOrgController.addToFavorites(10);
        arxivOrgController.getPreviousFavorites();
        arxivOrgController.updateXMLDocumentUserData();
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(new File("userData.xml"));
            final Element racine = document.getDocumentElement();
            final String favoriteArticle = racine.getElementsByTagName("favouriteArticle").item(0).getTextContent().trim();
            Assertions.assertThat (favoriteArticle).isEqualTo(archive.getArticle(0).getId().substring(0,31) +"\n"
                                            + archive.getArticle(0).getTitle() + "\n"
                                            + archive.getArticle(0).getURL_PDF());

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPreviousFavouritesTest(){
        ArxivOrgController arxivOrgController = new ArxivOrgController();
        arxivOrgController.addToFavorites(0);
        arxivOrgController.addToFavorites(10);
        arxivOrgController.getPreviousFavorites();
        assert (archive.getArticle(0).isFavoriteItem());
        assert (archive.getArticle(10).isFavoriteItem());
        assert (!archive.getArticle(2).isFavoriteItem());
    }

    @Test
    public void addToFavoritesTest(){
        ArxivOrgController arxivOrgController = new ArxivOrgController();
        arxivOrgController.addToFavorites(0);
        arxivOrgController.addToFavorites(10);
        assert (ArxivOrgController.favoriteArticles.containsValue(archive.getArticle(0).getId().substring(0,31)));
        assert (ArxivOrgController.favoriteArticles.containsValue(archive.getArticle(10).getId().substring(0,31)));
    }


}