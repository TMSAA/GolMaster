package com.example.golmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

    private final Context context;
    private final List<Evento> eventos;

    public EventoAdapter(Context context, List<Evento> eventos) {
        this.context = context;
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_evento, parent, false);
        return new EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
        Evento evento = eventos.get(position);

        // Configurar las vistas
        holder.tvMinutoEvento.setText(evento.getMinuto() + "'");
        holder.tvJugadorEvento.setText(evento.getJugador());
        holder.tvDescripcionEvento.setText(evento.getDescripcion());

        // Configurar el ícono según el tipo de evento
        switch (evento.getTipo()) {
            case "gol":
                holder.ivIconoEvento.setImageResource(R.drawable.ic_gol);
                break;
            case "amarilla":
                holder.ivIconoEvento.setImageResource(R.drawable.ic_tarjeta_amarilla);
                break;
            case "roja":
                holder.ivIconoEvento.setImageResource(R.drawable.ic_tarjeta_roja);
                break;
            default:
                holder.ivIconoEvento.setImageResource(R.drawable.ic_evento_placeholder);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    static class EventoViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIconoEvento;
        TextView tvMinutoEvento, tvJugadorEvento, tvDescripcionEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIconoEvento = itemView.findViewById(R.id.ivIconoEvento);
            tvMinutoEvento = itemView.findViewById(R.id.tvMinutoEvento);
            tvJugadorEvento = itemView.findViewById(R.id.tvJugadorEvento);
            tvDescripcionEvento = itemView.findViewById(R.id.tvDescripcionEvento);
        }
    }
}
