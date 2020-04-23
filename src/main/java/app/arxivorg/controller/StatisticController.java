package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Statistic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    @FXML public BarChart<String,Integer> barChart;
    @FXML public CategoryAxis categoryAxis;
    @FXML public NumberAxis numberAxis;
    @FXML public VBox statistics;


    public Archive archive;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void displayCategoryBarChart() {
        Statistic statistic = new Statistic();
        XYChart.Series<String,Integer> setOfCategories = new XYChart.Series<>();
        Map<String, Integer> categoriesData = statistic.countArticlesByCategory(archive);
        System.out.print(categoriesData.toString());
        for (Map.Entry<String,Integer> data : categoriesData.entrySet()) {
            setOfCategories.getData().add(new XYChart.Data<String,Integer>(data.getKey(),data.getValue()));
        }
        barChart.getData().add(setOfCategories);
        statistics.setVisible(false);
        barChart.setVisible(true);
    }

    public void displayAuthorStatistic(){

    }

    public void displayDateStatistic(){

    }

    public void displayExpressionsStatistic(){}

}
