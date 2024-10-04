package com.example.telefutbul.Fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.telefutbul.Adapters.PosicionAdapter;
import com.example.telefutbul.Adapters.ResultadoAdapter;
import com.example.telefutbul.DTOs.Resultado;
import com.example.telefutbul.DTOs.ResultadoDTO;
import com.example.telefutbul.DTOs.TeamDTO;
import com.example.telefutbul.R;
import com.example.telefutbul.Services.SportsService;
import com.example.telefutbul.databinding.FragmentPosicionesBinding;
import com.example.telefutbul.databinding.FragmentResultadosBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Resultados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Resultados extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // PARÁMETROS:

    FragmentResultadosBinding binding;

    private String mParam1;
    private String mParam2;

    RecyclerView rvResul;
    Button btnBuscar;
    SportsService sportsService;
    String rondaAnterior="";
    Integer sizeResultado = 0;

    List<Resultado> listResultados;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean dialogoActivo = false;
    private float umbral = 20.0f;

    private List<Integer> listaColores = new ArrayList<>(List.of(R.color.md_theme_inversePrimary_highContrast,
            R.color.md_theme_inversePrimary_mediumContrast,
            R.color.md_theme_primaryContainer_mediumContrast,
            R.color.md_theme_primaryContainer_highContrast,
            R.color.md_theme_tertiaryFixedDim_mediumContrast,
            R.color.md_theme_tertiaryContainer_mediumContrast,
            R.color.md_theme_tertiaryFixedDim,
            R.color.md_theme_tertiaryFixed));

    ResultadoAdapter resultadoAdapter;

    public Resultados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Resultados.
     */
    // TODO: Rename and change types and number of parameters
    public static Resultados newInstance(String param1, String param2) {
        Resultados fragment = new Resultados();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultadosBinding.inflate(inflater, container, false);
        createSportsService();
        rvResul = binding.rvResul;
        btnBuscar = binding.btnBuscarResul;

        // Gestión del adapter:
        listResultados = new ArrayList<>();
        resultadoAdapter = new ResultadoAdapter();
        resultadoAdapter.setListaResultados(listResultados);
        resultadoAdapter.setContext(getContext());
        resultadoAdapter.setRecyclerView(rvResul);
        rvResul.setAdapter(resultadoAdapter);
        rvResul.setLayoutManager(new LinearLayoutManager(getContext()));

        // Gestión del botón:
        btnBuscar.setOnClickListener(l -> {
            String idLiga = binding.inputIdLigaResul.getText().toString();
            String temp = binding.inputTempResul.getText().toString();
            String ronda = binding.inputRondaResul.getText().toString();

            if(!ronda.equals(rondaAnterior)) {
                // Validación:
                boolean validacion1 = true; //empty idLiga
                boolean validacion2 = true; //empty temp
                boolean validacion3 = true; //empty ronda
                boolean validacion4 = true; //formato incorrecto de temporada
                String mensaje = "";

                if (idLiga.isEmpty()) {
                    validacion1 = false;
                }
                if (temp.isEmpty()) {
                    validacion2 = false;
                }

                if (ronda.isEmpty()) {
                    validacion3 = false;
                }

                validacion4 = temp.matches("20(\\d{2})-20(\\d{2})") && Integer.parseInt(temp.substring(7)) == Integer.parseInt(temp.substring(2, 4)) + 1;

                // Mensajitos:

                if (!validacion1 && validacion2 && validacion3) {
                    mensaje = "El idLiga no puede estar vacío! ";
                }
                if (!validacion2 && validacion1 && validacion3) {
                    mensaje = "La temporada no puede estar vacía! ";
                }
                if (!validacion3 && validacion2 && validacion1) {
                    mensaje = "La ronda no puede estar vacía! ";
                }
                if (!validacion1 && !validacion2) {
                    mensaje = "El idLiga y la temporada no pueden estar vacíos! ";
                }
                if (!validacion1 && !validacion3) {
                    mensaje = "El idLiga y la ronda no pueden estar vacíos! ";
                }
                if (!validacion2 && !validacion3) {
                    mensaje = "La temporada y la ronda no pueden estar vacíos! ";
                }
                if (!validacion1 && !validacion2 && !validacion3) {
                    mensaje = "Todos los campos no pueden estar vacíos! ";
                }

                if (!validacion4 && validacion2) {
                    mensaje += "La temporada debe tener el formato 20XX-20XY!";
                }

                rondaAnterior = ronda;

                if (validacion1 && validacion2 && validacion3 && validacion4) {
                    // Llamada a la API:
                    obtenerResultados(idLiga, temp, ronda);
                } else {
                    Snackbar.make(binding.getRoot(), mensaje, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        return binding.getRoot();
    }

    public void obtenerResultados(String idLiga,String temporada,String ronda){
        sportsService.getResultados(idLiga,temporada,ronda).enqueue(new Callback<ResultadoDTO>() {
            @Override
            public void onResponse(Call<ResultadoDTO> call, Response<ResultadoDTO> response) {
                if(response.isSuccessful()){
                    if(response.body().getEvents() == null || response.toString().isEmpty()){
                        // Si no hay resultados:
                        Snackbar.make(binding.getRoot(), "No existen resultados!", Snackbar.LENGTH_LONG).show();
                    }else{
                        // Gestión del RecyclerView:
                        ArrayList<Resultado> listaResultadosAux = (ArrayList<Resultado>) response.body().getEvents();
                        for(Resultado resuAux: listaResultadosAux){
                            resuAux.setIdColor(listaColores.get(0));
                        }
                        sizeResultado = listaResultadosAux.size();
                        desplazarIzquierda();
                        resultadoAdapter.agregarElementos(listaResultadosAux);
                    }
                }else{
                    Log.d("BUG", "Error!");
                }
            }

            @Override
            public void onFailure(Call<ResultadoDTO> call, Throwable throwable) {
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

    public void desplazarIzquierda() {
        Integer primerElemento = listaColores.get(0);
        for (int i = 1; i < listaColores.size(); i++) {
            listaColores.set(i - 1, listaColores.get(i));
        }
        listaColores.set(listaColores.size() - 1, primerElemento);
    }

    // Sensores :O



    private void prenderSensor() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private void apagarSensor() {
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];

        float aceleracion = (float) Math.sqrt(x * x + y * y + z * z);

        if(aceleracion>umbral && !dialogoActivo){
            if(!resultadoAdapter.getListaResultados().isEmpty()){
                mostrarDialogo();
            }
            dialogoActivo = true;
        }
    }

    private void mostrarDialogo() {
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Aceleración alta!")
                .setMessage("La aceleración ha superado los "+ umbral + " m/s². Desea eliminar los últimos resultados?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    resultadoAdapter.eliminarHastaPosicion(sizeResultado);
                    dialogoActivo = false;
                    rvResul.scrollToPosition(0);
                }) // Reiniciar para poder mostrarlo de nuevo
                .setNegativeButton("No", (dialog, which) -> {
                    dialogoActivo = false;
                })
                .show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onStart() {
        super.onStart();
        prenderSensor();
    }

    @Override
    public void onStop() {
        super.onStop();
        apagarSensor();
    }
}