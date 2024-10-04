package com.example.telefutbul.Services;

import com.example.telefutbul.DTOs.League;
import com.example.telefutbul.DTOs.LeagueDTO;
import com.example.telefutbul.DTOs.ResultadoDTO;
import com.example.telefutbul.DTOs.TeamDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsService {


    // Ligas:
    @GET("/api/v1/json/3/all_leagues.php")
    Call<LeagueDTO> getLigasGeneral();
    @GET("/api/v1/json/3/search_all_leagues.php")
    Call<LeagueDTO> getLigasPais(@Query("c") String pais);

    // Posiciones:
    @GET("/api/v1/json/3/lookuptable.php")
    Call<TeamDTO> getPosiciones(@Query("l") String id,
                                @Query("s") String temporada);

    // Resultados:
    @GET("/api/v1/json/3/eventsround.php")
    Call<ResultadoDTO> getResultados(@Query("id") String id,
                                     @Query("s") String temporada,
                                     @Query("r") String ronda);
}