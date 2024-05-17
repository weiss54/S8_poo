package fr.ul.miage.weiss.s8_projet_poo;

/**
 * Classe représentant une baignoire.
 * @author Lucas WEISS
 */
public class Baignoire {

    /**
     * Capacité de la baignoire.
     */
    private int capacite;

    /**
     * Volume rempli de la baignoire.
     */
    private int volumeRempli;

    /**
     * Constructeur par défaut.
     */
    public Baignoire() {
        this.capacite = 1000;
        this.volumeRempli = 0;
    }

    /**
     * Constructeur avec un parametre.
     * @param capacite capacité de la baignoire.
     */
    public Baignoire(int capacite) {
        this.capacite = capacite;
        this.volumeRempli = 0;
    }

    /**
     * Permet de remplir la baignoire.
     * Méthode synchronisée pour éviter les problèmes de concurrence.
     * @param volume volume à ajouter à la baignoire.
     */
    public synchronized void remplir(int volume) {
        if (volume > 0) {
            this.volumeRempli += volume;
            if (this.volumeRempli > this.capacite) {
                this.volumeRempli = this.capacite;
            }
        }
    }

    /**
     * Permet de vider la baignoire.
     * Méthode synchronisée pour éviter les problèmes de concurrence.
     * @param volume volume à retirer de la baignoire.
     */
    public synchronized void vider(int volume) {
        if (volume > 0) {
            this.volumeRempli -= volume;
            if (this.volumeRempli < 0) {
                this.volumeRempli = 0;
            }
        }
    }

    /**
     * Permet de récupérer la capacité de la baignoire.
     * @return la capacité de la baignoire.
     */
    public int getCapacite() {
        return capacite;
    }

    /**
     * Permet de récupérer le volume rempli de la baignoire.
     * @return le volume rempli de la baignoire.
     */
    public int getVolumeRempli() {
        return volumeRempli;
    }

}
