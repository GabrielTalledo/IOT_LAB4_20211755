package com.example.telefutbul.DTOs;

import java.util.List;

public class Team {

    //Atributos:
    private String strTeam;
    private String intRank;
    private String strBadge;
    private String intWin;
    private String intDraw;
    private String intLoss;
    private String intGoalsFor;
    private String intGoalsAgainst;
    private String intGoalDifference;

    //Métodos:
    public String obtenerGoles(){
        return intGoalsFor + " / " + intGoalsAgainst + " / " + intGoalDifference;
    }

    public String obtenerResultados(){
        return intWin + " / " + intDraw + " / " + intLoss;
    }

    //Getter y setters:

    public String getIntDraw() {
        return intDraw;
    }

    public void setIntDraw(String intDraw) {
        this.intDraw = intDraw;
    }

    public String getIntGoalDifference() {
        return intGoalDifference;
    }

    public void setIntGoalDifference(String intGoalDifference) {
        this.intGoalDifference = intGoalDifference;
    }

    public String getIntGoalsAgainst() {
        return intGoalsAgainst;
    }

    public void setIntGoalsAgainst(String intGoalsAgainst) {
        this.intGoalsAgainst = intGoalsAgainst;
    }

    public String getIntGoalsFor() {
        return intGoalsFor;
    }

    public void setIntGoalsFor(String intGoalsFor) {
        this.intGoalsFor = intGoalsFor;
    }

    public String getIntLoss() {
        return intLoss;
    }

    public void setIntLoss(String intLoss) {
        this.intLoss = intLoss;
    }

    public String getIntRank() {
        return intRank;
    }

    public void setIntRank(String intRank) {
        this.intRank = intRank;
    }

    public String getIntWin() {
        return intWin;
    }

    public void setIntWin(String intWin) {
        this.intWin = intWin;
    }

    public String getStrBadge() {
        return strBadge;
    }

    public void setStrBadge(String strBadge) {
        this.strBadge = strBadge;
    }

    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }
}
