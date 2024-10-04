package com.example.telefutbul.DTOs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import kotlin.contracts.Returns;

public class Resultado {

    //Atributos:
    private String strEvent;
    private String intRound;
    private String strHomeTeam;
    private String strAwayTeam;
    private String intHomeScore;
    private String intAwayScore;
    private String dateEvent;
    private String strThumb;
    private String intSpectators;
    private int idColor;

    //Métodos:
    public String obtenerResultado(){
        return intHomeScore + " (L) - " + intAwayScore + " (V)";
    }
    public String obtenerFecha(){
        LocalDate fechaParseada = LocalDate.parse(dateEvent, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return fechaParseada.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    //Getter y setters:

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getIntAwayScore() {
        return intAwayScore;
    }

    public void setIntAwayScore(String intAwayScore) {
        this.intAwayScore = intAwayScore;
    }

    public String getIntHomeScore() {
        return intHomeScore;
    }

    public void setIntHomeScore(String intHomeScore) {
        this.intHomeScore = intHomeScore;
    }

    public String getIntRound() {
        return intRound;
    }

    public void setIntRound(String intRound) {
        this.intRound = intRound;
    }

    public String getIntSpectators() {
        if(intSpectators == null || intSpectators.isEmpty()){
            return "-";
        }else{
            return intSpectators;
        }
    }

    public void setIntSpectators(String intSpectators) {
        this.intSpectators = intSpectators;
    }

    public String getStrAwayTeam() {
        return strAwayTeam;
    }

    public void setStrAwayTeam(String strAwayTeam) {
        this.strAwayTeam = strAwayTeam;
    }

    public String getStrEvent() {
        return strEvent;
    }

    public void setStrEvent(String strEvent) {
        this.strEvent = strEvent;
    }

    public String getStrHomeTeam() {
        return strHomeTeam;
    }

    public void setStrHomeTeam(String strHomeTeam) {
        this.strHomeTeam = strHomeTeam;
    }

    public String getStrThumb() {
        if(strThumb == null || strThumb.isEmpty()){
            return "https://www.trschools.com/templates/imgs/default_placeholder.png";
        }else{
            return strThumb;
        }

    }

    public void setStrThumb(String strThumb) {
        this.strThumb = strThumb;
    }
}
