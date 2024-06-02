package fr.ul.miage.weiss.s8_projet_poo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import java.util.Map;

/**
 * Classe HistoriqueController, qui permet de gérer l'historique de la simulation.
 * @author Lucas WEISS
 */
public class HistoriqueController {

    /**
     * Modèle de la simulation.
     */
    private Modele modele;

    /**
     * Axe des abscisses du graphique.
     */
    @FXML
    private NumberAxis yAxis;

    /**
     * Graphique représentant l'historique de la simulation.
     */
    @FXML
    private LineChart<Integer, Integer> lineChart;

    /**
     * Texte affichant le volume total d'eau ajouté par les robinets.
     */
    @FXML
    private Text text_volume;

    /**
     * Texte affichant le volume total d'eau retiré par les fuites.
     */
    @FXML
    private Text text_fuite;

    /**
     * Bouton permettant d'exporter les données de l'historique.
     */
    @FXML
    private Button button_export;

    /**
     * Constructeur de la classe.
     * @param modele
     */
    public HistoriqueController(Modele modele) {
        this.modele = modele;
    }

    /**
     * Méthode appelée à l'initialisation de la vue.
     */
    @FXML
    public void initialize() {
        XYChart.Series<Integer, Integer> series = new XYChart.Series<>();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(modele.getCapaciteBaignoire());
        series.setName("Volume/Temps");
        lineChart.getData().add(series);
        updateChart(series);
        text_fuite.setText(String.valueOf(modele.volumeEauTotalFuiteProperty().get()));
        text_volume.setText(String.valueOf(modele.volumeEauTotalRobinetProperty().get()));
        modele.dureeSimulationProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                series.getData().clear();
                updateChart(series);
            });
        });
        modele.volumeEauTotalRobinetProperty().addListener((observable, oldValue, newValue) -> {
            text_volume.setText(newValue.toString());
        });
        modele.volumeEauTotalFuiteProperty().addListener((observable, oldValue, newValue) -> {
            text_fuite.setText(newValue.toString());
        });
    }

    /**
     * Méthode qui met à jour le graphique selon les données
     * @param series données de la simulation
     */
    private void updateChart(XYChart.Series<Integer, Integer> series) {
        for (Map.Entry<Integer, Integer> entry : modele.getHistorique().entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Méthode permettant d'exporter les données de l'historique.
     */
    @FXML
    public void exporterDonnees() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter les données");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("historique.csv");
        modele.exporterDonnees(fileChooser.showSaveDialog(null));
    }

}
