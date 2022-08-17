package com.galvanize;

public class PhoneCall {
    private String phoneNumber;
    private String secondNumber;
    private int durationMinutes;
    private boolean cutoff;
    private boolean threeway;

    public PhoneCall(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setThreeway(boolean startThreeway) {
        this.threeway = startThreeway;
    }

    public void setSecondNumber(String number){
        this.secondNumber = number;
    }
    public void setCutoffToTrue(){
        cutoff = true;
    }
    @Override
    public String toString(){
        if(cutoff == true && durationMinutes == 1){
            return String.format(phoneNumber + " (cut off at 1 minute)");
        } else if (cutoff == true && durationMinutes > 1) {
            return String.format(phoneNumber + " (cut off at %d minutes)", durationMinutes);
        }
        if(threeway == true){
            return String.format("Three-way call: " + phoneNumber + " & " + secondNumber + " (%d minutes)", durationMinutes);
        }
        if(durationMinutes == 1){
            return String.format(phoneNumber + " (1 minute)");
        }
        return String.format(phoneNumber + " (%d minutes)", durationMinutes);
    }
}
