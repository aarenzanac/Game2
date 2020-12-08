package com.example.game2;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        Button botonComenzar = findViewById(R.id.buttonComenzar);
        Button botonOpciones = findViewById(R.id.buttonOpciones);
        Button botonSalir = findViewById(R.id.buttonSalir);
        Button botonInstrucciones = findViewById(R.id.buttonInstrucciones);



        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego = new Intent(v.getContext(), PantallaJuego.class);
                startActivity(intentJuego);

            }
        });

        botonOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego = new Intent(v.getContext(), PantallaOpciones.class);
                startActivity(intentJuego);

            }
        });

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDestroy();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        botonInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("INSTRUCCIONES");
                builder.setMessage("EL jueco consiste en atrapar las pelotas verdes (suman 2 puntos) evitando coger las pelotas rojas (restan 5 puntos)." +
                        "Dispone de 3 vidas de la siguiente forma:" +
                        "- No puede dejar escapar mas de 3 pelotas verdes. Si deja escapar 3, finaliza la partida." +
                        "- No puede atrapar mas de 3 pelotas rojas. Si atrapa 3, finaliza la partida." +
                        "- La puntuación no puede ser menor de 0 con lo que si recoge varias pelotas rojas y su puntuación baja de 0, funaliza la partida." +
                        "¡¡¡Que se divierta!!!");
                builder.setPositiveButton("Aceptar", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    };


}