package fr.ul.miage.weiss.s8_projet_poo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobinetTest {

    @Test
    public void constructeurTest() {
        Robinet r = new Robinet();
        assertEquals(0, r.getDebit());
    }

    @Test
    public void changerDebitTest() {
        Robinet r = new Robinet();
        assertEquals(0, r.getDebit());
        r.changerDebit(5);
        assertEquals(5, r.getDebit());
        r.changerDebit(-5);
        assertEquals(5, r.getDebit());
    }


}
