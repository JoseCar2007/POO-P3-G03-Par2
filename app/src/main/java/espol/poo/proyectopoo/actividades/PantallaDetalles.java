package espol.poo.proyectopoo.actividades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.zip.Inflater;

import espol.poo.proyectopoo.R;

public class PantallaDetalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles);
        LayoutInflater i = LayoutInflater.from(this);
        ViewGroup parent = findViewById(R.id.linearPDetalles);
        i.inflate(R.layout.item_detalles_texto_academica, parent, true);
    }
}