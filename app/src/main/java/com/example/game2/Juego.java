package com.example.game2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        //DECLARACIÓN DE TODAS LAS VARIABLES
        public int ancho,alto;
        public float escala;
        public int posX,posY,radioAspirina, radioPacman,posMonedaX,posMonedaY, posMonedaFalsaX, posMonedaFalsaY, avanceMonedas, contadorMonedasMalasCogidas, contadorMonedasBuenasEscapadas;
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

        //BITMAPS PARA ESTABLECER IMAGENES PARA EL FONDO Y PARA SUPERPONER A LOS CÍRCULOS.
        Bitmap fondoPantalla;
        Bitmap pacman =  BitmapFactory.decodeResource(getResources(),R.drawable.pacman);
        Bitmap monedaFalsaImagen =  BitmapFactory.decodeResource(getResources(),R.drawable.pastilla_mala);
        Bitmap pastillaBuenaImagen =  BitmapFactory.decodeResource(getResources(),R.drawable.aspirina);
        Bitmap monedaFalsaCogidaImagen = BitmapFactory.decodeResource(getResources(),R.drawable.pastilla_mala_cogida);

        //PAINTS PARA LOS CIRCULOS CESTA, CIRCULO BUENO, CIRCULO MALO, PUNTUACION Y TEXTOS DE TITULO DE VIDAS
        //Paint fondo = new Paint();//ELIMINO ESTE PAINT PUESTO QUE HEMOS PUESTO UNA IMAGEN BITMAP DE FONDO DE PANTALLA
        Paint cesta = new Paint();
        Paint moneda = new Paint();
        Paint monedaFalsa = new Paint();
        Paint puntos = new Paint();
        Paint tituloPuntos = new Paint();
        Paint tituloMonedasBuenas = new Paint();
        Paint tituloMonedasMalas = new Paint();

        //PAINTS PARA PINTAR LOS CIRCULOS DE LAS VIDAS. MONEDAS MALAS COGIDAS Y BUENAS ESCAPADAS
        Paint vidaMonedaBuena1 = new Paint();
        Paint vidaMonedaMala1 = new Paint();
        Paint vidaMonedaBuena2 = new Paint();
        Paint vidaMonedaMala2 = new Paint();
        Paint vidaMonedaBuena3 = new Paint();
        Paint vidaMonedaMala3 = new Paint();

        //COLORES DE LOS CÍRCULOS DE LAS VIDAS.
        int colorMonedaBuena1 = Color.GREEN;
        int colorMonedaBuena2 = Color.GREEN;
        int colorMonedaBuena3 = Color.GREEN;
        int colorMonedaMala1 = Color.RED;
        int colorMonedaMala2 = Color.RED;
        int colorMonedaMala3 = Color.RED;
        Bitmap vidaMonedaFalsaImagen1 = monedaFalsaImagen;
        Bitmap vidaMonedaFalsaImagen2 = monedaFalsaImagen;
        Bitmap vidaMonedaFalsaImagen3 = monedaFalsaImagen;


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
                    radioPacman=70;
                    // invalidate llama al onDraw y vuelve a pintar el circulo
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
            //fondo.setColor(Color.BLACK); //ELIMINO EL COLOR PUESTO QUE HEMOS PUESTO IMAGEN DE FONDO.
            //fondo.setStyle(Paint.Style.FILL_AND_STROKE); //ELIMINO EL ESTILO PUESTO QUE HEMOS PUESTO FONDO.

            //ESTABLEZCO EL BITMAP CON LA IMAGEN DE FONDO DE PANTALLA
            fondoPantalla = BitmapFactory.decodeResource(getResources(),R.drawable.fondo_pantalla_juego);

            //cesta.setColor(Color.YELLOW); //ELIMINO EL COLOR PARA QUE NO SE VEA EL COLOR AMARILLO
            cesta.setStyle(Paint.Style.FILL_AND_STROKE);
            moneda.setColor(Color.GREEN);
            moneda.setStyle(Paint.Style.FILL_AND_STROKE);
            tituloPuntos.setTextSize(45);
            tituloPuntos.setTextAlign(Paint.Align.RIGHT);
            tituloPuntos.setColor(Color.BLACK);
            tituloMonedasBuenas.setTextSize(45);
            tituloMonedasBuenas.setTextAlign(Paint.Align.RIGHT);
            tituloMonedasBuenas.setColor(Color.BLACK);
            tituloMonedasMalas.setTextSize(45);
            tituloMonedasMalas.setTextAlign(Paint.Align.RIGHT);
            tituloMonedasMalas.setColor(Color.BLACK);
            puntos.setTextAlign(Paint.Align.RIGHT);
            puntos.setTextSize(100);
            puntos.setColor(Color.BLACK);
            vidaMonedaBuena1.setColor(colorMonedaBuena1);
            vidaMonedaMala1.setColor(colorMonedaMala1);
            vidaMonedaFalsaImagen1 = monedaFalsaImagen;
            vidaMonedaBuena2.setColor(colorMonedaBuena2);
            vidaMonedaMala2.setColor(colorMonedaMala2);
            vidaMonedaFalsaImagen2 = monedaFalsaImagen;
            vidaMonedaBuena3.setColor(colorMonedaBuena3);
            vidaMonedaMala3.setColor(colorMonedaMala3);
            vidaMonedaFalsaImagen3 = monedaFalsaImagen;


            //Pinto rectángulo con un ancho y alto de 1000 o de menos si la pantalla es menor.
            //canvas.drawRect(new Rect(0,0,(ancho),(alto)),fondo); //ELIMINO ESTE DRAWRECT PUESTO QUE PINTAREMOS CON DRAWBITMAP EL FONDO PANTALLA
            canvas.drawBitmap(fondoPantalla, null, new Rect(0,0,(ancho),(alto)), null);

            // PINTO LA CESTA
            rectCesta= new RectF((posX-radioPacman),(posY-radioPacman),(posX+radioPacman),(posY+radioPacman));
            canvas.drawOval(rectCesta,cesta);
            canvas.drawBitmap(pacman, null, rectCesta, null);

            //PINTAMOS LA PASTILLA BUENA
            if (posMonedaY>alto) {
                posMonedaY=50;
                posMonedaX= random.nextInt(ancho);
            }
            rectMoneda = new RectF((posMonedaX-radioAspirina),(posMonedaY-radioAspirina),(posMonedaX+radioAspirina),(posMonedaY+radioAspirina));
            canvas.drawOval(rectMoneda,moneda);
            canvas.drawBitmap(pastillaBuenaImagen, null, rectMoneda, null);

            //PINTAMOS LA PASTILLA MALA

            //monedaFalsa.setColor(Color.RED); //ELIMINO ESTE COLOR PUESTO QUE HEMOS SUPERPUESTO IMAGEN DE PASTILLA MALA
            //monedaFalsa.setStyle(Paint.Style.FILL_AND_STROKE); //ELIMINO ESTE ESTILO PUESTO QUE HEMOS SUPERPUESTO IMAGEN DE PASTILLA MALA
            if (posMonedaFalsaY>alto) {
                posMonedaFalsaY=50;
                posMonedaFalsaX= random.nextInt(ancho);
            }
            rectMonedaFalsa = new RectF((posMonedaFalsaX-radioAspirina),(posMonedaFalsaY-radioAspirina),(posMonedaFalsaX+radioAspirina),
                    (posMonedaFalsaY+radioAspirina));
            canvas.drawOval(rectMonedaFalsa,monedaFalsa);

            canvas.drawBitmap(monedaFalsaImagen, null, rectMonedaFalsa, null);

            // Calculo intersección
            if (RectF.intersects(rectCesta,rectMonedaFalsa)) {
                if(valorSonidos){
                    monedaMala.start();
                }
                puntuacion -= 3;
                posMonedaFalsaY=50;
                posMonedaFalsaX= random.nextInt(ancho);

                //CONTAMOS LAS PASTILLAS MALAS COGIDAS PARA FINALIZAR EL JUEGO AL LLEGAR A 3
                contadorMonedasMalasCogidas += 1;
                if(contadorMonedasMalasCogidas == 1){
                    colorMonedaMala1 = Color.GRAY;
                    vidaMonedaFalsaImagen1= monedaFalsaCogidaImagen;

                }else if(contadorMonedasMalasCogidas == 2){
                    colorMonedaMala2 = Color.GRAY;
                    vidaMonedaFalsaImagen2= monedaFalsaCogidaImagen;
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

                //CONTAMOS LAS PASTILLAS BUENAS ESCAPADAS PARA AL LLEGAR A 3 FINALIZAR EL JUEGO
                if(posMonedaY - radioAspirina == 0){
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



            //POSICIONAMIENTO DE CONTADOR DE PASTILLAS BUENAS ESCAPADAS
            rectVidaMonedaBuena1= new RectF(350, 150, 300 , 100);
            rectVidaMonedaBuena2= new RectF(450, 150, 400 , 100);
            rectVidaMonedaBuena3= new RectF(550, 150, 500 , 100);
            canvas.drawOval(rectVidaMonedaBuena1, vidaMonedaBuena1);
            canvas.drawOval(rectVidaMonedaBuena2, vidaMonedaBuena2);
            canvas.drawOval(rectVidaMonedaBuena3, vidaMonedaBuena3);


            //POSICIONAMIENTO DE CONTADOR DE PASTILLAS MALAS COGIDAS
            rectVidaMonedaMala1= new RectF(850, 150, 800 , 100);
            rectVidaMonedaMala2= new RectF(950, 150, 900 , 100);
            rectVidaMonedaMala3= new RectF(1050, 150, 1000 , 100);
            canvas.drawOval(rectVidaMonedaMala1, vidaMonedaMala1);
            canvas.drawOval(rectVidaMonedaMala2, vidaMonedaMala2);
            canvas.drawOval(rectVidaMonedaMala3, vidaMonedaMala3);
            canvas.drawBitmap(vidaMonedaFalsaImagen1, null, rectVidaMonedaMala1, null);
            canvas.drawBitmap(vidaMonedaFalsaImagen2, null, rectVidaMonedaMala2, null);
            canvas.drawBitmap(vidaMonedaFalsaImagen3, null, rectVidaMonedaMala3, null);




        }
}
