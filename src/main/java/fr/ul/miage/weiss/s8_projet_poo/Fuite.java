package fr.ul.miage.weiss.s8_projet_poo;

/**
 * Classe Fuite, qui represente une fuite d'eau.
 * @author Lucas WEISS
 */
public class Fuite {

    /**
     * Debit de la fuite.
     */
    private int debit;

    /**
     * Indique si la fuite est réparée.
     */
    private boolean reparer;

    /**
     * Constructeur par défaut.
     */
    public Fuite() {
        this.debit = 0;
        this.reparer = false;
    }

    /**
     * Constructeur avec un parametre.
     * @param debit
     */
    public Fuite(int debit) {
        this.debit = debit;
        this.reparer = false;
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
        return debit;
    }

    /**
     * Permet de savoir si la fuite est réparée.
     * @return true si la fuite est réparée, false sinon.
     */
    public boolean isReparer() {
        return reparer;
    }

}
