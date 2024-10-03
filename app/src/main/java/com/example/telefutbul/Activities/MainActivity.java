package com.example.telefutbul.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telefutbul.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding:
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Lógica:
        binding.btnIngresar.setOnClickListener(view -> {
            if(!verificarConexionInternet()){
                mostrarDialogError();
            }else{
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
            }
        });
    }


    // ---------
    //  MÉTODOS:
    // ---------

    public void mostrarDialogError(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Error de conexión");
        builder.setMessage("Por favor, conéctese a internet y vuelva a intentarlo.");
        builder.setPositiveButton("Ir a configuración", (dialog, which) ->{
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
            //dialog.dismiss();
        });
        builder.setNegativeButton("Cerrar", (dialog, which) ->{
          dialog.dismiss();
        });
        builder.show();
    }

    public boolean verificarConexionInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean hayInternet = false;

        if(connectivityManager != null){
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if(capabilities != null){
                if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    hayInternet = true;
                }else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    hayInternet = true;
                }else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    hayInternet = true;
                }
            }

        }

        return hayInternet;
    }
}