package br.edu.ifgoiano.kronnos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class CronometroActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    /**
     * This one refers to the action of stopping or starting.
     * 00 = stop
     * 01 = start
     */
    private int state = 0;
    private TextView txtCrono;
    private Button btnStartStop;
    private Boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        if(savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            state = savedInstanceState.getInt("state");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        txtCrono = findViewById(R.id.txtCrono);
        btnStartStop = findViewById(R.id.btnStartStop);

        runTimer();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    public void onClickStartOrStop(View view) {
        state++;
        if(state == 1) {
            running = true;
            state = -1;
            btnStartStop.setText(getString(R.string.btnStop));
        } else {
            running = false;
            btnStartStop.setText(getString(R.string.btnResume));
        }
    }

    public void onClickReset(View view) {
        running = false;
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

                if(running)
                    seconds++;

                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putInt("state", state);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning != null && wasRunning)
            running = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}