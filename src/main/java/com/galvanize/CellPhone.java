package com.galvanize;

import java.util.ArrayList;
import java.util.List;

public class CellPhone {

    private CallingCard card;
    private boolean talkingStatus;

    private List<String> history;
    private int startMinutes;

    private int index;
    private String phoneNumber;

    public CellPhone(CallingCard card) {
        this.card = card;
        history = new ArrayList<>();
        index = 0;
    }

    public boolean isTalking() {
        return talkingStatus;
    }

    public void call(String phoneNumber) {
        talkingStatus = true;
        this.phoneNumber = phoneNumber;
        startMinutes = card.getRemainingMinutes();
    }

    public void endCall() {
        talkingStatus = false;
        addToHistory(false);
        phoneNumber = null;
    }

    public void tick() {
        if(talkingStatus == false){return;}
        if(card.getRemainingMinutes() > 1) {
            card.useMinutes(1);
        } else {
            talkingStatus = false;
            addToHistory(true);
        }
    }

    public void addToHistory(boolean cutOff){
        if(cutOff == true) {
            history.add(String.format(phoneNumber + " (cut off at 1 minute)"));
        } else {
            int min = startMinutes - card.getRemainingMinutes();
            if (min == 1) {
                history.add(String.format(phoneNumber + " (%d minute)", min));
            } else {
                history.add(String.format(phoneNumber + " (%d minutes)", min));
            }
        }
    }


    public CallingCard getCard() {
        return card;
    }

    public String getHistory() {
        String base = history.toString();
        StringBuilder sb = new StringBuilder(base);
        sb.deleteCharAt(base.length()-1);
        sb.deleteCharAt(0);
        return sb.toString();
    }

}
