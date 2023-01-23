package com.jherrera.sensorespruebadiagnostica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ProximidadActivity extends AppCompatActivity {

    private TextView textViewResultado;
    private Sensor sensor;
    private SensorManager administradorDeSensor;
    private SensorEventListener eventosSensor;
    private ConstraintLayout mainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximidad);
        setInitComponents();
    }

    private void setInitComponents() {
        textViewResultado = findViewById(R.id.textViewResultado);
        mainContent = findViewById(R.id.layout_proximidad);
        administradorDeSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor =  administradorDeSensor.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensor == null) {
            Toast.makeText(this, "Su dispositivo no tiene el sensor", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Sensor de proximidad detectado", Toast.LENGTH_LONG).show();
        }

        eventosSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                double valueSensor = sensorEvent.values[0];
                textViewResultado.setText("VALOR: "+valueSensor);

                if (valueSensor < sensor.getMaximumRange()){
                    textViewResultado.setBackground(ContextCompat.getDrawable(ProximidadActivity.this, R.drawable.text_view_red));
                    textViewResultado.setTextColor(Color.WHITE);
                    mainContent.setBackground(ContextCompat.getDrawable(ProximidadActivity.this, R.drawable.background_blue));
                }else {
                    textViewResultado.setBackground(ContextCompat.getDrawable(ProximidadActivity.this, R.drawable.text_view_green));
                    textViewResultado.setTextColor(Color.BLACK);
                    mainContent.setBackground(ContextCompat.getDrawable(ProximidadActivity.this, R.drawable.background_white));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        iniciarSensor();
    }

    private void iniciarSensor() {
        administradorDeSensor.registerListener(eventosSensor, sensor, (2000*1000));
    }

    private void detenerSensor() {
        administradorDeSensor.unregisterListener(eventosSensor);
    }

    @Override
    protected void onPause() {
        detenerSensor();
        super.onPause();
    }

    @Override
    protected void onResume() {
        iniciarSensor();
        super.onResume();
    }
}