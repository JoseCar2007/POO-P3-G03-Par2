package espol.poo.proyectopoo.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.proyectopoo.R;
import espol.poo.proyectopoo.modelo.Actividad;
import espol.poo.proyectopoo.modelo.ActividadAcademica;
import espol.poo.proyectopoo.modelo.TecnicasEnfoque;

public class DeepWorkActivity extends AppCompatActivity {
    /**
     * @param txtTimer: TextView para mostrar el tiempo restante
     * @param txtActividad: TextView para mostrar la actividad
     * @param timer: ContadorDownTimer para el tiempo restante
     * @param tiempoRestante: Tiempo restante en milisegundos
     * @param tiempoActual: Tiempo actual en minutos
     * @param a: Actividad académica a la que se le va a registrar la sesión
     */

    private TextView txtTimer, txtActividad;
    private CountDownTimer timer;
    private long tiempoRestante = 45 * 60 * 1000;
    private int tiempoActual = 45;
    private ActividadAcademica a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_work);

        txtTimer = findViewById(R.id.txtTimerDW);
        txtActividad = findViewById(R.id.txtActividad);
        Intent i = getIntent();
        int pos = i.getIntExtra("Posicion", 0);
        a = (ActividadAcademica) Actividad.getActividades().get(pos);
        txtActividad.setText("Actividad: " + a.getNombre());
        actualizar();
        ((Button) findViewById(R.id.btnRegreso)).setOnClickListener(v -> {
            finish();
        });
    }
    public void set45(View v) {
        tiempoRestante = 45 * 60 * 1000;
        tiempoActual = 45;
        actualizar();
    }
    public void set60(View v){
        tiempoRestante = 60 * 60 * 1000;
        tiempoActual = 60;
        actualizar();
    }
    public void set90(View v) {
        tiempoRestante = 90 * 60 * 1000;
        tiempoActual = 90;
        actualizar();
    }
    public void set10(View v) {
        tiempoRestante = 10 * 1000;
        tiempoActual = 10;
        actualizar();
    }

    /**
     * Metodo para iniciar la sesión de estudio
     * @param v: View en la cual se encuentra el CounDownTimer
     *         en esta implementacion no se usa
     */
    public void iniciar(View v) {
        timer = new CountDownTimer(tiempoRestante, 1000) {
            public void onTick(long millisUntilFinished) {
                tiempoRestante = millisUntilFinished;
                actualizar();
            }

            public void onFinish() {
                guardarSesion();
            }
        }.start();
    }

    /**
     * Metodo para finalizar la sesión de estudio
     *
     * @param v: como es un metodo de un boton del layout
     *         es necesario poner el View v para que funcione
     *         pero en este caso no se usa
     */
    public void finalizar(View v) {
        if(timer != null) timer.cancel();

        if(tiempoActual == 45) tiempoRestante = 45 * 60 * 1000;
        if(tiempoActual == 90) tiempoRestante = 90 * 60 * 1000;
        if(tiempoActual == 60) tiempoRestante = 60 * 60 * 1000;
        if(tiempoActual == 10) tiempoRestante = 10 * 1000;

        guardarSesion();
        actualizar();
    }

    /**
     * Metodo para reiniciar la sesión de estudio
     * @param v: como es un metodo de un boton del layout
     *           es necesario poner el View v para que funcione
     *           pero en este caso no se usa
     */
    public void pausar(View v) {
        if (timer != null) timer.cancel();
    }

    /**
     * Metodo para actualizar el temporizador
     *
     */
    private void actualizar() {
        int m = (int) (tiempoRestante / 1000) / 60;
        int s = (int) (tiempoRestante / 1000) % 60;
        txtTimer.setText(String.format("%02d:%02d", m, s));
    }

    /**
     * Metodo para guardar la sesión de estudio
     * en la actividad academica
     */
    private void guardarSesion(){
        a.registrarTecnicaEnfoque(new TecnicasEnfoque("Deep Work", tiempoActual, 0, 1));
        Toast.makeText(DeepWorkActivity.this,
                "Sesión Deep Work registrada", Toast.LENGTH_SHORT).show();
    }
}
