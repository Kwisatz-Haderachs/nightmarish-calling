package com.galvanize;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

public class CellPhone {

    public CallingCard card;
    private boolean talkingStatus;

    private List<PhoneCall> history;
    private List<Contact> contacts;
    private int startMinutes;

    private PhoneCall currentCall;
    private String ownerNumber;

    public CellPhone(CallingCard card, String selfNumber) {
        this.card = card;
        history = new ArrayList<>();
        contacts = new ArrayList<>();
        this.ownerNumber = selfNumber;
    }

    public String cellPhoneNumber(){
        return ownerNumber;
    }
    public boolean isTalking() {
        return talkingStatus;
    }

    public void call(String phoneNumber) {
        if(talkingStatus == true){
            currentCall.setThreeway(true);
            currentCall.setSecondNumber(phoneNumber);
        }else {
            currentCall = new PhoneCall(phoneNumber);
            talkingStatus = true;
            startMinutes = card.getRemainingMinutes();
        }
    }

    public void endCall() {
        int min = startMinutes - card.getRemainingMinutes();
        currentCall.setDurationMinutes(min);
        talkingStatus = false;
        history.add(currentCall);
    }

    public CellPhone tick() {
        if(talkingStatus == false){return this;}
        if(card.getRemainingMinutes() > 1) {
            card.useMinutes(1);
        } else {
            talkingStatus = false;
            currentCall.setCutoffToTrue();
            endCall();
        }
        return this;
    }

    public void addConact(Contact c){
        for (Contact a: contacts) {
            if(c.getNumber() == a.getNumber()){
                System.out.println("Contact already exists. Updated info?");
                return;
            }
        }
        contacts.add(c);
    }
    public Contact getContact(int i){
        if(i > contacts.size()-1){
            System.out.println("Contact doesn't exist");
            return null;
        }
        return contacts.get(i);
    }

    public String getHistory() {
        String base = history.toString();
        StringBuilder sb = new StringBuilder(base);
        sb.deleteCharAt(base.length()-1);
        sb.deleteCharAt(0);
        return sb.toString();
    }

    public void changeCallingCard(CallingCard newCard) {
        int dollars = (card.getRemainingMinutes() * card.getCentsPerMinute())/100;
        card = newCard;
        card.addDollars(dollars);
    }
}
