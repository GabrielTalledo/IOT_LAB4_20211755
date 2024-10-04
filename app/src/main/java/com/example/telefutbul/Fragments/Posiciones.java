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

import com.example.telefutbul.Adapters.LigaAdapter;
import com.example.telefutbul.Adapters.PosicionAdapter;
import com.example.telefutbul.DTOs.LeagueDTO;
import com.example.telefutbul.DTOs.TeamDTO;
import com.example.telefutbul.R;
import com.example.telefutbul.Services.SportsService;
import com.example.telefutbul.databinding.FragmentLigasBinding;
import com.example.telefutbul.databinding.FragmentPosicionesBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Posiciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Posiciones extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // PARÁMETROS:

    FragmentPosicionesBinding binding;

    private String mParam1;
    private String mParam2;

    RecyclerView rvPos;
    Button btnBuscar;
    SportsService sportsService;

    PosicionAdapter posicionAdapter;

    public Posiciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Posiciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Posiciones newInstance(String param1, String param2) {
        Posiciones fragment = new Posiciones();
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
        binding = FragmentPosicionesBinding.inflate(inflater, container, false);
        createSportsService();
        rvPos = binding.rvPos;
        btnBuscar = binding.btnBuscarPos;

        // Gestión del botón:
        btnBuscar.setOnClickListener(l -> {
            String idLiga = binding.inputIdLigaPos.getText().toString();
            String temp = binding.inputTempPos.getText().toString();

            // Validación:
            boolean validacion1 = true; //empty
            boolean validacion2 = true; //empty
            boolean validacion3; //temporada con mal formato
            String mensaje = "";

            if(idLiga.isEmpty()){
                validacion1 = false;
            }
            if(temp.isEmpty()){
                validacion2 = false;
            }
            validacion3 = temp.matches("20(\\d{2})-20(\\d{2})") && Integer.parseInt(temp.substring(7)) == Integer.parseInt(temp.substring(2, 4)) + 1;

            // Mensajitos:
            if(!validacion1 && !validacion2){
                mensaje = "Ambos campos no pueden estar vacíos!";
            }
            if(!validacion1 && validacion2){
                mensaje = "EL idLiga no puede estar vacío!";
            }
            if(!validacion2 && validacion1){
                mensaje = "La temporada no puede estar vacía!";
            }
            if(!validacion1 && !validacion3 && validacion2){
                mensaje = "El idLiga no puede estar vacío y la temporada no tiene el formato correcto!";
            }
            if(!validacion3 && validacion1 && validacion2){
                mensaje = "La temporada debe tener el formato 20XX-20XY!";
            }

            if(validacion1 && validacion2 && validacion3){
                // Llamada a la API:
                obtenerLigasPais(idLiga,temp);
            }else{
                Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG).show();
            }

        });


        return binding.getRoot();
    }

    public void obtenerLigasPais(String idLiga,String temporada){
        sportsService.getPosiciones(idLiga,temporada).enqueue(new Callback<TeamDTO>() {
            @Override
            public void onResponse(Call<TeamDTO> call, Response<TeamDTO> response) {
                if(response.isSuccessful()){
                    if(response.body().getTable() == null || response.toString().isEmpty()){
                        // Si no hay resultados:
                        Snackbar.make(binding.getRoot(), "La liga introducida no es válida!", Snackbar.LENGTH_LONG).show();
                    }else{
                        // Gestión del RecyclerView desde cero:
                        posicionAdapter = new PosicionAdapter();
                        posicionAdapter.setListaTeams(response.body().getTable());
                        posicionAdapter.setContext(getContext());
                        rvPos.setAdapter(posicionAdapter);
                        rvPos.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                }else{
                    Log.d("BUG", "Error!");
                }
            }

            @Override
            public void onFailure(Call<TeamDTO> call, Throwable throwable) {
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