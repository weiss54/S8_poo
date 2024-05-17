package fr.ul.miage.weiss.s8_projet_poo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaignoireTest {

    @Test
    public void constructorTest() {
        Baignoire baignoire = new Baignoire();
        assertEquals(200, baignoire.getCapacite());
        assertEquals(0, baignoire.getVolumeRempli());
        baignoire = new Baignoire(100);
        assertEquals(100, baignoire.getCapacite());
        assertEquals(0, baignoire.getVolumeRempli());
    }

    @Test
    public void remplirTestGood() {
        Baignoire b = new Baignoire();
        b.remplir(10);
        assertEquals(10, b.getVolumeRempli());
    }

    @Test
    public void remplirTestDepassementCapacite() {
        Baignoire b = new Baignoire();
        b.remplir(250);
        assertEquals(200, b.getVolumeRempli());
    }

    @Test
    public void remplirTestValeurNegative() {
        Baignoire b = new Baignoire();
        b.remplir(-5);
        assertEquals(0, b.getVolumeRempli());
    }

    @Test
    public void viderTestGood() {
        Baignoire b = new Baignoire();
        b.remplir(50);
        b.vider(10);
        assertEquals(40, b.getVolumeRempli());
    }

    @Test
    public void viderTestDepassementCapacite() {
        Baignoire b = new Baignoire();
        b.remplir(50);
        b.vider(60);
        assertEquals(0, b.getVolumeRempli());
    }

    @Test
    public void viderTestValeurNegative() {
        Baignoire b = new Baignoire();
        b.remplir(50);
        b.vider(-10);
        assertEquals(50, b.getVolumeRempli());
    }

}
