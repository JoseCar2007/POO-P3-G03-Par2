package espol.poo.proyectopoo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class DeepWorkActivity extends AppCompatActivity {

    private TextView txtTimer;
    private CountDownTimer timer;
    private long tiempoRestante = 45 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_work);

        txtTimer = findViewById(R.id.txtTimerDW);
        actualizar();

        findViewById(R.id.btnIniciarDW).setOnClickListener(v -> iniciar());
        findViewById(R.id.btnFinalizarDW).setOnClickListener(v -> finish());
    }

    private void iniciar() {
        timer = new CountDownTimer(tiempoRestante, 1000) {
            public void onTick(long millisUntilFinished) {
                tiempoRestante = millisUntilFinished;
                actualizar();
            }

            public void onFinish() {
                Toast.makeText(DeepWorkActivity.this,
                        "Sesión Deep Work registrada", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void actualizar() {
        int m = (int) (tiempoRestante / 1000) / 60;
        int s = (int) (tiempoRestante / 1000) % 60;
        txtTimer.setText(String.format("%02d:%02d", m, s));
    }
}
