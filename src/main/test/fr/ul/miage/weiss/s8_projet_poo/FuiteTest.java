package fr.ul.miage.weiss.s8_projet_poo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FuiteTest {

    @Test
    public void constructorTest() {
        Fuite f = new Fuite(5);
        assertEquals(5, f.getDebit());
        assertFalse(f.isReparer());
    }

    @Test
    public void reparerTest() {
        Fuite f = new Fuite(5);
        assertFalse(f.isReparer());
        f.reparer();
        assert(f.isReparer());
    }

}
