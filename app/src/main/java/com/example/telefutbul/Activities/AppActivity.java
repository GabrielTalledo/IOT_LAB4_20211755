package com.example.telefutbul.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptions;
import androidx.navigation.NavOptionsBuilder;
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

            // A LIGAS:
            if(itemId == R.id.ligas){

                /* Método alternativo con actions individuales
                if(currentId == R.id.posiciones){
                    navController.navigate(R.id.action_fragment_posiciones_to_fragment_ligas);
                } else if (currentId == R.id.resultados) {
                    navController.navigate(R.id.action_fragment_resultados_to_fragment_ligas);
                }*/

                navegar(R.id.fragment_ligas);
            }

            // A POSICIONES:
            if(itemId == R.id.posiciones){

                /* Método alternativo con actions individuales
                if(currentId == R.id.ligas){
                    navController.navigate(R.id.action_fragment_ligas_to_fragment_posiciones);
                } else if (currentId == R.id.resultados) {
                    navController.navigate(R.id.action_fragment_resultados_to_fragment_posiciones);
                }*/

                navegar(R.id.fragment_posiciones);
            }

            // A RESULTADOS:
            if(itemId == R.id.resultados){

                /* Método alternativo con actions individuales
                if(currentId == R.id.ligas){
                    navController.navigate(R.id.action_fragment_ligas_to_fragment_resultados);
                } else if (currentId == R.id.posiciones) {
                    navController.navigate(R.id.action_fragment_posiciones_to_fragment_resultados);
                }*/

                navegar(R.id.fragment_resultados);
            }

            return true;

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // Descomentar el otro código para que esto funcione :D
        //navController.popBackStack();
        //supportFinishAfterTransition();
        super.onBackPressed();
    }

    public void navegar(int fragmentoId){
        NavOptions navOptions = new NavOptions.Builder().setEnterAnim(R.anim.enter_anim)
                .setExitAnim(R.anim.exit_anim)
                .setPopUpTo(navController.getGraph().getStartDestination(), true)
                .build();
        navController.navigate(fragmentoId, null, navOptions);
        navController.popBackStack(fragmentoId, true);

    }

}