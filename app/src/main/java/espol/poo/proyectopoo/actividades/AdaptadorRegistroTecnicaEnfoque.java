package espol.poo.proyectopoo.actividades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.TecnicasEnfoque;

public class AdaptadorRegistroTecnicaEnfoque extends RecyclerView.Adapter<AdaptadorRegistroTecnicaEnfoque.TecEnfoqueViewHolder> {
    private ArrayList<TecnicasEnfoque> listaTecnicas;
    Context context;
    public AdaptadorRegistroTecnicaEnfoque(Context context, ArrayList<TecnicasEnfoque> listaTecnicas){
        this.listaTecnicas = listaTecnicas;
        this.context = context;
    }
    @NonNull
    @Override
    public AdaptadorRegistroTecnicaEnfoque.TecEnfoqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.tecnica_enfoque_registro, parent, false);
        return new AdaptadorRegistroTecnicaEnfoque.TecEnfoqueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRegistroTecnicaEnfoque.TecEnfoqueViewHolder holder, int position) {
        holder.fechaSesion.setText(listaTecnicas.get(position).getFecha());
        holder.tecnicaAplicada.setText(listaTecnicas.get(position).getTecnica());
        holder.duracion.setText(String.valueOf(listaTecnicas.get(position).getDuracion()));
    }

    @Override
    public int getItemCount() {
        return listaTecnicas.size();
    }
    public static class TecEnfoqueViewHolder extends RecyclerView.ViewHolder {
        TextView fechaSesion, tecnicaAplicada, duracion;
        public TecEnfoqueViewHolder(@NonNull View itemView) {
            super(itemView);
            fechaSesion = itemView.findViewById(R.id.fechaSesion);
            tecnicaAplicada = itemView.findViewById(R.id.tecnicaAplicada);
            duracion = itemView.findViewById(R.id.duracion);
        }
    }
}
