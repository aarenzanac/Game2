package com.example.game2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;

public class PantallaOpciones extends AppCompatActivity {

    Button botonComenzar;
    Button botonVolver;
    Switch switchMusica;
    Switch switchSonidos;
    ArrayList<Boolean> arrayIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_opciones);
        getSupportActionBar().hide();

        botonComenzar = findViewById(R.id.buttonComenzar);
        botonVolver = findViewById(R.id.buttonVolver);
        switchMusica = findViewById(R.id.switchMusica);
        switchSonidos = findViewById(R.id.switchSonidos);
        arrayIntent = new ArrayList<Boolean>();


        //BOTÓN PARA EMPEZAR LA PARTIDA CON LAS OPCIONES DESEADAS
        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego = new Intent(v.getContext(), PantallaJuego.class);
                boolean valorMusica = switchMusica.isChecked();
                boolean valorSonidos = switchSonidos.isChecked();
                arrayIntent.add(valorMusica);
                arrayIntent.add(valorSonidos);

                Bundle parametros = new Bundle();
                parametros.putBoolean("musica", valorMusica);
                parametros.putBoolean("sonidos", valorSonidos);

                intentJuego.putExtras(parametros);

                startActivity(intentJuego);

            }
        });

        //BOTON PARA VOLVER AL MENÚ PRINCIPAL
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}