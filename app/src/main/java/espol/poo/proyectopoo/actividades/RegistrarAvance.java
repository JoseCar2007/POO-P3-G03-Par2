package espol.poo.proyectopoo.actividades;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;

public class RegistrarAvance extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_avance);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent i = this.getIntent();
        Actividad a = (Actividad) i.getSerializableExtra("ObjetoActividad");
        ((TextView) findViewById(R.id.id)).setText(String.valueOf(String.valueOf(a.getId())));
        ((TextView) findViewById(R.id.nombre)).setText(a.getNombre());
        ((Button) findViewById(R.id.btnGuardar)).setOnClickListener(v -> {
            Dialog d = new Dialog(this);
            d.setContentView(R.layout.advertencia_avance);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(false);
            d.findViewById(R.id.btnSi).setOnClickListener(view -> {
                a.setAvance(Integer.parseInt(((TextView) findViewById(R.id.avanceInput)).getText().toString()));
                d.dismiss();
                finish();
            });
            d.findViewById(R.id.btnNo).setOnClickListener(view -> {
                d.dismiss();
            });
            d.show();
        });
        ((Button) findViewById(R.id.btnCancelar)).setOnClickListener(v -> {
            finish();
        });
    }
}