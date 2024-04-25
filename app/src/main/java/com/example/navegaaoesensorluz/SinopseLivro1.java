package com.example.navegaaoesensorluz;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SinopseLivro1 extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isHorizontal = false;

    private ImageView sn1;
    private ImageView sn2;
    private ImageView sn3;
    private TextView sinopseTextView1;
    private TextView sinopseTextView2;
    private TextView sinopseTextView3;

    private TextView tituloTextView1;
    private TextView tituloTextView2;
    private TextView tituloTextView3;

    private ImageView botaoV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinopse_livro1);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometerSensor == null) {
            Toast.makeText(this, "O dispositivo não tem o sensor necessário.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sn1 = findViewById(R.id.s1);
        sn2 = findViewById(R.id.s2);
        sn3 = findViewById(R.id.s3);
        sinopseTextView1 = findViewById(R.id.textViewSynopsis1);
        sinopseTextView2 = findViewById(R.id.textViewSynopsis2);
        sinopseTextView3 = findViewById(R.id.textViewSynopsis3);
        tituloTextView1 = findViewById(R.id.textViewTitle1);
        tituloTextView2 = findViewById(R.id.textViewTitle2);
        tituloTextView3 = findViewById(R.id.textViewTitle3);

        // Defini a visibilidade inicial das sinopses e títulos
        sinopseTextView1.setVisibility(View.VISIBLE);
        tituloTextView1.setVisibility(View.VISIBLE);
        sinopseTextView2.setVisibility(View.GONE);
        tituloTextView2.setVisibility(View.GONE);
        sinopseTextView3.setVisibility(View.GONE);
        tituloTextView3.setVisibility(View.GONE);

        // Configuração do OnClickListener para o botão 1
        sn1.setOnClickListener(v -> {
            sinopseTextView1.setVisibility(View.VISIBLE);
            tituloTextView1.setVisibility(View.VISIBLE);
            sinopseTextView2.setVisibility(View.GONE);
            tituloTextView2.setVisibility(View.GONE);
            sinopseTextView3.setVisibility(View.GONE);
            tituloTextView3.setVisibility(View.GONE);
        });

        // Configuração do OnClickListener para o botão 2
        sn2.setOnClickListener(v -> {
            sinopseTextView1.setVisibility(View.GONE);
            tituloTextView1.setVisibility(View.GONE);
            sinopseTextView2.setVisibility(View.VISIBLE);
            tituloTextView2.setVisibility(View.VISIBLE);
            sinopseTextView3.setVisibility(View.GONE);
            tituloTextView3.setVisibility(View.GONE);
        });

        // Configuração do OnClickListener para o botão 3
        sn3.setOnClickListener(v -> {
            sinopseTextView1.setVisibility(View.GONE);
            tituloTextView1.setVisibility(View.GONE);
            sinopseTextView2.setVisibility(View.GONE);
            tituloTextView2.setVisibility(View.GONE);
            sinopseTextView3.setVisibility(View.VISIBLE);
            tituloTextView3.setVisibility(View.VISIBLE);
        });

        botaoV = findViewById(R.id.voltar);

        botaoV.setOnClickListener(this::navigate);
    }

    @Override
    public void onBackPressed() {
        // Quando o botão de voltar é pressionado, retornar ao estado inicial
        super.onBackPressed();
        sinopseTextView1.setVisibility(View.VISIBLE);
        tituloTextView1.setVisibility(View.VISIBLE);
        sinopseTextView2.setVisibility(View.GONE);
        tituloTextView2.setVisibility(View.GONE);
        sinopseTextView3.setVisibility(View.GONE);
        tituloTextView3.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(accelerometerListener);
    }

    public void navigate(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private final SensorEventListener accelerometerListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];

            if (!isHorizontal && Math.abs(x) > 5 && Math.abs(y) < 5) {
                isHorizontal = true;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else if (isHorizontal && Math.abs(x) < 5 && Math.abs(y) > 5) {
                isHorizontal = false;
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
