package com.jherrera.sensorespruebadiagnostica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LuminosidadActivity extends AppCompatActivity {

    private TextView textViewResultado;
    private Button buttonEnviar;
    private Sensor sensor;
    private SensorManager administradorDeSensor;
    private SensorEventListener eventosSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luminosidad);

        setInitComponents();
        addActionButtons();
    }

    private void setInitComponents() {
        textViewResultado = findViewById(R.id.textViewResultado);
        buttonEnviar = findViewById(R.id.buttonEnviar);
        administradorDeSensor = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor =  administradorDeSensor.getDefaultSensor(Sensor.TYPE_LIGHT);

        //verficando estado del sensor
        if (sensor == null) {
            Toast.makeText(this, "Su dispositivo no tiene el sensor", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Sensor de proximidad detectado", Toast.LENGTH_LONG).show();
        }

        eventosSensor = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                double valueSensor = sensorEvent.values[0];
                textViewResultado.setText("VALOR: "+valueSensor+ " LUX");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        iniciarSensor();
    }

    private void addActionButtons() {
        buttonEnviar.setOnClickListener(view -> {
            sendSensorInfo();
        });
    }

    private void sendSensorInfo() {
        String sensorValue = textViewResultado.getText().toString();
        String telefono = "+50375121671";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String uri = "whatsapp://send?phone=" + telefono + "&text=" + sensorValue;
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    private void iniciarSensor() {
        administradorDeSensor.registerListener(eventosSensor, sensor, administradorDeSensor.SENSOR_DELAY_NORMAL);
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