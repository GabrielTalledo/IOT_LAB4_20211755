package com.example.telefutbul.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefutbul.DTOs.League;
import com.example.telefutbul.R;

import java.util.List;

public class LigaAdapter extends RecyclerView.Adapter<LigaAdapter.LigaViewHolder>{

    private List<League> listaLigas;
    private Context context;
    private List<Integer> listaColores = List.of(R.color.md_theme_inversePrimary_highContrast,
                                                        R.color.md_theme_inversePrimary_mediumContrast,
                                                        R.color.md_theme_primaryContainer_mediumContrast,
                                                        R.color.md_theme_primaryContainer_highContrast);

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<League> getListaLigas() {
        return listaLigas;
    }

    public void setListaLigas(List<League> listaLigas) {
        this.listaLigas = listaLigas;
    }

    @NonNull
    @Override
    public LigaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liga_rv_item, parent, false);
        return new LigaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LigaViewHolder holder, int position) {
        League liga = listaLigas.get(position);
        holder.liga = liga;
        ((TextView) holder.itemView.findViewById(R.id.text_nombre_liga)).setText(liga.getStrLeague());
        ((TextView) holder.itemView.findViewById(R.id.text_id_liga)).setText(liga.getIdLeague());
        ((TextView) holder.itemView.findViewById(R.id.text_alt1_liga)).setText(liga.obtenerNombresAlternativos().get(0));
        ((TextView) holder.itemView.findViewById(R.id.text_alt2_liga)).setText(liga.obtenerNombresAlternativos().get(1));
        holder.itemView.findViewById(R.id.adorno_pos).setBackgroundColor(ContextCompat.getColor(getContext(), listaColores.get(position % listaColores.size())));
    }

    @Override
    public int getItemCount() {
        return listaLigas.size();
    }

    public class LigaViewHolder extends RecyclerView.ViewHolder{

        League liga;

        public LigaViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
