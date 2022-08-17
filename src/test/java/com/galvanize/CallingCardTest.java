package com.galvanize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallingCardTest {

    public CallingCard scam;
    public CallingCard dope;

    @BeforeEach
    void init() {
        this.scam = new CallingCard(10);
        this.dope = new CallingCard(7);
    }

    @Nested
    class Getters {
        @Test
        void testGetMinutesOnCard() {
            assertEquals(0, scam.getRemainingMinutes());
        }

        @Test
        void testGetCentsPerMinute() {
            assertEquals(10, scam.getCentsPerMinute());
        }
    }

    @Nested
    class AddMoney{
        @Test
        void testAddMoneyBasic(){
            scam.addDollars(5);
            assertEquals(50 , scam.getRemainingMinutes());
            scam.addDollars(5);
            assertEquals(100 , scam.getRemainingMinutes());
        }
        @Test
        void testAddMoneyRounded(){
            dope.addDollars(3);
            assertEquals(42 , dope.getRemainingMinutes());
            dope.addDollars(11);
            assertEquals(199 , dope.getRemainingMinutes());
        }
    }



    @Test
    void testDecreasedMinutes() {
        scam.useMinutes(5);
        assertEquals(0 , scam.getRemainingMinutes());
        scam.addDollars(1);
        scam.useMinutes(5);
        assertEquals(5 , scam.getRemainingMinutes());
    }


}
