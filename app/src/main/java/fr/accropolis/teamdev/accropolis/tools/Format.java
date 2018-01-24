package fr.accropolis.teamdev.accropolis.tools;

/**
 * Created by nico on 26/02/17.
 */

public class Format {
    static public String displayTime(int nHour, int nMinute, String separator){
    String sHour = "";
    String sMinute = "";

    if(nHour < 10){
        sHour = "0" + nHour;
    }else {
        sHour = "" + nHour;
    }

    if(nMinute < 10){
        sMinute = "0" + nMinute;
    }else {
        sMinute = "" + nMinute;
    }

    return sHour+separator+sMinute;
}
}
