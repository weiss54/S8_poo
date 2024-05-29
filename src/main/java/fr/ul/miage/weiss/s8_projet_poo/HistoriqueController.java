package fr.ul.miage.weiss.s8_projet_poo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Map;

public class HistoriqueController {

    private Modele modele;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<Integer, Integer> lineChart;

    public HistoriqueController(Modele modele) {
        this.modele = modele;
    }

    @FXML
    public void initialize() {
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(modele.getCapaciteBaignoire());
        series.setName("Volume/Temps");
        lineChart.getData().add(series);
        modele.dureeSimulationProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                // Clear the old data
                series.getData().clear();
                // Update the chart with new data
                updateChart(series);
            });
        });
    }

    private void updateChart(XYChart.Series<Integer, Integer> series) {
        for (Map.Entry<Integer, Integer> entry : modele.getHistorique().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
    }




}
