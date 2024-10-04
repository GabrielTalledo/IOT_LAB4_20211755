package com.example.telefutbul.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefutbul.DTOs.League;
import com.example.telefutbul.DTOs.Team;
import com.example.telefutbul.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PosicionAdapter extends RecyclerView.Adapter<PosicionAdapter.PosicionViewHolder>{
    private List<Team> listaTeams;
    private Context context;
    private List<Integer> listaColores = List.of(R.color.md_theme_inversePrimary_highContrast,
            R.color.md_theme_inversePrimary_mediumContrast,
            R.color.md_theme_primaryContainer_mediumContrast,
            R.color.md_theme_primaryContainer_highContrast,
            R.color.md_theme_tertiaryFixedDim_mediumContrast,
            R.color.md_theme_tertiaryContainer_mediumContrast,
            R.color.md_theme_tertiaryFixedDim,
            R.color.md_theme_tertiaryFixed);

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Integer> getListaColores() {
        return listaColores;
    }

    public void setListaColores(List<Integer> listaColores) {
        this.listaColores = listaColores;
    }

    public List<Team> getListaTeams() {
        return listaTeams;
    }

    public void setListaTeams(List<Team> listaTeams) {
        this.listaTeams = listaTeams;
    }

    @NonNull
    @Override
    public PosicionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pos_rv_item, parent, false);
        return new PosicionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosicionViewHolder holder, int position) {
        Team team = listaTeams.get(position);
        holder.team = team;
        ((TextView) holder.itemView.findViewById(R.id.text_nombre_pos)).setText(team.getStrTeam());
        ((TextView) holder.itemView.findViewById(R.id.text_ranking_pos)).setText("N°"+team.getIntRank());
        ((TextView) holder.itemView.findViewById(R.id.text_res_pos)).setText(team.obtenerResultados());
        ((TextView) holder.itemView.findViewById(R.id.text_goals_pos)).setText(team.obtenerGoles());
        ImageView badge = holder.itemView.findViewById(R.id.image_badge_pos);
        Picasso.get().load(team.getStrBadge()).into(badge);
        holder.itemView.findViewById(R.id.adorno_pos).setBackgroundColor(ContextCompat.getColor(getContext(), listaColores.get(position % listaColores.size())));
    }

    @Override
    public int getItemCount() {
        return listaTeams.size();
    }


    public class PosicionViewHolder extends RecyclerView.ViewHolder{
        Team team;
        public PosicionViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
