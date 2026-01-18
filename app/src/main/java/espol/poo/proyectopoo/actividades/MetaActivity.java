package espol.poo.proyectopoo.actividades;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import espol.poo.proyectopoo.modelo.RegistroAgua;
import androidx.appcompat.app.AppCompatActivity;
import espol.poo.proyectopoo.R;
public class MetaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meta);


        TextView txtActual = findViewById(R.id.txtMetaActual);
        EditText input = findViewById(R.id.inputNuevaMeta);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        RegistroAgua modelo = new RegistroAgua();


        txtActual.setText(modelo.getMetaDiaria() + " ml");


        btnGuardar.setOnClickListener(v -> {
            String texto = input.getText().toString();
            if(!texto.isEmpty()){
                modelo.establecerMeta(Integer.parseInt(texto));
                finish();
            }
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}


