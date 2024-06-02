package fr.ul.miage.weiss.s8_projet_poo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

/**
 * Classe HomeController
 * Cette classe est le contrôleur de la vue principale de l'application
 * @author Lucas WEISS
 */
public class HomeController {

    /* Attribut Text lié à la vue de cet écran, contient le texte qui indique le statut de la simulation (stop/en cours) */
    @FXML
    private Text text_simulation;

    /* Attribut HBox lié à la vue de cet écran, contient les écrans qui représentent les robinets */
    @FXML
    private HBox pane_robinets;

    /* Attribut HBox lié à la vue de cet écran, contient les écrans qui représentent les fuites */
    @FXML
    private HBox pane_fuites;

    /* Attribut Text lié à la vue de cet écran, contient le texte qui indique le type de la valeur affiche dans text_libelle_volume/input_capacite_baignoire */
    @FXML
    private Text text_libelle_baignoire;

    /* Attribut Text lié à la vue de cet écran, contient le texte qui indique le volume de la baignoire */
    @FXML
    private Text text_libelle_volume;

    /* Attribut Text lié à la vue de cet écran, contient le temps écoulé depuis le début de la simulation */
    @FXML
    private Text text_temps_simulation;

    /* Attribut TextField lié à la vue de cet écran, permet de changer la capacité de la baignoire */
    @FXML
    private TextField input_capacite_baignoire;

    /* Attribut Bouton lié à la vue de cet écran, permet de diminuer le débit d'un robinet/fuite */
    @FXML
    private Button button_robinet_moins, button_robinet_plus, button_fuite_moins, button_fuite_plus;

    /* Attribut Bouton lié à la vue de cet écran, permet de démarrer/arrêter la simulation et d'exporter les données */
    @FXML
    private Button button_start, button_stop, button_export;

    /* Attribut qui contient les écrans qui représentent les fuites */
    private Stack<FuiteController> fuites = new Stack<>();

    /* Attribut qui content les écrans qui représentent les robinets */
    private Stack<RobinetController> robinets = new Stack<>();

    /**
     * Attribut lié à la vue de cet écran, contient l'image du personnage qui apparaît à la fin de la simulation
     */
    @FXML
    private ImageView image_personnage;

    /**
     * Attribut lié à la vue de cet écran, contient le rectangle qui représente l'eau dans la baignoire
     */
    @FXML
    private Rectangle rectangle_eau;

    /* Attribut qui contient le modèle de l'application */
    private Modele modele;

    /**
     * Constructeur de la classe HomeController
     * @param modele modele de données de l'application
     */
    public HomeController(Modele modele) {
        this.modele = modele;
    }

    /**
     * Méthode appelée lors de l'initialisation de la vue
     */
    @FXML
    public void initialize() {
        text_temps_simulation.setText("0 seconde(s)");
        input_capacite_baignoire.setText(String.valueOf(modele.getCapaciteBaignoire()));
        input_capacite_baignoire.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                input_capacite_baignoire.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                modele.setCapaciteBaignoire(Integer.parseInt(input_capacite_baignoire.getText()));
            }
        });
        input_capacite_baignoire.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (input_capacite_baignoire.getText().isEmpty()) {
                    modele.setCapaciteBaignoire(100);
                    input_capacite_baignoire.setText("100");
                }
            }
        });
        rectangle_eau.setHeight(0);
        modele.getPropertyVolumeRempliBaignoire().addListener((observable, oldValue, newValue) -> {
            text_libelle_volume.setText(newValue.toString() + "/" + modele.getCapaciteBaignoire());
            rectangle_eau.setHeight(70.0 / modele.getCapaciteBaignoire() * modele.getPropertyVolumeRempliBaignoire().get());
        });
        modele.getSimulationEnCours().addListener((observable, oldValue, newValue) -> {
            affichage_mode_simulation(newValue);
        });
        modele.dureeSimulationProperty().addListener((observable, oldValue, newValue) -> {
            text_temps_simulation.setText(newValue + " seconde(s)");
        });
        modele.messageErreurProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                new AlerteView(newValue);
                modele.messageErreurProperty().set("");
            }
        });
        modele.simulationCompleteProperty().addListener((observable, oldValue, newValue) -> {
            image_personnage.setVisible(newValue);
            if (newValue) {
                text_simulation.setText("Simulation terminée");
            }
        });
        affichage_mode_simulation(false);
    }

    /**
     * Méthode qui permet de changer l'affichage de la vue en fonction du mode de la simulation
     * Dans la première partie, on change le text qui s'affiche
     * Dans la deuxième partie, on affiche/cache les composants qui permettent d'afficher la vue en fonction des besoins
     * @param mode_simulation indique quel est le mode de la simulation (en cours ou non)
     */
    public void affichage_mode_simulation(boolean mode_simulation) {
        rectangle_eau.setHeight(0);
        // Partie 1
        if (mode_simulation) {
            text_simulation.setText("Mode simulation activé");
            text_libelle_baignoire.setText("Volume/Capacité");
        } else {
            text_simulation.setText("Mode simulation désactivé");
            text_libelle_baignoire.setText("Capacité?");
        }
        // Partie 2
        text_temps_simulation.setVisible(mode_simulation);
        button_fuite_moins.setDisable(mode_simulation);
        button_fuite_plus.setDisable(mode_simulation);
        button_robinet_moins.setDisable(mode_simulation);
        button_robinet_plus.setDisable(mode_simulation);
        button_start.setDisable(mode_simulation);
        button_stop.setDisable(!mode_simulation);
        button_export.setDisable(!mode_simulation);
        text_libelle_volume.setVisible(mode_simulation);
        input_capacite_baignoire.setVisible(!mode_simulation);
        for (RobinetController robinet : robinets) {
            robinet.visibiliteSimulation(mode_simulation);
        }
        for (FuiteController fuite : fuites) {
            fuite.visibiliteSimulation(mode_simulation);
        }
    }

    /**
     * Méthode lié au bouton start
     * Elle enclenche la simulation au sein du modèle
     */
    @FXML
    public void action_start() {
        modele.demarrerSimulation();
    }

    /**
     * Méthode lié au bouton stop
     * Elle arrête la simulation au sein du modèle
     */
    @FXML
    public void action_stop() {
        modele.arreterSimulation();
    }

    /**
     * Méthode lié au bouton exporter
     */
    @FXML
    public void action_exporter() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("historique.fxml"));
            fxmlLoader.setControllerFactory(param -> new HistoriqueController(modele));
            Pane pane = fxmlLoader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Historique");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Méthode lié au bouton "-" des robinets
     * Elle supprime un robinet du modèle et de la vue
     */
    @FXML
    public void action_robinet_moins() {
        boolean res = modele.supprimerRobinet();
        if (res) {
            RobinetController robinetController = robinets.pop();
            robinetController.suppression();
            this.pane_robinets.getChildren().remove(this.pane_robinets.getChildren().size()-1);
        } else {
            new AlerteView("Impossible de supprimer un robinet, il doit en avoir au moins un.");
        }
    }

    /**
     * Méthode lié au bouton "+" des robinets
     * Elle ajoute un robinet au modèle et à la vue
     */
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
        } else {
            new AlerteView("Impossible d'ajouter un robinet, il y en a déjà le maximum.");
        }
    }


    /**
     * Méthode lié au bouton "-" des fuites
     * Elle supprime une fuite du modèle et de la vue
     */
    @FXML
    public void action_fuite_moins() {
        boolean res = modele.supprimerFuite();
        if (res) {
            FuiteController fuiteController = fuites.pop();
            fuiteController.suppression();
            this.pane_fuites.getChildren().remove(this.pane_fuites.getChildren().size()-1);
        } else {
            new AlerteView("Impossible de supprimer une fuite, il doit en avoir au moins une.");
        }
    }

    /**
     * Méthode lié au bouton "+" des fuites
     * Elle ajoute une fuite au modèle et à la vue
     */
    @FXML
    public void action_fuite_plus() {
        Fuite f = modele.ajouterFuite();
        if (f != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MyApplication.class.getResource("fuite.fxml"));
                fxmlLoader.setControllerFactory(param -> new FuiteController(f, modele.getSimulationEnCours()));
                Pane pane = fxmlLoader.load();
                fuites.push(fxmlLoader.getController());
                this.pane_fuites.getChildren().add(pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            new AlerteView("Impossible d'ajouter une fuite, il y en a déjà le maximum.");
        }
    }

}
