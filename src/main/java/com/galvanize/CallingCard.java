package com.galvanize;

import jdk.vm.ci.code.site.Call;

public class CallingCard {

    private int centsPerMinute;
    private int minutesOnCard;


    public CallingCard(int cost) {
        this.centsPerMinute = cost;
        this.minutesOnCard = 0;
    }

    public int getCentsPerMinute() {
        return centsPerMinute;
    }

    public int getRemainingMinutes() {
        return minutesOnCard;
    }

    public void addDollars(int dollars) {
        minutesOnCard += ((dollars * 100) / centsPerMinute);
    }

    public void useMinutes(int minutes) {
        if(minutesOnCard - minutes < 0) {
            minutesOnCard = 0;
        } else {
            minutesOnCard -= minutes;
        }
    }


}
