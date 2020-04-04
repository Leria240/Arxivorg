package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Article;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(ApplicationExtension.class)
class ArxivOrgControllerTest {

    Archive archive = Archive.archiveFile2;
    int numberOfArticles = archive.getArticles().size();
    ArxivOrgController controller = new ArxivOrgController();



    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().
                getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }

    @Test
    public void initListOfArticlesTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea metadata = robot.lookup("#metadata").query();

        Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more details");
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);
        Assertions.assertThat(listView.getSelectionModel().getSelectionMode()).isEqualTo(SelectionMode.MULTIPLE);
    }



    @Test
    public void displayArticlesTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        Button results = robot.lookup("#results").queryButton();
        TextArea keywords = robot.lookup("#keywords").query();

        keywords.setText("void");

        int numberOfArticlesSelected = 0;
        for(Article article: archive.getArticles()){
            if(article.isSelected()){
                numberOfArticlesSelected++;
            }
        }

        Assertions.assertThat(listView).isVisible();
        Assertions.assertThat(listView).isNotEmpty();
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticlesSelected);
        //Assertions.assertThat(listView).doesNotHaveExactlyNumItems(numberOfArticles);
        keywords.clear();
        robot.clickOn(results);
        Assertions.assertThat(listView).hasExactlyNumItems(numberOfArticles);
    }


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
        robot.clickOn(favorite);
        Assertions.assertThat(favorite.isIndeterminate()).isNotEqualTo(archive.getArticle(0).isSelected());

    }

    @Test
    public void displayFilterTest(FxRobot robot){

        ChoiceBox<String> categories = robot.lookup("#categories").query();
        Button results = robot.lookup("#results").queryButton();
        DatePicker period = robot.lookup("#period").query();
        TextArea keywords = robot.lookup("#keywords").query();
        TextArea authors = robot.lookup("#authors").query();
        TextArea metadata = robot.lookup("#metadata").query();
        int numOfCategories = archive.getPossibleCategories().size();

        Assertions.assertThat(robot.lookup("#results").query().isVisible());
        Assertions.assertThat(categories.getItems()).hasSize(numOfCategories);
        Assertions.assertThat(categories.getValue()).isEqualTo(" All categories");
        Assertions.assertThat(period.getValue()).isEqualTo(LocalDate.now());
        Assertions.assertThat(keywords).isVisible();
        Assertions.assertThat(keywords.getText()).isEmpty();
        Assertions.assertThat(authors).isVisible();
        Assertions.assertThat(authors.getText()).isEmpty();

        //robot.clickOn(results);
        //Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more details");
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
        for(Article article: archive.getArticles()){
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
        robot.clickOn(results);
    }






}