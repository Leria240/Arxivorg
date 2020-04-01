package app.arxivorg.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ArxivOrgControllerTest {

    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().
                getResource("/app/arxivorg/view/arxivorg.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }

    @Test
    public void displayArticlesTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        Assertions.assertThat(listView).isNotEmpty();
        Assertions.assertThat(listView).hasExactlyNumItems(1000);
        Assertions.assertThat(listView).isVisible();
    }

    @Test
    public void initListOfArticlesTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        TextArea metadata = robot.lookup("#metadata").query();
        Button results = robot.lookup("#results").queryButton();

        Assertions.assertThat(metadata).hasText("Click on one of the articles above to see more detail");
        Assertions.assertThat(listView).hasExactlyNumItems(1000);
        Assertions.assertThat(listView.getSelectionModel().getSelectionMode()).isEqualTo(SelectionMode.MULTIPLE);
        Assertions.assertThat(results).isVisible();
        Assertions.assertThat(results).hasText("Show results");
    }


    @Test
    private void displayFilterTest(){
    }



    @Test
    private void applyFilterTest(){
    }

    @Test
    public void displayDetailsTest(FxRobot robot){
        ListView<String> listView = robot.lookup("#listView").queryListView();
        robot.clickOn(listView.getItems().get(2));
        assertEquals(6,listView.getSelectionModel().getSelectedIndices());
    }

    @Test
    public void downloadArticleTest(){
    }


}