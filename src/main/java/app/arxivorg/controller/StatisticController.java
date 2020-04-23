package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Statistic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    @FXML public BarChart<String,Integer> barChart;
    @FXML public CategoryAxis categoryAxis;
    @FXML public NumberAxis numberAxis;
    @FXML public VBox statistics;
    @FXML public Button author;
    @FXML public Button date;
    @FXML public ListView<String> expressionsList;
    @FXML public Button expressions;
    @FXML public PieChart authorsPieChart;
    @FXML public VBox authorStatistic;


    public Archive archive;
    private Statistic statistic = new Statistic();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void displayCategoryBarChart() {
        XYChart.Series<String,Integer> setOfCategories = new XYChart.Series<>();
        Map<String, Integer> categoriesData = statistic.countArticlesByCategory(archive);
        for (Map.Entry<String,Integer> data : categoriesData.entrySet()) {
            setOfCategories.getData().add(new XYChart.Data<>(data.getKey(),data.getValue()));
        }
        barChart.getData().add(setOfCategories);
        statistics.setVisible(false);
        barChart.setVisible(true);
    }

    public void displayAuthorStatistic(){
        int nbAuthors_max = 15;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String,Integer> authorsData = statistic.mostProductiveAuthors(archive);
        for(Map.Entry<String,Integer> data : authorsData.entrySet()){
            pieChartData.add(new PieChart.Data(data.getKey() + " (" + data.getValue() + ")",data.getValue()));
            nbAuthors_max --;
            if(nbAuthors_max == 0) break;
            System.out.println(data.getKey() + " " + data.getValue());
        }
        authorsPieChart.setData(pieChartData);
        statistics.setVisible(false);
        authorStatistic.setVisible(true);
    }




    public void displayDateStatistic(){

    }

    public void displayExpressionsStatistic(){}

}