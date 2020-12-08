package com.example.game2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class Juego extends View {
        public int ancho,alto;
        public float escala;
        public int posX,posY,radio,posMonedaX,posMonedaY, posMonedaFalsaX, posMonedaFalsaY, avanceMonedas, contadorMonedasMalasCogidas, contadorMonedasBuenasEscapadas;
        private GestureDetector gestos;
        private RectF rectCesta;
        private RectF rectMoneda;
        private RectF rectMonedaFalsa;
        public Integer puntuacion = 0;
        private Random random = new Random();
        private MediaPlayer gameloop = new MediaPlayer();
        private MediaPlayer monedaBuena = new MediaPlayer();
        private MediaPlayer monedaMala = new MediaPlayer();
        private PantallaOpciones pantallaOpciones = new PantallaOpciones();
        private PantallaJuego pantallaJuego = new PantallaJuego();
        boolean valorMusica;
        boolean valorSonidos;



        //Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fuente.ttf");
        public Juego(Context context) {
            super(context);
        }

        //CONSTRUCTOR DE JUEGO. DEPENDE DE LOS VALORES DE PANTALLA OPICONES, AÑADIMOS MÚSICA Y SONIDO.
        public Juego(Context context, AttributeSet attrs) {
            super(context, attrs);

            gameloop = MediaPlayer.create(context,R.raw.musica);
            monedaBuena = MediaPlayer.create(context,R.raw.coin);
            monedaMala = MediaPlayer.create(context,R.raw.roto);

            //EJECUTA LA CANCIÓN EN LOOP SIN FINAL
            gameloop.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    gameloop.start();
                }
            });
        }

        //FUNCIÓN PARA DETECTAR LOS MOVIMIENTOS DEL USUARIO. PULSAR, SOLTAR Y ARRASTRAR.
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        // you may need the x/y location
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    //Elimino el movimiento vertical
                    //posY=(int)event.getY();
                    posX=(int)event.getX();
                    radio=50;
                    // invalidate llama al onDraw y vuelve a pintar la bola
                    this.invalidate();
            }
            return true;
        }
        public Juego(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        //PINTAMOS TODOS LOS ELEMENTOS DEL JUEGO
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if(valorMusica){
                gameloop.start();

            }

            //Definimos los objetos a pintar
            Paint fondo = new Paint();
            Paint cesta = new Paint();
            Paint moneda = new Paint();
            Paint puntos = new Paint();

            //Definimos los colores de los objetos a pintar
            //Bitmap res = BitmapFactory.decodeResource(getResources(),R.drawable.piranha);
            //canvas.drawBitmap(res, 0, 0, null);
            fondo.setColor(Color.BLACK);
            //fondo.setStyle(Paint.Style.FILL_AND_STROKE);
            cesta.setColor(Color.YELLOW);
            cesta.setStyle(Paint.Style.FILL_AND_STROKE);
            moneda.setColor(Color.RED);
            moneda.setStyle(Paint.Style.FILL_AND_STROKE);
            puntos.setTextAlign(Paint.Align.RIGHT);
            puntos.setTextSize(100);
            puntos.setColor(Color.WHITE);
            // puntos.setTypeface(typeface);

            //Pinto rectángulo con un ancho y alto de 1000 o de menos si la pantalla es menor.
            canvas.drawRect(new Rect(0,0,(ancho),(alto)),fondo);

            // Pinto la pelota
            rectCesta= new RectF((posX-radio),(posY-radio),(posX+radio),(posY+radio));
            canvas.drawOval(rectCesta,cesta);

            //Pintamos moneda
            if (posMonedaY>alto) {
                posMonedaY=50;
                posMonedaX= random.nextInt(ancho);
            }
            rectMoneda = new RectF((posMonedaX-radio),(posMonedaY-radio),(posMonedaX+radio),(posMonedaY+radio));
            canvas.drawOval(rectMoneda,moneda);

            //Pintamos monnedaFalsa
            Paint monedaFalsa = new Paint();
            monedaFalsa.setColor(Color.GREEN);
            monedaFalsa.setStyle(Paint.Style.FILL_AND_STROKE);
            if (posMonedaFalsaY>alto) {
                posMonedaFalsaY=50;
                posMonedaFalsaX= random.nextInt(ancho);
            }
            rectMonedaFalsa = new RectF((posMonedaFalsaX-radio),(posMonedaFalsaY-radio),(posMonedaFalsaX+radio),
                    (posMonedaFalsaY+radio));
            canvas.drawOval(rectMonedaFalsa,monedaFalsa);

            // Calculo intersección
            if (RectF.intersects(rectCesta,rectMonedaFalsa)) {
                if(valorSonidos){
                    monedaMala.start();
                }
                puntuacion -= 5;
                posMonedaFalsaY=50;
                posMonedaFalsaX= random.nextInt(ancho);
                //CONTAMOS LAS MONEDAS MALAS COGIDAS PARA FINALIZAR EL JUEGO AL LLEGAR A 3
                contadorMonedasMalasCogidas += 1;
            }

            // Calculo intersección
            if (RectF.intersects(rectCesta,rectMoneda)) {
                if(valorSonidos){
                    monedaBuena.start();
                }
                puntuacion += 2;
                posMonedaY=60;
                posMonedaX= random.nextInt(ancho);
            }else{
                //CONTAMOS LAS MONEDAS BUENAS ESCAPADAS PARA AL LLEGAR A 3 FINALIZAR EL JUEGO
                if(posMonedaY - radio == 0){
                    contadorMonedasBuenasEscapadas += 1;
                }
            }


            //SI LA PUNTUACIÓN <= 0 DETENGO LA MÚSICA Y EFECTOS, MUESTRO UN MENSAJE DE GAME OVER Y PONGO EFECTO SONIDO FINAL
            if (puntuacion < 0 || contadorMonedasMalasCogidas == 3 || contadorMonedasBuenasEscapadas == 4){
                gameloop.stop();
                gameloop.release();
                monedaMala.release();
                monedaBuena.release();
                Toast toast = Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT);
                toast.show();
                if(valorSonidos){
                    gameloop = MediaPlayer.create(getContext(),R.raw.dun);
                    gameloop.start();
                }

            }

            canvas.drawText(puntuacion.toString(), 150,150,puntos);
        }
}
