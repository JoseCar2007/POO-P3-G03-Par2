package espol.poo.proyectopoo.actividades;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import espol.poo.proyectopoo.modelo.IngestaAgua;
import espol.poo.proyectopoo.R;
public class IngestaAdapter extends RecyclerView.Adapter<IngestaAdapter.IngestaViewHolder> {
    private ArrayList<IngestaAgua> listaIngestas;

    public IngestaAdapter(ArrayList<IngestaAgua> listaIngestas) {
        this.listaIngestas = listaIngestas;
    }
    @NonNull
    @Override
    public IngestaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingesta, parent, false);
        return new IngestaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngestaViewHolder holder, int position) {
        IngestaAgua ingesta = listaIngestas.get(position);

        // Formato: "250.0 ml - 10:30 AM"
        String texto = ingesta.getCantidadMl() + " ml - " + ingesta.getHoraToma();
        holder.tvDetalle.setText(texto);
    }

    @Override
    public int getItemCount() {
        return listaIngestas.size();
    }

    public static class IngestaViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetalle;

        public IngestaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDetalle = itemView.findViewById(R.id.tvDetalleAgua);
        }
    }

}
