package com.example.telefutbul.DTOs;

import java.io.Serializable;
import java.util.List;

public class League implements Serializable {

    //Atributos:
    private String idLeague;
    private String strLeague;
    private String strSport;
    private String strLeagueAlternate;

    //Métodos:
    public List<String> obtenerNombresAlternativos(){
        String[] nombres = new String[2];
        String nombre1 = "-";
        String nombre2 = "-";

        if(!strLeagueAlternate.isEmpty()){
            nombres = strLeagueAlternate.split(", ");

            try{
                nombre1 = nombres[0];
                nombre2 = nombres[1];
            }catch (ArrayIndexOutOfBoundsException e){

            }
        }

        return List.of(nombre1, nombre2);
    }

    //Getter y setters:
    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public String getStrLeagueAlternate() {
        return strLeagueAlternate;
    }

    public void setStrLeagueAlternate(String strLeagueAlternate) {
        this.strLeagueAlternate = strLeagueAlternate;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }
}
