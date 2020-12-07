package com.example.game2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import java.util.Timer;
import java.util.TimerTask;

public class PantallaJuego extends AppCompatActivity {
    public Juego juego;
    private Handler handler = new Handler();
    Boolean valorMusica = true;
    Boolean valorSonidos =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        valorMusica = getIntent().getBooleanExtra("musica", true);
        valorSonidos = getIntent().getBooleanExtra("sonidos", true);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantalla_juego);


        juego = (Juego) findViewById(R.id.Pantalla);

        ViewTreeObserver obs = juego.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Sólo se puede averiguar el ancho y alto una vez ya se ha pintado el layout. Por eso se calcula en este listener
                juego.ancho = juego.getWidth();
                juego.alto = juego.getHeight();
                juego.posX=juego.ancho/2;
                juego.posY=juego.alto-50;
                juego.radio=50;
                juego.posMonedaY=50;
                juego.posMonedaFalsaX=200;


            }
        });
        //Ejecutamos cada 15 milisegundos
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //Cada x segundos movemos la moneda 10dp
                        juego.posMonedaY+=10;
                        juego.posMonedaFalsaY+=10;
                        //refresca la pantalla y llama al draw
                        juego.invalidate();
                        if (juego.puntuacion < 0){

                            timer.cancel();
                            finish();
                        }
                    }
                });
            }
        }, 0, 15);
    }
}