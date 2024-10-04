package com.example.telefutbul.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.telefutbul.DTOs.League;
import com.example.telefutbul.Adapters.LigaAdapter;
import com.example.telefutbul.DTOs.LeagueDTO;
import com.example.telefutbul.Services.SportsService;
import com.example.telefutbul.databinding.FragmentLigasBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ligas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ligas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // PARÁMETROS:

    FragmentLigasBinding binding;

    private String mParam1;
    private String mParam2;

    RecyclerView rvLigas;
    Button btnBuscar;
    SportsService sportsService;

    LigaAdapter ligaAdapter;


    public Ligas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ligas.
     */
    // TODO: Rename and change types and number of parameters
    public static Ligas newInstance(String param1, String param2) {
        Ligas fragment = new Ligas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLigasBinding.inflate(inflater, container, false);
        createSportsService();
        rvLigas = binding.rvLigas;
        btnBuscar = binding.btnBuscarLigas;

        // Gestión del botón:
        btnBuscar.setOnClickListener(l -> {
            String pais = binding.inputPaisLiga.getText().toString();
            if(pais.isEmpty()){
                obtenerLigasGeneral();
            }else{
                obtenerLigasPais(pais);
            }
        });


        return binding.getRoot();
    }



    public void obtenerLigasGeneral(){
        sportsService.getLigasGeneral().enqueue(new Callback<LeagueDTO>() {
            @Override
            public void onResponse(Call<LeagueDTO> call, Response<LeagueDTO> response) {
                if(response.isSuccessful()){
                    // Gestión del RecyclerView desde cero:

                    ligaAdapter = new LigaAdapter();
                    ligaAdapter.setListaLigas(response.body().getLeagues());
                    ligaAdapter.setContext(getContext());
                    rvLigas.setAdapter(ligaAdapter);
                    rvLigas.setLayoutManager(new LinearLayoutManager(getContext()));

                }else{
                    Log.d("BUG", "Error!" + response.code() + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<LeagueDTO> call, Throwable throwable) {
                Log.d("BUG2", "Error!");
            }
        });
    }

    public void obtenerLigasPais(String pais){
        sportsService.getLigasPais(pais).enqueue(new Callback<LeagueDTO>() {
            @Override
            public void onResponse(Call<LeagueDTO> call, Response<LeagueDTO> response) {
                if(response.isSuccessful()){

                    if(response.body().getCountries() == null){
                        // Si el País no existe:
                        Snackbar.make(binding.getRoot(), "El país ingresado no existe/es incorrecto!", Snackbar.LENGTH_LONG)
                                .setAction("Limpiar", v -> {
                                    binding.inputPaisLiga.setText("");})
                                .show();
                    }else{
                        // Gestión del RecyclerView desde cero:
                        ligaAdapter = new LigaAdapter();
                        ligaAdapter.setListaLigas(response.body().getCountries());
                        ligaAdapter.setContext(getContext());
                        rvLigas.setAdapter(ligaAdapter);
                        rvLigas.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                }else{
                    Log.d("BUG", "Error!");
                }
            }

            @Override
            public void onFailure(Call<LeagueDTO> call, Throwable throwable) {
                Log.d("BUG2", "Error!");
            }
        });
    }


    public void createSportsService() {
        sportsService = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SportsService.class);
    }
}