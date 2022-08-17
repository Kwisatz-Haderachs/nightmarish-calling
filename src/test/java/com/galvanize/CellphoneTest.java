package com.galvanize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellphoneTest {

    public CellPhone nokia;
    public CellPhone samsung;

    @BeforeEach
    void init() {
        CallingCard nokiaCard = new CallingCard(10);
        nokiaCard.addDollars(1);
        CallingCard samsungCard = new CallingCard(11);
        this.nokia = new CellPhone(nokiaCard);
        this.samsung = new CellPhone(samsungCard);
    }

    @Nested
    class callFunctions {
        @Test
        void testIsTalking() {
            assertEquals(false, nokia.isTalking());
        }

        @Test
        void testCall() {
            nokia.call("678-9999");
            assertEquals(true, nokia.isTalking());
            nokia.endCall();
            assertEquals(false, nokia.isTalking());
        }

        @Test
        void testTick() {
            nokia.call("111-1111");
            nokia.tick().tick();
            nokia.endCall();
            assertEquals(8, nokia.card.getRemainingMinutes());
            samsung.call("678-9999");
            assertEquals(0, samsung.card.getRemainingMinutes());
            samsung.tick();
            assertEquals(false, samsung.isTalking());
        }
    }

    @Nested
    class testHistoryFunctions {
        @Test
        void testGetHistory() {
            nokia.call("777-7777");
            nokia.tick();
            nokia.tick();
            nokia.endCall();
            nokia.call("111-1111");
            nokia.tick();
            nokia.endCall();
            assertEquals("777-7777 (2 minutes), 111-1111 (1 minute)", nokia.getHistory());
        }

        @Test
        void testAddToHistory() {
            samsung.call("111-1111");
            samsung.tick();
            assertEquals("111-1111 (cut off at 1 minute)", samsung.getHistory());
        }
    }

    @Nested
    class additionalMods {
        @Test
        void testChangeCallingCard() {
            CallingCard newCard = new CallingCard(5);
            nokia.changeCallingCard(newCard);
            assertEquals(20, nokia.card.getRemainingMinutes());
        }

        @Test
        void testThreeway() {
            nokia.call("777-7777");
            nokia.tick();
            nokia.call("111-1111");
            nokia.tick();
            nokia.endCall();
            assertEquals("Three-way call: 777-7777 & 111-1111 (2 minutes)", nokia.getHistory());
        }
    }
}
