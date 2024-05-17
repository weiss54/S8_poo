package fr.ul.miage.weiss.s8_projet_poo;

/**
 * Classe représentant un robinet.
 * @author Lucas WEISS
 */
public class Robinet {

    /**
     * Débit du robinet.
     */
    private int debit;

    /**
     * Constructeur par défaut.
     */
    public Robinet() {
        this.debit = 0;
    }

    /**
     * Constructeur avec un parametre
     * @param debit débit initial du robinet.
     */
    public Robinet(int debit) {
        this.debit = debit;
    }

    /**
     * Permet de changer le débit du robinet.
     * @param debit nouveau début du robinet, ne peut pas être négatif.
     */
    public void changerDebit(int debit) {
        if (debit >= 0) {
            this.debit = debit;
        }
    }

    /**
     * Permet de récupérer le débit du robinet.
     * @return le débit du robinet.
     */
    public int getDebit() {
        return this.debit;
    }
}
