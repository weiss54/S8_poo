package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe représentant un robinet.
 * @author Lucas WEISS
 */
//TODO javadoc
public class Robinet {

    private static int nbRobinets = 0;
    private static final int NB_ROBINETS_MAX = 7;

    /**
     * Débit du robinet.
     */
    private IntegerProperty debit;

    /**
     * Constructeur par défaut.
     */
    public Robinet() {
        this.debit = new SimpleIntegerProperty(10);
        Robinet.setNbRobinetsPlus();
    }

    /**
     * Permet de changer le débit du robinet.
     * @param debit nouveau début du robinet, ne peut pas être négatif.
     */
    public void changerDebit(int debit) {
        if (debit >= 0) {
            this.debit.set(debit);
        }
    }

    /**
     * Permet de récupérer le débit du robinet.
     * @return le débit du robinet.
     */
    public int getDebit() {
        return this.debit.get();
    }

    public IntegerProperty debitProperty() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit.set(debit);
    }

    public static int getNbRobinets() {
        return nbRobinets;
    }

    public static void setNbRobinetsPlus() {
        if (nbRobinets < NB_ROBINETS_MAX) {
            nbRobinets++;
        }
    }

    public static void setNbRobinetsMoins() {
        if (nbRobinets > 0) {
            nbRobinets--;
        }
    }

    public static boolean isSuppressionPossible() {
        return nbRobinets > 0;
    }

    public static boolean isAjoutPossible() {
        return nbRobinets < NB_ROBINETS_MAX;
    }

    public void suppression() {
        Robinet.setNbRobinetsMoins();
    }

}
