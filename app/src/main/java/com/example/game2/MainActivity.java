package com.example.game2;


import android.content.Intent;
import android.os.Bundle;
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
    };


}