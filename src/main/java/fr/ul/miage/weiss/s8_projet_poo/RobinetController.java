package fr.ul.miage.weiss.s8_projet_poo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Classe RobinetController
 * Cette classe est le contrôleur de la vue d'un robinet
 * Elle permet de gérer les actions de l'utilisateur sur un robinet
 * @author Lucas WEISS
 */
public class RobinetController {

    /* Attribut TextField lié à la vue de cet écran, permet de changer le débit du robinet */
    @FXML
    private TextField input_robinet;

    /* Attribut Text lié à la vue de cet écran, contient le numéro du robinet */
    @FXML
    private Text text_num;

    /* Attribut qui contient le robinet que l'écran représente */
    private Robinet robinet;

    /**
     * Constructeur de la classe RobinetController
     * @param r robinet que l'écran représente
     */
    public RobinetController(Robinet r) {
        this.robinet = r;
    }

    /**
     * Méthode appelée à l'initialisation de l'écran
     */
    @FXML
    public void initialize() {
        text_num.setText("N°" + Robinet.getNbRobinets());
        input_robinet.setText(String.valueOf(robinet.getDebit()));
        input_robinet.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                input_robinet.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.isEmpty()) {
                robinet.setDebit(0);
            } else {
                robinet.setDebit(Integer.parseInt(input_robinet.getText()));
            }
        });
        input_robinet.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (input_robinet.getText().isEmpty()) {
                    robinet.setDebit(0);
                }
            }
        });
        visibiliteSimulation(false);
    }

    /**
     * Méthode qui permet de changer la visibilité de certains composants selon l'état de la simulation
     * @param b indique si la simulation est en cours ou non
     */
    public void visibiliteSimulation(boolean b) {

    }

    /**
     * Méthode qui permet de supprimer le robinet
     */
    public void suppression() {
        this.robinet.suppression();
    }


}
