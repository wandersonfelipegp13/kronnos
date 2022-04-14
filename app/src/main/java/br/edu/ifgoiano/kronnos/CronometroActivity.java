package br.edu.ifgoiano.kronnos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

public class CronometroActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean execution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        runTimer();
    }

    public void onClickStart(View view) {
        execution = true;
    }

    public void onClickStop(View view) {
        execution = false;
    }

    public void onClickReset(View view) {
        execution = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView txtCrono = findViewById(R.id.txtCrono);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);

                txtCrono.setText(time);

                if(execution) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

}