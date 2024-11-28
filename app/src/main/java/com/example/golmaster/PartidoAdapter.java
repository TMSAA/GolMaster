package com.example.golmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PartidoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TIPO_FECHA = 0;
    private final int TIPO_PARTIDO = 1;

    private List<ElementoLista> elementos;

    public PartidoAdapter(List<ElementoLista> elementos) {
        this.elementos = elementos;
    }

    @Override
    public int getItemViewType(int position) {
        return elementos.get(position).getTipo();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TIPO_FECHA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fecha, parent, false);
            return new FechaViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partido, parent, false);
            return new PartidoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ElementoLista elemento = elementos.get(position);

        if (holder instanceof FechaViewHolder) {
            ((FechaViewHolder) holder).bind(elemento.getFecha());
        } else if (holder instanceof PartidoViewHolder) {
            ((PartidoViewHolder) holder).bind(elemento.getPartido());
        }
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public static class FechaViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha;

        public FechaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
        }

        public void bind(String fecha) {
            tvFecha.setText(fecha);
        }
    }

    public static class PartidoViewHolder extends RecyclerView.ViewHolder {
        TextView tvHorario, tvEquipoLocal, tvEquipoVisitante;

        public PartidoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHorario = itemView.findViewById(R.id.tvHorario);
            tvEquipoLocal = itemView.findViewById(R.id.tvEquipoLocal);
            tvEquipoVisitante = itemView.findViewById(R.id.tvEquipoVisitante);
        }

        public void bind(Partido partido) {
            if (partido.getHorario() != null) {
                Date horarioDate = partido.getHorario().toDate();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                tvHorario.setText(sdf.format(horarioDate));
            } else {
                tvHorario.setText("Horario no disponible");
            }

            tvEquipoLocal.setText(partido.getNombreEquipoLocal());
            tvEquipoVisitante.setText(partido.getNombreEquipoVisitante());
        }
    }

    public void actualizarLista(List<ElementoLista> nuevosElementos) {
        this.elementos.clear();
        this.elementos.addAll(nuevosElementos);
        notifyDataSetChanged();
    }
}
