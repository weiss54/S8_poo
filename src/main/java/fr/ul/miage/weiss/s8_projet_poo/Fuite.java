package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe Fuite, qui represente une fuite d'eau.
 * @author Lucas WEISS
 */
public class Fuite {

    /**
     * Nombre de fuites au sein de la simulation
     */
    private static int nbFuites = 0;

    /**
     * Nombre de fuites maximum autorisé.
     */
    private static final int NB_FUITES_MAX = 5 ;

    /**
     * Debit de la fuite.
     */
    private IntegerProperty debit;

    /**
     * Indique si la fuite est réparée.
     */
    private BooleanProperty reparer;

    /**
     * Constructeur sans parametre.
     */
    public Fuite() {
        this.debit = new SimpleIntegerProperty(10);
        this.reparer = new SimpleBooleanProperty(false);
        Fuite.setNbFuitesPlus();
    }

    /**
     * Permet d'indiquer que la fuite est réparée.
     */
    public void reparer() {
        this.reparer.set(true);
    }

    /**
     * Permet de récupérer le débit de la fuite.
     * @return le débit de la fuite.
     */
    public int getDebit() {
        if (!isReparer()) return this.debit.get();
        return 0;
    }

    public IntegerProperty debitProperty() {
        return debit;
    }

    /**
     * Permet de définir le débit de la fuite.
     * @param debit noyveau débit de la fuite.
     */
    public void setDebit(int debit) {
        this.debit.set(debit);
    }

    /**
     * Permet de savoir si la fuite est réparée.
     * @return true si la fuite est réparée, false sinon.
     */
    public boolean isReparer() {
        return reparer.get();
    }

    public BooleanProperty reparerProperty() {
        return reparer;
    }

    /**
     * Augmente d'un le nombre de fuites.
     */
    public static void setNbFuitesPlus() {
        if (nbFuites < NB_FUITES_MAX) {
            nbFuites++;
        }
    }

    /**
     * Diminue d'un le nombre de fuites.
     */
    public static void setNbFuitesMoins() {
        if (nbFuites > 0) {
            nbFuites--;
        }
    }

    /**
     * Permet de savoir si une fuite peut être supprimée.
     * @return
     */
    public static boolean isSuppressionPossible() {
        return nbFuites > 0;
    }

    /**
     * Permet de savoir si une fuite peut être ajoutée.
     * @return
     */
    public static boolean isAjoutPossible() {
        return nbFuites < NB_FUITES_MAX;
    }

    /**
     * Permet de récupérer le nombre de fuites.
     * @return
     */
    public static int getNbFuites() {
        return nbFuites;
    }

    /**
     * Permet de supprimer une fuite.
     */
    public void suppression() {
        Fuite.setNbFuitesMoins();
    }

    /**
     * Indique que la fuite n'est plus réparée car la simulation est terminée
     */
    public void finSimulation() {
        this.reparer.set(false);
    }

}
