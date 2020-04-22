package app.arxivorg.controller;

import app.arxivorg.model.Archive;
import app.arxivorg.model.Statistic;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticController implements Initializable {

    @FXML
    public BarChart<Integer,String> barChart;

    @FXML
    public CategoryAxis categoryAxis;

    @FXML
    public NumberAxis numberAxis;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void fillStatisticData(Archive archive) {
        Statistic statistic = new Statistic();
        XYChart.Series setOfCategories = new XYChart.Series();
        Map<String, Integer> categoriesData = statistic.countArticlesByCategory(archive);
        System.out.print(categoriesData.toString());
        for (Map.Entry data : categoriesData.entrySet()) {
            setOfCategories.getData().add(new XYChart.Data(data.getKey(),data.getValue()));
        }
        barChart.getData().add(setOfCategories);
    }

}
