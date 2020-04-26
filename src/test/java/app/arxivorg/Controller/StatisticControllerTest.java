package app.arxivorg.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class StatisticControllerTest {


    @Start
    public void start(Stage stage) throws Exception{
        Parent mainNode = FXMLLoader.load(getClass().getResource("/app/arxivorg/view/statistic.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
    }

    @Test
    void testDisplayCategoryBarChart(FxRobot robot) {
        VBox statistics = robot.lookup("#statistics").query();
        VBox categoryStatistic = robot.lookup("#categoryStatistic").query();
        robot.clickOn("#category");
        //Assertions.assertFalse(statistics.isVisible());
        //Assertions.assertTrue(categoryStatistic.isVisible());
    }

    @Test
    void displayAuthorStatistic(FxRobot robot) {
        VBox statistics = robot.lookup("#statistics").query();
        VBox authorStatistic = robot.lookup("#authorStatistic").query();
        robot.clickOn("#author");
        //Assertions.assertFalse(statistics.isVisible());
        //Assertions.assertTrue(authorStatistic.isVisible());
    }

    @Test
    void testDisplayDateStatistic(FxRobot robot) {
        VBox statistics = robot.lookup("#statistics").query();
        VBox dateStatistic = robot.lookup("#dateStatistic").query();
        robot.clickOn("#date");
        //Assertions.assertFalse(statistics.isVisible());
        //Assertions.assertTrue(dateStatistic.isVisible());
    }

    @Test
    void testdDisplayExpressionsStatistic(FxRobot robot) {
        VBox statistics = robot.lookup("#statistics").query();
        VBox expressionStatistic = robot.lookup("#expressionStatistic").query();
        robot.clickOn("#expression");
        //Assertions.assertFalse(statistics.isVisible());
        //Assertions.assertTrue(dateStatistic.isVisible());
    }

    @Test
    void displayStatisticGUI() {
    }

    @Test
    void deleteExpression(FxRobot robot) {
        ListView listExpressions = robot.lookup("#listExpressions").query();
        listExpressions.getItems().add("Test");
        org.testfx.assertions.api.Assertions.assertThat(listExpressions).hasExactlyNumItems(1);
        robot.clickOn(listExpressions);
        robot.clickOn("#delete");
        //org.testfx.assertions.api.Assertions.assertThat(listExpressions).hasExactlyNumItems(0);
    }

    @Test
    void addExpression(FxRobot robot) {
        TextArea textAreaExpression = robot.lookup("#textAreaExpression").query();
        ListView listExpressions = robot.lookup("#listExpressions").query();
        textAreaExpression.setText("programming");
        org.testfx.assertions.api.Assertions.assertThat(listExpressions).hasExactlyNumItems(0);
        robot.clickOn("#save");
        //assertFalse(listExpressions.getItems().isEmpty());
        //org.testfx.assertions.api.Assertions.assertThat(listExpressions).hasExactlyNumItems(1);
    }

}