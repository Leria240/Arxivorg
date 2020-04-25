package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Statistic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class StatisticController implements Initializable {

    @FXML public BarChart<String,Integer> barChart;
    @FXML public Button author;
    @FXML public Button date;
    @FXML public PieChart authorsPieChart;
    @FXML public VBox statistics;
    @FXML public VBox categoryStatistic;
    @FXML public VBox authorStatistic;
    @FXML public VBox expressionStatistic;
    @FXML public VBox dateStatistic;

    @FXML public LineChart<String,Integer> dateLineChart;

    @FXML public StackedBarChart<String, Integer> expressionStackedBarChart;
    @FXML public TextArea textAreaExpression;
    @FXML public ListView<String> listExpressions;







    public Archive archive;
    private Statistic statistic = new Statistic();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayStatisticGUI();
    }

    public void displayCategoryBarChart() {
        barChart.getData().clear();
        Series<String,Integer> setOfCategories = new Series<>();
        Map<String, Integer> categoriesData = statistic.countArticlesByCategory(archive);
        for (Map.Entry<String,Integer> data : categoriesData.entrySet()) {
            setOfCategories.getData().add(new XYChart.Data<>(data.getKey(),data.getValue()));
        }
        barChart.getData().add(setOfCategories);
        statistics.setVisible(false);
        categoryStatistic.setVisible(true);
    }

    public void displayAuthorStatistic(){
        authorsPieChart.getData().clear();
        int nbAuthors_max = 15;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String,Integer> authorsData = statistic.mostProductiveAuthors(archive);
        for(Map.Entry<String,Integer> data : authorsData.entrySet()){
            pieChartData.add(new PieChart.Data(data.getKey() + " (" + data.getValue() + ")",data.getValue()));
            nbAuthors_max --;
            if(nbAuthors_max == 0) break;
        }
        authorsPieChart.setData(pieChartData);
        statistics.setVisible(false);
        authorStatistic.setVisible(true);
    }



    public void displayDateStatistic(){
        Series<String, Integer> series2Data = new Series<>();
        Map<String,Integer> dateData = statistic.nbPublicationPerDay(archive);
        for(Map.Entry<String,Integer> data : dateData.entrySet()) {
            series2Data.getData().add(new XYChart.Data<>(data.getKey(), data.getValue()));
        }
        dateLineChart.getData().add(series2Data);
        statistics.setVisible(false);
        dateStatistic.setVisible(true);
    }


    public void displayExpressionsStatistic() {
        expressionStackedBarChart.getData().clear();
        Series<String, Integer> title = new Series<>();
        Series<String, Integer> summary = new Series<>();
        title.setName("Title");
        summary.setName("Summary");
        for(String expression: listExpressions.getItems()){
            List<Integer> data = statistic.nbExpressionsAppear(archive, expression);
            title.getData().add(new XYChart.Data<>(expression, data.get(0)));
            summary.getData().add(new XYChart.Data<>(expression, data.get(1)));
        }
        expressionStackedBarChart.getData().add(title);
        expressionStackedBarChart.getData().add(summary);
        statistics.setVisible(false);
        expressionStatistic.setVisible(true);
    }

    public void displayStatisticGUI(){
        statistics.setVisible(true);
        authorStatistic.setVisible(false);
        expressionStatistic.setVisible(false);
        categoryStatistic.setVisible(false);
        dateStatistic.setVisible(false);
        listExpressions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //textAreaExpression.setText("Enter an expression then push \nthe \"save expression\" button");
    }


    public void deleteExpression(){
        int index = listExpressions.getSelectionModel().getSelectedIndex();
        listExpressions.getItems().remove(index);
    }


    public void addExpression(){
        String expression = textAreaExpression.getText();
        listExpressions.getItems().add(expression);
        textAreaExpression.clear();
    }

}