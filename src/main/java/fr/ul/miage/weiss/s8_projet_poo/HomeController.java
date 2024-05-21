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

    private Modele modele;

    public HomeController(Modele modele) {
        this.modele = modele;
    }

    @FXML
    public void initialize() {
        modele.getPropertyVolumeRempliBaignoire().addListener((observable, oldValue, newValue) -> {
            text_libelle_volume.setText(newValue.toString());
        });
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
        modele.demarrerSimulation();
        //TODO on doit passer par le modele
        affichage_mode_simulation(true);
    }

    public void action_stop() {
        modele.arreterSimulation();
        //TODO on doit passer par le modele
        affichage_mode_simulation(false);
    }

    @FXML
    public void action_robinet_moins() {
        boolean res = modele.supprimerRobinet();
        if (res) {
            RobinetController robinetController = robinets.pop();
            robinetController.suppression();
            this.pane_robinets.getChildren().removeLast();
        }
    }

    @FXML
    public void action_robinet_plus() {
        Robinet r = modele.ajouterRobinet();
        if (r != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("robinet.fxml"));
                fxmlLoader.setControllerFactory(param -> new RobinetController(r));
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
        boolean res = modele.supprimerFuite();
        if (res) {
            FuiteController fuiteController = fuites.pop();
            fuiteController.suppression();
            this.pane_fuites.getChildren().removeLast();
        }
    }

    @FXML
    public void action_fuite_plus() {
        Fuite f = modele.ajouterFuite();
        if (f != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("fuite.fxml"));
                fxmlLoader.setControllerFactory(param -> new FuiteController(f));
                Pane pane = fxmlLoader.load();
                fuites.push(fxmlLoader.getController());
                this.pane_fuites.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
