package app.arxivorg.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
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
}