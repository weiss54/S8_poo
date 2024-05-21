package fr.ul.miage.weiss.s8_projet_poo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Classe Fuite, qui represente une fuite d'eau.
 * @author Lucas WEISS
 */
//TODO javadoc
public class Fuite {

    private static int nbFuites = 0;
    private static final int NB_FUITES_MAX = 7;

    /**
     * Debit de la fuite.
     */
    private IntegerProperty debit;

    /**
     * Indique si la fuite est réparée.
     */
    private boolean reparer;

    /**
     * Constructeur avec un parametre.
     * @param debit
     */
    public Fuite(int debit) {
        this.debit = new SimpleIntegerProperty(10);
        this.reparer = false;
        Fuite.setNbFuitesPlus();
    }

    /**
     * Permet d'indiquer que la fuite est réparée.
     */
    public void reparer() {
        this.reparer = true;
    }

    /**
     * Permet de récupérer le débit de la fuite.
     * @return débit de la fuite.
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

    /**
     * Permet de savoir si la fuite est réparée.
     * @return true si la fuite est réparée, false sinon.
     */
    public boolean isReparer() {
        return reparer;
    }

    public static void setNbFuitesPlus() {
        if (nbFuites < NB_FUITES_MAX) {
            nbFuites++;
        }
    }

    public static void setNbFuitesMoins() {
        if (nbFuites > 0) {
            nbFuites--;
        }
    }

    public static boolean isSuppressionPossible() {
        return nbFuites > 0;
    }

    public static boolean isAjoutPossible() {
        return nbFuites < NB_FUITES_MAX;
    }

    public static int getNbFuites() {
        return nbFuites;
    }

    public void suppression() {
        Fuite.setNbFuitesMoins();
    }


}
