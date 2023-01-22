package com.jherrera.sensorespruebadiagnostica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button buttonProximidad;
    private Button buttonLuminosidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInitComponents();
        addEventsButtons();
    }

    private void addEventsButtons() {
        //Inicia ProximidadActivity
        buttonProximidad.setOnClickListener(view -> {
            Intent intent = new Intent(this, ProximidadActivity.class);
            startActivity(intent);
        });

        //Inicia LuminosidadActivity
        buttonLuminosidad.setOnClickListener(view -> {
            Intent intent = new Intent(this, LuminosidadActivity.class);
            startActivity(intent);
        });
    }

    //inicializar elemeentos de la ui
    private void setInitComponents() {
        buttonProximidad = findViewById(R.id.buttonProximidad);
        buttonLuminosidad = findViewById(R.id.buttonLuminosidad);
    }
}