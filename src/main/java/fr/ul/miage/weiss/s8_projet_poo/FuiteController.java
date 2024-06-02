package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Classe FuiteController, qui permet de gérer les fuites d'eau.
 * Cette classe est un contrôleur pour la vue FuiteView.
 * @author Lucas WEISS
 */
public class FuiteController {

    /**
     * Champ de texte permettant de saisir le débit de la fuite.
     */
    @FXML
    private TextField input_fuite;

    /**
     * Texte affichant le numéro de la fuite.
     */
    @FXML
    private Text text_num;

    /**
     * Texte affichant le débit de la fuite.
     */
    @FXML
    private Text text_fuite;

    /**
     * Images représentant la fuite
     */
    @FXML
    private ImageView image_fuite, image_reparation;

    /**
     * Fuite à gérer.
     */
    private Fuite fuite;

    /**
     * On récupère le BooleanProperty qui indique si la simulation est en cours.
     */
    private BooleanProperty simulation;

    /**
     * Constructeur de la classe.
     * @param fuite
     * @param simulation
     */
    public FuiteController(Fuite fuite, BooleanProperty simulation) {
        this.fuite = fuite;
        this.simulation = simulation;
    }

    /**
     * Méthode appelée à l'initialisation de la vue.
     */
    @FXML
    public void initialize() {
        // On définit les valeurs des champs de la vue.
        text_num.setText("N°" + Fuite.getNbFuites());
        input_fuite.setText(String.valueOf(fuite.getDebit()));
        text_fuite.setText(String.valueOf(fuite.getDebit()));
        visibiliteReparation(false);
        visibiliteSimulation(false);
        // On définit les écouteurs
        input_fuite.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                input_fuite.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!newValue.isEmpty()) {
                fuite.setDebit(Integer.parseInt(input_fuite.getText()));
            }
        });
        input_fuite.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (input_fuite.getText().isEmpty()) {
                    fuite.setDebit(10);
                    text_fuite.setText("10");
                }
            }
        });
        fuite.debitProperty().addListener((observable, oldValue, newValue) -> {
            text_fuite.setText(observable.getValue().toString());
        });
        fuite.reparerProperty().addListener((observable, oldValue, newValue) -> {
            image_reparation.setVisible(newValue);
        });
        image_fuite.onMouseClickedProperty().set(event -> {
            if (!fuite.isReparer() && simulation.get()) fuite.reparer();
        });
    }

    /**
     * Méthode permettant de supprimer la fuite.
     */
    public void suppression() {
        this.fuite.suppression();
    }

    /**
     * Méthode permettant de rendre visible ou non l'image de réparation.
     * @param b
     */
    public void visibiliteReparation(boolean b) {
        image_reparation.setVisible(b);
    }

    /**
     * Méthode permettant de rendre visible ou non le champ de saisie du débit de la fuite.
     * @param b
     */
    public void visibiliteSimulation(boolean b) {
        text_fuite.setVisible(b);
        input_fuite.setVisible(!b);
    }

}
