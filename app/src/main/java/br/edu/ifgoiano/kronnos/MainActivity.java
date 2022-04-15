package br.edu.ifgoiano.kronnos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnCrono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCronometro(View view) {
        Intent intent = new Intent(MainActivity.this, CronometroActivity.class);
        startActivity(intent);
    }

    public void onClickTimer(View view) {
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        startActivity(intent);
    }

}