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
        public RectF rectVidaMonedaBuena1, rectVidaMonedaBuena2, rectVidaMonedaBuena3;
        public RectF rectVidaMonedaMala1, rectVidaMonedaMala2, rectVidaMonedaMala3;
        public Integer puntuacion = 0;
        private Random random = new Random();
        private MediaPlayer gameloop = new MediaPlayer();
        private MediaPlayer monedaBuena = new MediaPlayer();
        private MediaPlayer monedaMala = new MediaPlayer();
        private PantallaOpciones pantallaOpciones = new PantallaOpciones();
        private PantallaJuego pantallaJuego = new PantallaJuego();
        boolean valorMusica;
        boolean valorSonidos;
        Paint fondo = new Paint();
        Paint cesta = new Paint();
        Paint moneda = new Paint();
        Paint puntos = new Paint();
        Paint tituloPuntos = new Paint();
        Paint tituloMonedasBuenas = new Paint();
        Paint tituloMonedasMalas = new Paint();
        Paint vidaMonedaBuena1 = new Paint();
        Paint vidaMonedaMala1 = new Paint();
        Paint vidaMonedaBuena2 = new Paint();
        Paint vidaMonedaMala2 = new Paint();
        Paint vidaMonedaBuena3 = new Paint();
        Paint vidaMonedaMala3 = new Paint();
        int colorMonedaBuena1 = Color.GREEN;
        int colorMonedaBuena2 = Color.GREEN;
        int colorMonedaBuena3 = Color.GREEN;
        int colorMonedaMala1 = Color.RED;
        int colorMonedaMala2 = Color.RED;
        int colorMonedaMala3 = Color.RED;




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


            //Definimos los colores de los objetos a pintar
            //Bitmap res = BitmapFactory.decodeResource(getResources(),R.drawable.piranha);
            //canvas.drawBitmap(res, 0, 0, null);
            fondo.setColor(Color.BLACK);
            //fondo.setStyle(Paint.Style.FILL_AND_STROKE);
            cesta.setColor(Color.YELLOW);
            cesta.setStyle(Paint.Style.FILL_AND_STROKE);
            moneda.setColor(Color.GREEN);
            moneda.setStyle(Paint.Style.FILL_AND_STROKE);
            tituloPuntos.setTextSize(45);
            tituloPuntos.setTextAlign(Paint.Align.RIGHT);
            tituloPuntos.setColor(Color.WHITE);
            tituloMonedasBuenas.setTextSize(45);
            tituloMonedasBuenas.setTextAlign(Paint.Align.RIGHT);
            tituloMonedasBuenas.setColor(Color.WHITE);
            tituloMonedasMalas.setTextSize(45);
            tituloMonedasMalas.setTextAlign(Paint.Align.RIGHT);
            tituloMonedasMalas.setColor(Color.WHITE);
            puntos.setTextAlign(Paint.Align.RIGHT);
            puntos.setTextSize(100);
            puntos.setColor(Color.WHITE);
            vidaMonedaBuena1.setColor(colorMonedaBuena1);
            vidaMonedaMala1.setColor(colorMonedaMala1);
            vidaMonedaBuena2.setColor(colorMonedaBuena2);
            vidaMonedaMala2.setColor(colorMonedaMala2);
            vidaMonedaBuena3.setColor(colorMonedaBuena3);
            vidaMonedaMala3.setColor(colorMonedaMala3);
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
            monedaFalsa.setColor(Color.RED);
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
                if(contadorMonedasMalasCogidas == 1){
                    colorMonedaMala1 = Color.GRAY;
                }else if(contadorMonedasMalasCogidas == 2){
                    colorMonedaMala2 = Color.GRAY;
                }
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
                    if(contadorMonedasBuenasEscapadas == 2){
                        colorMonedaBuena1 = Color.GRAY;
                    }else if(contadorMonedasBuenasEscapadas == 3){
                        colorMonedaBuena2 = Color.GRAY;
                    }
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
            canvas.drawText("PUNTOS", 200,50,tituloPuntos);
            canvas.drawText("MONEDAS BUENAS", 680,50,tituloMonedasBuenas);
            canvas.drawText("MODEDAS MALAS", 1150,50,tituloMonedasMalas);



            //CONTADOR DE MONEDAS BUENAS ESCAPADAS
            rectVidaMonedaBuena1= new RectF(350, 150, 300 , 100);
            rectVidaMonedaBuena2= new RectF(450, 150, 400 , 100);
            rectVidaMonedaBuena3= new RectF(550, 150, 500 , 100);
            canvas.drawOval(rectVidaMonedaBuena1, vidaMonedaBuena1);
            canvas.drawOval(rectVidaMonedaBuena2, vidaMonedaBuena2);
            canvas.drawOval(rectVidaMonedaBuena3, vidaMonedaBuena3);

            //CONTADOR DE MONEDAS MALAS COGIDAS
            rectVidaMonedaMala1= new RectF(850, 150, 800 , 100);
            rectVidaMonedaMala2= new RectF(950, 150, 900 , 100);
            rectVidaMonedaMala3= new RectF(1050, 150, 1000 , 100);
            canvas.drawOval(rectVidaMonedaMala1, vidaMonedaMala1);
            canvas.drawOval(rectVidaMonedaMala2, vidaMonedaMala2);
            canvas.drawOval(rectVidaMonedaMala3, vidaMonedaMala3);



        }
}
