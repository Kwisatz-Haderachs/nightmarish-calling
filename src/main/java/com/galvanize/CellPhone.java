package com.galvanize;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

public class CellPhone {

    public CallingCard card;
    private boolean talkingStatus;

    private List<PhoneCall> history;
    private int startMinutes;

    private int index;
    private PhoneCall currentCall;

    public CellPhone(CallingCard card) {
        this.card = card;
        history = new ArrayList<>();
        index = 0;
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
