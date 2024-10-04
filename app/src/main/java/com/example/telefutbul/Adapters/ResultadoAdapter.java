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

import com.example.telefutbul.DTOs.Resultado;
import com.example.telefutbul.DTOs.Team;
import com.example.telefutbul.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ResultadoAdapter extends RecyclerView.Adapter<ResultadoAdapter.ResultadoViewHolder>{

    private List<Resultado> listaResultados;
    private Context context;
    private RecyclerView recyclerView;

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Resultado> getListaResultados() {
        return listaResultados;
    }

    public void setListaResultados(List<Resultado> listaResultados) {
        this.listaResultados = listaResultados;
    }

    public void agregarElementos(ArrayList<Resultado> nuevosElementos) {
        int posicionInicial = listaResultados==null?0:listaResultados.size();
        listaResultados.addAll(0,nuevosElementos);
        notifyItemRangeInserted(0, nuevosElementos.size());
        recyclerView.scrollToPosition(0);
    }

    public void eliminarHastaPosicion(int posicion) {
        if (posicion > 0 && posicion <= listaResultados.size()) {
            listaResultados.subList(0, posicion).clear();
            notifyItemRangeRemoved(0, posicion);  // Notificar que se han eliminado elementos
        }
    }

    @NonNull
    @Override
    public ResultadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resul_rv_item, parent, false);
        return new ResultadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadoViewHolder holder, int position) {
        Resultado resultado = listaResultados.get(position);
        holder.resultado = resultado;
        ((TextView) holder.itemView.findViewById(R.id.text_nombre_resul)).setText(resultado.getStrEvent());
        ((TextView) holder.itemView.findViewById(R.id.text_ronda_resul)).setText("N°"+resultado.getIntRound());
        ((TextView) holder.itemView.findViewById(R.id.text_local_resul)).setText(resultado.getStrHomeTeam());
        ((TextView) holder.itemView.findViewById(R.id.text_visi_resul)).setText(resultado.getStrAwayTeam());
        ((TextView) holder.itemView.findViewById(R.id.text_resul_resul)).setText(resultado.obtenerResultado());
        ((TextView) holder.itemView.findViewById(R.id.text_fecha_resul)).setText(resultado.obtenerFecha());
        ((TextView) holder.itemView.findViewById(R.id.text_espectadores_resul)).setText(resultado.getIntSpectators());
        ImageView banner = holder.itemView.findViewById(R.id.image_banner_resul);
        Picasso.get().load(resultado.getStrThumb()).into(banner);
        holder.itemView.findViewById(R.id.adorno_resul).setBackgroundColor(ContextCompat.getColor(getContext(), resultado.getIdColor()));
    }

    @Override
    public int getItemCount() {
        if(listaResultados == null){
            return 0;
        }else{
            return listaResultados.size();

        }
    }

    public class ResultadoViewHolder extends RecyclerView.ViewHolder{
        Resultado resultado;
        public ResultadoViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
