package br.edu.ifgoiano.kronnos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CronometroActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean execution;
    /**
     * This one refers to the action of stopping or starting.
     * 00 = stop
     * 01 = start
     */
    private int state = 0;
    private TextView txtCrono;
    private Button btnStartStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        txtCrono = findViewById(R.id.txtCrono);
        btnStartStop = findViewById(R.id.btnStartStop);

        runTimer();
    }

    public void onClickStartOrStop(View view) {
        state++;
        if(state == 1) {
            execution = true;
            state = -1;
            btnStartStop.setText(getString(R.string.btnStop));
        } else {
            execution = false;
            btnStartStop.setText(getString(R.string.btnStart));
        }
    }

    public void onClickReset(View view) {
        execution = false;
        seconds = 0;
        btnStartStop.setText(getString(R.string.btnStart));
        state = 0;
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.US,"%02d:%02d:%02d", hours, minutes, secs);

                txtCrono.setText(time);

                if(execution) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

}