package com.galvanize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneCallTest {

    public PhoneCall ph;

    @BeforeEach
    void init(){
        ph = new PhoneCall("111-2222");
        ph.setDurationMinutes(4);
    }
    @Test
    void testToString(){
        assertEquals("111-2222 (4 minutes)", ph.toString());
    }
}
