package br.edu.ifgoiano.kronnos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private Spinner spHours;
    private Spinner spMinutes;
    private Spinner spSeconds;
    private TextView tvTimer;
    private Button btnStartStop;

    private int seconds = 0;
    private boolean running;
    private Boolean wasRunning;
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            state = savedInstanceState.getInt("state");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        spHours = findViewById(R.id.spHours);
        spMinutes = findViewById(R.id.spMins);
        spSeconds = findViewById(R.id.spSecs);
        tvTimer = findViewById(R.id.tvTimer);
        btnStartStop = findViewById(R.id.btnStartStopTimer);

        runTimer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClickStartOrStop(View view) {
        getSeconds();
        state++;
        if (state == 1) {
            running = true;
            state = -1;
            btnStartStop.setText(getString(R.string.btnStop));
        } else {
            running = false;
            btnStartStop.setText(getString(R.string.btnResume));
        }
    }

    private void getSeconds() {
        if (tvTimer.getText().equals("00:00:00")) {
            seconds += 3600 * Integer.parseInt(spHours.getSelectedItem().toString());
            seconds += (Integer.parseInt(spMinutes.getSelectedItem().toString()) % 3600) * 60;
            seconds += Integer.parseInt(spSeconds.getSelectedItem().toString());
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
                String time = String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, secs);

                tvTimer.setText(time);

                if (running && seconds > 0)
                    seconds--;
                
                if (seconds == 0) {
                    btnStartStop.setText(getString(R.string.btnStart));
                    state = 0;
                    running = false;
                }

                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
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
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}