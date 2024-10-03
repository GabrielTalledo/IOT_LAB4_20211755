package com.example.telefutbul.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.telefutbul.R;
import com.example.telefutbul.databinding.ActivityAppBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AppActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    ActivityAppBinding binding;
    BottomNavigationView bottomNav;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Binding:
        binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Bottom menu:
        bottomNav = binding.bottomNavigation;
        bottomNav.setOnNavigationItemSelectedListener(this);
        bottomNav.setSelectedItemId(R.id.ligas);

        // Nav controller:

        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView)).getNavController();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        int itemId = item.getItemId();
        int currentId = bottomNav.getSelectedItemId();
        if(currentId != itemId){

            // Desde LIGAS:
            if(itemId == R.id.ligas){

                if(currentId == R.id.posiciones){
                    navController.navigate(R.id.action_fragment_posiciones_to_fragment_ligas);
                } else if (currentId == R.id.resultados) {
                    navController.navigate(R.id.action_fragment_resultados_to_fragment_ligas);
                }
                return true;
            }

            // Desde POSICIONES:
            if(itemId == R.id.posiciones){

                if(currentId == R.id.ligas){
                    navController.navigate(R.id.action_fragment_ligas_to_fragment_posiciones);
                } else if (currentId == R.id.resultados) {
                    navController.navigate(R.id.action_fragment_resultados_to_fragment_posiciones);
                }
                return true;
            }

            // Desde RESULTADOS:
            if(itemId == R.id.resultados){

                if(currentId == R.id.ligas){
                    navController.navigate(R.id.action_fragment_ligas_to_fragment_resultados);
                } else if (currentId == R.id.posiciones) {
                    navController.navigate(R.id.action_fragment_posiciones_to_fragment_ligas);
                }
                return true;
            }

        }
        return false;
    }

}