package com.galvanize;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContactTest {

    @Test
    void generate(){
        Contact first = new Contact("Adam", "512-4232");
        assertEquals("Adam", first.getName());
        assertEquals("512-4232", first.getNumber());
        first.setName("Big A");
        first.setNumber("512-4343");
        assertEquals("Big A", first.getName());
        assertEquals("512-4343", first.getNumber());
    }
}
