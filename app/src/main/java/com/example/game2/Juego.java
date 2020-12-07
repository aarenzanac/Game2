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
        public int posX,posY,radio,posMonedaX,posMonedaY, posMonedaFalsaX, posMonedaFalsaY;
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


        boolean valorMusica = true;

        //Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/fuente.ttf");
        public Juego(Context context) {
            super(context);
        }


        public Juego(Context context, AttributeSet attrs) {
            super(context, attrs);
            monedaBuena = MediaPlayer.create(context,R.raw.coin);
            monedaMala = MediaPlayer.create(context,R.raw.roto);

            if(valorMusica){
                gameloop = MediaPlayer.create(context,R.raw.musica);
                gameloop.start();
            }else{
                gameloop.stop();
            }

            //mantiene el loop del soundtrack
            gameloop.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    gameloop.start();
                }
            });
        }

        //Sección que capta los eventos del usuario
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
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
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
                monedaMala.start();
                puntuacion -= 5;
                posMonedaFalsaY=50;
                posMonedaFalsaX= random.nextInt(ancho);

            }
            // Calculo intersección
            if (RectF.intersects(rectCesta,rectMoneda)) {
                monedaBuena.start();
                puntuacion += 2;
                posMonedaY=50;
                posMonedaX= random.nextInt(ancho);

            }
            if (puntuacion < 0){
                gameloop.stop();
                gameloop.release();
                monedaMala.release();
                monedaBuena.release();
                Toast toast = Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT);
                toast.show();
                gameloop = MediaPlayer.create(getContext(),R.raw.dun);
                gameloop.start();



            }

            canvas.drawText(puntuacion.toString(), 150,150,puntos);
        }
}
