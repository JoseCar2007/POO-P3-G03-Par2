package espol.poo.proyectopoo.actividades;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import espol.poo.proyectopoo.R;

public class PomodoroActivity extends AppCompatActivity {

    private TextView txtTimer;
    private Button btnIniciar, btnPausar, btnReiniciar;
    private CountDownTimer timer;
    private long tiempoRestante = 25 * 60 * 1000;
    private boolean corriendo = false;
    private int tiempoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        txtTimer = findViewById(R.id.txtTimer);
        actualizarTimer();

    }
    public void ciclo25(View v) {
        tiempoRestante = 25 * 60 * 1000;
        tiempoActual = 25;
        actualizarTimer();
    }
    public void ciclo5(View v){
        tiempoRestante = 5 * 60 * 1000;
        tiempoActual = 5;
        actualizarTimer();
    }
    public void ciclo15(View v) {
        tiempoRestante = 15 * 60 * 1000;
        tiempoActual = 15;
        actualizarTimer();
    }
    public void iniciar(View v) {
        timer = new CountDownTimer(tiempoRestante, 1000) {
            public void onTick(long millisUntilFinished) {
                tiempoRestante = millisUntilFinished;
                actualizarTimer();
            }

            public void onFinish() {
                //  AQUÍ REGISTRAS LA SESIÓN EN EL HISTORIAL
                Toast.makeText(PomodoroActivity.this,
                        "Ciclo Pomodoro registrado", Toast.LENGTH_SHORT).show();
            }
        }.start();
        corriendo = true;
    }

    public void pausar(View v) {
        if (corriendo) {
            timer.cancel();
            corriendo = false;
        }
    }

    public void reiniciar(View v) {
        if (timer != null) timer.cancel();

        if(tiempoActual == 25) tiempoRestante = 25 * 60 * 1000;
        if(tiempoActual == 5) tiempoRestante = 5 * 60 * 1000;
        if(tiempoActual == 15) tiempoRestante = 15 * 60 * 1000;
        actualizarTimer();
    }

    private void actualizarTimer() {
        int minutos = (int) (tiempoRestante / 1000) / 60;
        int segundos = (int) (tiempoRestante / 1000) % 60;
        txtTimer.setText(String.format("%02d:%02d", minutos, segundos));
    }
}

