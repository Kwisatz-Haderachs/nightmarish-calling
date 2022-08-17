package com.galvanize;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

public class CellPhone {

    public CallingCard card;
    private boolean talkingStatus;

    private List<PhoneCall> history;
    private List<Contact> contacts;
    private int minutesElapsed;

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
            minutesElapsed = 0;
        }
    }

    public void endCall() {
        currentCall.setDurationMinutes(minutesElapsed);
        talkingStatus = false;
        history.add(currentCall);
    }

    public CellPhone tick() {
        if(talkingStatus == false){return this;}
        if(card.getRemainingMinutes() > 1) {
            card.useMinutes(1);
            minutesElapsed++;
        } else {
            talkingStatus = false;
            minutesElapsed++;
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

    public Contact searchContactByName(String name){
        for (Contact individualContact: contacts) {
            if(individualContact.getName().equals(name)){
                return individualContact;
            }
        }
        return null;
    }
    public Contact searchContactByNumber(String number){
        for (Contact individualContact: contacts) {
            if(individualContact.getNumber().equals(number)){
                return individualContact;
            }
        }
        return null;
    }

    public String printContactCard(int i){
        return String.format(contacts.get(i).getName() + ": " + contacts.get(i).getNumber());
    }

    public void printAllContacts(){
        //could implement a comparable to search
        contacts.sort(null);
        for (Contact c: contacts) {
            System.out.printf("%s: %s", c.getName(), c.getNumber());
        }
    }

    public String getHistory() {
        return history.toString().substring(1, (history.toString().length()-1));
//      alt return history.toString().replace("[", "").replace("]","");
//        String base =history.toString();
//        StringBuilder sb = new StringBuilder(base);
//        sb.deleteCharAt(base.length()-1);
//        sb.deleteCharAt(0);
//        return sb.toString();
    }

    public void changeCallingCard(CallingCard newCard) {
        int dollars = (card.getRemainingMinutes() * card.getCentsPerMinute())/100;
        card = newCard;
        card.addDollars(dollars);
    }
}
