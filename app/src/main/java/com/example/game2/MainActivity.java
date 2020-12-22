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


        //ACCIÓN PARA EL BOTON DE COMENZAR LA PARTIDA
        botonComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego = new Intent(v.getContext(), PantallaJuego.class);
                startActivity(intentJuego);

            }
        });
        //ACCIÓN PARA EL BOTÓN DE LA PANTALLA DE OPCIONES
        botonOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentJuego = new Intent(v.getContext(), PantallaOpciones.class);
                startActivity(intentJuego);

            }
        });

        //ACCION PARA EL BOTON SALIR
        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onDestroy();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        //ACCION PARA EL BOTON INSTRUCCIONES.
        botonInstrucciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("INSTRUCCIONES");
                builder.setMessage("El juego consiste en atrapar las aspirinas (suman 2 puntos) evitando coger las pastillas venenosas (restan 3 puntos).\n" +
                        "Dispone de 3 vidas de la siguiente forma:\n" +
                        "- No puede dejar escapar 3 aspirinas. Si deja escapar 3, finaliza la partida.\n" +
                        "- No puede atrapar 3 pastillas venenosas. Si atrapa 3, finaliza la partida.\n" +
                        "- La puntuación no puede ser menor de 0 con lo que si recoge varias pastillas venenosas y su puntuación baja de 0, finaliza la partida.\n" +
                        "La difuciltad del juego irà en aumento, subiendo la velocidad de caida de las pastillas, a medida que la partida va avanzando. \n" +
                        "En la parte superior de la pantalla de juego encontraŕa la puntuación obtenida así como las aspirinas escapadas (en color verde)y las pastillas venenosas atrapadas (en rojo).\n" +
                        "¡¡¡Que se divierta!!!\n");
                builder.setPositiveButton("Aceptar", null);

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    };


}