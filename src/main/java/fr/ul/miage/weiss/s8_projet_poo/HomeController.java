package fr.ul.miage.weiss.s8_projet_poo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Stack;

public class HomeController {

    @FXML
    private Text text_simulation;

    @FXML
    private HBox pane_robinets, pane_fuites;

    @FXML
    private Text text_libelle_baignoire, text_libelle_volume;

    @FXML
    private TextField input_capacite_baignoire;

    @FXML
    private Button button_robinet_moins, button_robinet_plus, button_fuite_moins, button_fuite_plus, button_start, button_stop, button_export;

    private Stack<FuiteController> fuites = new Stack<>();
    private Stack<RobinetController> robinets = new Stack<>();


    @FXML
    public void initialize() {
        affichage_mode_simulation(false);
    }

    public void affichage_mode_simulation(boolean mode_simulation) {
        if (mode_simulation) {
            text_simulation.setText("Mode simulation activé");
            text_libelle_baignoire.setText("Volume/Capacité");
        } else {
            text_simulation.setText("Mode simulation désactivé");
            text_libelle_baignoire.setText("Capacité?");
        }
        button_fuite_moins.setDisable(mode_simulation);
        button_fuite_plus.setDisable(mode_simulation);
        button_robinet_moins.setDisable(mode_simulation);
        button_robinet_plus.setDisable(mode_simulation);
        button_start.setDisable(mode_simulation);
        button_stop.setDisable(!mode_simulation);
        button_export.setDisable(mode_simulation);
        for (RobinetController robinet : robinets) {
            robinet.visibiliteSimulation(mode_simulation);
        }
        for (FuiteController fuite : fuites) {
            fuite.visibiliteSimulation(mode_simulation);
        }
        text_libelle_volume.setVisible(mode_simulation);
        input_capacite_baignoire.setVisible(!mode_simulation);
    }

    @FXML
    public void action_start() {
        affichage_mode_simulation(true);
    }

    public void action_stop() {
        affichage_mode_simulation(false);
    }

    @FXML
    public void action_robinet_moins() {
        if (Robinet.isSuppressionPossible()) {
            RobinetController robinetController = robinets.pop();
            robinetController.suppression();
            this.pane_robinets.getChildren().removeLast();
        }
    }

    @FXML
    public void action_robinet_plus() {
        if (Robinet.isAjoutPossible()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("robinet.fxml"));
                Parent pane = fxmlLoader.load();
                robinets.push(fxmlLoader.getController());
                this.pane_robinets.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    public void action_fuite_moins() {
        if (Fuite.isSuppressionPossible()) {
            FuiteController fuiteController = fuites.pop();
            fuiteController.suppression();
            this.pane_fuites.getChildren().removeLast();
        }
    }

    @FXML
    public void action_fuite_plus() {
        if (Fuite.isAjoutPossible()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("fuite.fxml"));
                Pane pane = fxmlLoader.load();
                fuites.push(fxmlLoader.getController());
                this.pane_fuites.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
