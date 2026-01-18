package espol.poo.proyectopoo.actividades;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import espol.poo.proyectopoo.modelo.RegistroSostenibilidad;
import espol.poo.proyectopoo.R;
public class SostenibilidadAdapter extends RecyclerView.Adapter<SostenibilidadAdapter.ViewHolder> {

    private RegistroSostenibilidad modelo;

    public SostenibilidadAdapter(RegistroSostenibilidad modelo) {
        this.modelo = modelo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sostenibilidad, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = modelo.getNombreAccionFija(position);
        int dias = modelo.getContadorSemana(position);
        String calificacion = modelo.getCalificacionTexto(position);

        holder.tvNombre.setText(nombre);
        holder.tvContador.setText(dias + " / 7");
        holder.tvBadge.setText(calificacion);

        // CAMBIAR COLOR DEL BADGE (Verde vs Naranja)
        // Obtenemos el fondo del TextView para cambiarle el color
        GradientDrawable background = (GradientDrawable) holder.tvBadge.getBackground();

        // 5 o más es verde, menos es naranja
        if (dias >= 5) {
            background.setColor(Color.parseColor("#4CAF50")); // VERDE (Ej: Gran Movilidad)
        } else {
            background.setColor(Color.parseColor("#FF9800")); // NARANJA (Ej: Necesita mejorar)
        }
    }

    @Override
    public int getItemCount() {
        return modelo.getCantidadAcciones(); // Hay 4 acciones fijas
    }

    // Clase interna para guardar las referencias de la fila
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvContador, tvBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreAccion);
            tvContador = itemView.findViewById(R.id.tvContador);
            tvBadge = itemView.findViewById(R.id.tvBadge);
        }
    }
}