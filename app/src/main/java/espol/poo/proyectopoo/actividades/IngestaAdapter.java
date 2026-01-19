package espol.poo.proyectopoo.actividades;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.RegistroAgua;
public class IngestaAdapter extends RecyclerView.Adapter<IngestaAdapter.IngestaViewHolder> {
    private ArrayList<RegistroAgua.TomaAgua> listaTomas;
    public IngestaAdapter(ArrayList<RegistroAgua.TomaAgua> listaTomas) {
        this.listaTomas = listaTomas;
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
        RegistroAgua.TomaAgua toma = listaTomas.get(position);

        String texto = toma.cantidadMl + " ml - " + toma.hora;
        holder.tvDetalle.setText(texto);
    }

    @Override
    public int getItemCount() {
        return listaTomas.size();
    }

    public static class IngestaViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetalle;

        public IngestaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDetalle = itemView.findViewById(R.id.tvDetalleAgua);
        }
    }

}
