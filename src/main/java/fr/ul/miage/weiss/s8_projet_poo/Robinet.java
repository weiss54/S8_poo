package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe représentant un robinet.
 * @author Lucas WEISS
 */
public class Robinet {

    /**
     * Nombre de robinets au sein de la simulation.
     */
    private static int nbRobinets = 0;

    /**
     * Nombre de robinets maximum autorisé.
     */
    private static final int NB_ROBINETS_MAX = 5;

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

    /**
     * Change le débit du robinet.
     * @param debit
     */
    public void setDebit(int debit) {
        System.out.println("debit = " + debit);
        this.debit.set(debit);
    }

    /**
     * Permet de récupérer le nombre de robinets.
     * @return
     */
    public static int getNbRobinets() {
        return nbRobinets;
    }

    /**
     * Permet d'ajouter un robinet.
     */
    public static void setNbRobinetsPlus() {
        if (nbRobinets < NB_ROBINETS_MAX) {
            nbRobinets++;
        }
    }

    /**
     * Permet de retirer un robinet.
     */
    public static void setNbRobinetsMoins() {
        if (nbRobinets > 0) {
            nbRobinets--;
        }
    }

    /**
     * Permet de savoir si une suppression est possible.
     * @return
     */
    public static boolean isSuppressionPossible() {
        return nbRobinets > 0;
    }

    /**
     * Permet de savoir si un ajout est possible.
     * @return
     */
    public static boolean isAjoutPossible() {
        return nbRobinets < NB_ROBINETS_MAX;
    }

    /**
     * Permet de supprimer un robinet.
     */
    public void suppression() {
        Robinet.setNbRobinetsMoins();
    }

}
