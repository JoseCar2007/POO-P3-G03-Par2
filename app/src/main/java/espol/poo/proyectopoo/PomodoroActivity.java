package espol.poo.proyectopoo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class PomodoroActivity extends AppCompatActivity {

    private TextView txtTimer;
    private Button btnIniciar, btnPausar, btnReiniciar;
    private CountDownTimer timer;
    private long tiempoRestante = 25 * 60 * 1000;
    private boolean corriendo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        txtTimer = findViewById(R.id.txtTimer);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnReiniciar = findViewById(R.id.btnReiniciar);

        actualizarTimer();

        btnIniciar.setOnClickListener(v -> iniciar());
        btnPausar.setOnClickListener(v -> pausar());
        btnReiniciar.setOnClickListener(v -> reiniciar());
    }

    private void iniciar() {
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

    private void pausar() {
        if (corriendo) {
            timer.cancel();
            corriendo = false;
        }
    }

    private void reiniciar() {
        if (timer != null) timer.cancel();
        tiempoRestante = 25 * 60 * 1000;
        actualizarTimer();
    }

    private void actualizarTimer() {
        int minutos = (int) (tiempoRestante / 1000) / 60;
        int segundos = (int) (tiempoRestante / 1000) % 60;
        txtTimer.setText(String.format("%02d:%02d", minutos, segundos));
    }
}

