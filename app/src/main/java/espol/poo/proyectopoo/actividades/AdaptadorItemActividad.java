package espol.poo.proyectopoo.actividades;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;
import espol.poo.proyectopoo.modelo.ActividadAcademica;
import espol.poo.proyectopoo.modelo.ActividadPersonal;

import java.util.ArrayList;
public class AdaptadorItemActividad extends RecyclerView.Adapter<AdaptadorItemActividad.ItemViewHolder>{
    /**
     * @param TYPE_ACADEMICA: Tipo de actividad académica
     * @param TYPE_PERSONAL: Tipo de actividad personal
     *                     Las dos variables anteriores sirven
     *                     para detectar cual subclase de AdaptadorItemActividad
     *                     estamos utilizando
     * @param listaActividades: Lista de actividades
     * @param context: Contexto de la actividad
     */
    private final int TYPE_ACADEMICA = 2;
    private final int TYPE_PERSONAL = 1;
    private ArrayList<Actividad> listaActividades;
    Context context;
    public AdaptadorItemActividad(Context context, ArrayList<Actividad> listaActividades){
        this.listaActividades = listaActividades;
        this.context = context;
    }

    /**
     * Metodo para detectar cual subclase de AdaptadorItemActividad
     * estamos utilizando
     * @param position posicion del elemento de la lista
     * @return devuelve el tipo de actividad a utilizar
     */
    @Override
    public int getItemViewType(int position) {
        if (listaActividades.get(position) instanceof ActividadAcademica) {
            return TYPE_ACADEMICA;
        }
        return TYPE_PERSONAL;
    }

    /**
     *
     * @param parent   El contenedor en el cual se inflará el layout del item.
     * @param viewType Entero para verificar si inflar actividad academica o personal
     * @return Devuelve el viewHolder correspondiente a la actividad
     */
    @NonNull
    @Override
    public AdaptadorItemActividad.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        if(viewType == TYPE_ACADEMICA){
             view = inflater.inflate(R.layout.item_ac_academica, parent, false);
             return new AdaptadorItemActividad.AcademicaViewHolder(view, context);
        }
        else{
            Log.d("DEBUG: ", "Tipo de actividad personal (en desarrollo)");
            view = inflater.inflate(R.layout.item_ac_personal, parent, false);
        }
        return new AdaptadorItemActividad.ItemViewHolder(view, context);
    }

    /**
     *
     * @param holder   El ViewHolder con el cual se trabajará
     * @param position la posicion en la lista del elemento con el cual
     *                 estamos trabajando.
     */
    @Override
    public void onBindViewHolder(@NonNull AdaptadorItemActividad.ItemViewHolder holder, int position) {
        Actividad ac = listaActividades.get(position);
        holder.id.setText(String.valueOf(ac.getId()));
        holder.nombre.setText(ac.getNombre());
        holder.fecha.setText(ac.getFecha());
        holder.prioridad.setText(ac.getPrioridad());
        holder.avance.setText(ac.getAvance() + "%");
        holder.ac = listaActividades.get(position);
        //Boton para ir a la actividad de Detalles
        holder.btnDetalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(context, PantallaDetalles.class);
                intento.putExtra("ObjetoActividad", ac);
                context.startActivity(intento);
            }
        });
        //Boton para ir a la actividad de registrarAvance (esta no se muestra
        //si la actividad tiene avance = 100)
        if(ac.getAvance() != 100) {
            holder.btnAvance.setVisibility(View.VISIBLE);
            holder.btnAvance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, RegistrarAvance.class);
                    intento.putExtra("Posicion", position);
                    context.startActivity(intento);
                    notifyItemChanged(position);
                }
            });
        }
        else{
            holder.btnAvance.setVisibility(View.GONE);
        }
        //Boton para eliminar actividad
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(context);
                d.setContentView(R.layout.advertencia_avance);
                d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d.setCancelable(false);
                d.findViewById(R.id.btnSi).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Actividad.removerActividad(ac);
                        notifyItemRemoved(position);
                        d.dismiss();
                    }
                });
                d.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                d.show();
            }
        });
        //Si se tiene un Holder de Actividad academica se colocarán los
        //botones de iniciar deepWork y pomodoro
        if(holder instanceof AcademicaViewHolder){
            AcademicaViewHolder acHolder = (AcademicaViewHolder) holder;
            acHolder.btnDeepWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, DeepWorkActivity.class);
                    intento.putExtra("Posicion", position);
                    context.startActivity(intento);
                }
            });
            acHolder.btnPomodoro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intento = new Intent(context, PomodoroActivity.class);
                    intento.putExtra("Posicion",  position);
                    context.startActivity(intento);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }
    //Clase interna para gestionar el ViewHolder de la actividad correspondiente
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parent;
        TextView id, nombre, fecha, prioridad, avance;
        Actividad ac;
        Button btnAvance, btnEliminar, btnDetalles;
        public ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            id = itemView.findViewById(R.id.itId);
            nombre = itemView.findViewById(R.id.itNombre);
            fecha = itemView.findViewById(R.id.itFecha);
            prioridad = itemView.findViewById(R.id.itPrioridad);
            avance = itemView.findViewById(R.id.itAvance);
            btnAvance = itemView.findViewById(R.id.btnAvance);
            btnDetalles = itemView.findViewById(R.id.btnDetalles);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            parent = itemView.findViewById(R.id.btnLayout);
        }
    }
    //Subclase en caso de que se tenga una actividad academica
    public static class AcademicaViewHolder extends ItemViewHolder{
        Button btnPomodoro, btnDeepWork;
        public AcademicaViewHolder(@NonNull View itemView, Context context) {
            super(itemView, context);
            btnPomodoro = itemView.findViewById(R.id.btnPomodoro);
            btnDeepWork = itemView.findViewById(R.id.btnDeepWork);
        }
    }
}
