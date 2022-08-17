package com.galvanize;

public class Contact {
    private String name;
    private String number;

    public Contact(String contactName, String contactNumber){
        this.name = contactName;
        this.number = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
