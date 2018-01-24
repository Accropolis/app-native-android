package fr.accropolis.teamdev.accropolis.model;

import java.util.Calendar;

/**
 * Created by Nicolas Padiou on 25/02/17.
 */

public class Live {

    String uriImage;
    String title;
    String content;
    Calendar date;

    public Live(String uriImage, String title, String content, Calendar date) {
        this.uriImage = uriImage;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
