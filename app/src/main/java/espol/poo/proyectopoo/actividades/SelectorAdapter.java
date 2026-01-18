package espol.poo.proyectopoo.actividades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import espol.poo.proyectopoo.R;

public class SelectorAdapter extends RecyclerView.Adapter<SelectorAdapter.ViewHolder> {

    private String[] acciones;
    private boolean[] estados;

    public SelectorAdapter(String[] acciones) {
        this.acciones = acciones;
        this.estados = new boolean[acciones.length];
    }

    // Método para marcar casillas si ya estaban guardadas
    public void setEstadosIniciales(ArrayList<Integer> indicesYaMarcados) {
        for (int i : indicesYaMarcados) {
            if (i < estados.length) estados[i] = true;
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_seleccion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Poner el texto
        holder.checkBox.setText((position + 1) + ". " + acciones[position]);

        // Primero quitamos el listener para que no se dispare solo
        holder.checkBox.setOnCheckedChangeListener(null);

        // Ponemos el estado real (true/false)
        holder.checkBox.setChecked(estados[position]);

        // Guardar el cambio cuando el usuario toca
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            estados[position] = isChecked;
        });
    }

    @Override
    public int getItemCount() {
        return acciones.length;
    }

    // --- MÉTODO CLAVE PARA EL GUARDADO ---
    // Devuelve los índices de las filas que están marcadas (Ej: [0, 3])
    public int[] obtenerIndicesSeleccionados() {
        ArrayList<Integer> lista = new ArrayList<>();
        for (int i = 0; i < estados.length; i++) {
            if (estados[i]) {
                lista.add(i);
            }
        }
        // Convertir a int[]
        int[] resultado = new int[lista.size()];
        for (int i = 0; i < lista.size(); i++) resultado[i] = lista.get(i);
        return resultado;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.chkAccion);
        }
    }
}