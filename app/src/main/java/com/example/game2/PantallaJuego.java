package com.example.game2;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;

import java.util.Timer;
import java.util.TimerTask;

public class PantallaJuego extends AppCompatActivity {
    private Juego juego;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //OBTENEMOS LOS VALORES DE MUSICA Y SONIDO DE LA PANTALLA OPCIONES
        boolean valorMusica = getIntent().getBooleanExtra("musica", true);
        boolean valorSonidos = getIntent().getBooleanExtra("sonidos", true);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pantalla_juego);

        juego = (Juego) findViewById(R.id.Pantalla);

        ViewTreeObserver obs = juego.getViewTreeObserver();
        obs.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Sólo se puede averiguar el ancho y alto una vez ya se ha pintado el layout. Por eso se calcula en este listener
                //INICIALIZO LAS VARIABLES DEL JUEGO
                juego.ancho = juego.getWidth();
                juego.alto = juego.getHeight();
                juego.posX=juego.ancho/2;
                juego.posY=juego.alto-50;
                juego.radio=50;
                juego.posMonedaY=50;
                juego.posMonedaFalsaX=200;
                juego.avanceMonedas = 10;
                juego.valorMusica = valorMusica;
                juego.valorSonidos = valorSonidos;
                juego.contadorMonedasMalasCogidas = 0;
                juego.contadorMonedasBuenasEscapadas = 0;
            }
        });
        //TIMER PARA HACER EL AVANCE DE LAS MONEDAS. CADA 15 MILISEGUNDOS AVANZA. SI NUESTRA PUNTUACIÓN ES MENOR O IGUAL A 0, COGEMOS 3 MALAS O DEJAMOS ESCAPAR 3 BUENAS, GAME OVER
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //Cada x segundos movemos la moneda 10dp
                        juego.posMonedaY += juego.avanceMonedas;
                        juego.posMonedaFalsaY += juego.avanceMonedas;

                        //refresca la pantalla y llama al draw
                        juego.invalidate();
                        if (juego.puntuacion < 0 || juego.contadorMonedasMalasCogidas == 3 || juego.contadorMonedasBuenasEscapadas == 4){

                            timer.cancel();
                            finish();
                        }
                    }
                });
            }
        }, 0, 15);

        //TIMER QUE AUMENTA LA DIFICULTAD CADA 15 SEGUNDOS AUMENTANDO LA VELOCIDAD DE AVANCE DE LAS MONEDAS
        Timer timerDificultad = new Timer();
        timerDificultad.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        juego.avanceMonedas += 5;

                    }
                });
            }
        }, 0, 15000);
    }
}