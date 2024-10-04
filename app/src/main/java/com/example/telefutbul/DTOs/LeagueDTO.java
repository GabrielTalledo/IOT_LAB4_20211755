package com.example.telefutbul.DTOs;

import java.util.List;

public class LeagueDTO {

    //Atributos:
    List<League> leagues;
    List<League> countries;

    //Getter y setters:
    public List<League> getCountries() {
        return countries;
    }

    public void setCountries(List<League> countries) {
        this.countries = countries;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
}
