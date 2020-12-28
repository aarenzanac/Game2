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
        public int posX,posY,radioAspirina, radioPacman, posPastillaX, posPastillaY, posPastillaMalaX, posPastillaMalaY, avancePastillas, contadorPastillasMalasCogidas, contadorPastillasBuenasEscapadas;
        private GestureDetector gestos;
        private RectF rectPacman;
        private RectF rectMoneda;
        private RectF rectPastillaFalsa;
        public RectF rectVidaPastillaBuena1, rectVidaPastillaBuena2, rectVidaPastillaBuena3;
        public RectF rectVidaPastillaMala1, rectVidaPastillaMala2, rectVidaPastillaMala3;
        public Integer puntuacion = 0;
        private Random random = new Random();
        public MediaPlayer gameloopSound = new MediaPlayer();
        private MediaPlayer pastillaBuenaSound = new MediaPlayer();
        private MediaPlayer pastillaMalaSound = new MediaPlayer();
        private PantallaOpciones pantallaOpciones = new PantallaOpciones();
        private PantallaJuego pantallaJuego = new PantallaJuego();
        boolean valorMusica;
        boolean valorSonidos;

        //BITMAPS PARA ESTABLECER IMAGENES PARA EL FONDO Y PARA SUPERPONER A LOS CÍRCULOS.
        Bitmap fondoPantalla;
        Bitmap pacman =  BitmapFactory.decodeResource(getResources(),R.drawable.pacman);
        Bitmap pastillaMalaImagen =  BitmapFactory.decodeResource(getResources(),R.drawable.pastilla_mala);
        Bitmap pastillaBuenaImagen =  BitmapFactory.decodeResource(getResources(),R.drawable.aspirina);

        //PAINTS PARA LOS CIRCULOS PACMAN, CIRCULO PASTILLA BUENA, CIRCULO PASTILLA MALA, PUNTUACION Y TEXTOS DE TITULO DE VIDAS
        //Paint fondo = new Paint();//ELIMINO ESTE PAINT PUESTO QUE HEMOS PUESTO UNA IMAGEN BITMAP DE FONDO DE PANTALLA
        Paint circuloPacman = new Paint();
        Paint pastilla = new Paint();
        Paint pastillaMala = new Paint();
        Paint puntos = new Paint();
        Paint tituloPuntos = new Paint();
        Paint tituloPastillasBuenas = new Paint();
        Paint tituloPastillasMalas = new Paint();

        //PAINTS PARA PINTAR LOS CIRCULOS DE LAS VIDAS. MONEDAS MALAS COGIDAS Y BUENAS ESCAPADAS
        Paint vidaPastillaBuena1 = new Paint();
        Paint vidaPastillaMala1 = new Paint();
        Paint vidaPastillaBuena2 = new Paint();
        Paint vidaPastillaMala2 = new Paint();
        Paint vidaPastillaBuena3 = new Paint();
        Paint vidaPastillaMala3 = new Paint();

        //COLORES DE LOS CÍRCULOS DE LAS VIDAS.
        int colorPastillaBuena1 = Color.GREEN;
        int colorPastillaBuena2 = Color.GREEN;
        int colorPastillaBuena3 = Color.GREEN;
        int colorPastillaMala1 = Color.RED;
        int colorPastillaMala2 = Color.RED;
        int colorPastillaMala3 = Color.RED;


        public Juego(Context context) {
            super(context);
        }

        //CONSTRUCTOR DE JUEGO. DEPENDE DE LOS VALORES DE PANTALLA OPICONES, AÑADIMOS MÚSICA Y SONIDO.
        public Juego(Context context, AttributeSet attrs) {
            super(context, attrs);

            gameloopSound = MediaPlayer.create(context,R.raw.musica);
            pastillaBuenaSound = MediaPlayer.create(context,R.raw.coin);
            pastillaMalaSound = MediaPlayer.create(context,R.raw.roto);

            //EJECUTA LA CANCIÓN EN LOOP SIN FINAL
            gameloopSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    gameloopSound.start();
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
                gameloopSound.start();

            }


            //Definimos los colores de los objetos a pintar
            //fondo.setColor(Color.BLACK); //ELIMINO EL COLOR PUESTO QUE HEMOS PUESTO IMAGEN DE FONDO.
            //fondo.setStyle(Paint.Style.FILL_AND_STROKE); //ELIMINO EL ESTILO PUESTO QUE HEMOS PUESTO FONDO.

            //ESTABLEZCO EL BITMAP CON LA IMAGEN DE FONDO DE PANTALLA
            fondoPantalla = BitmapFactory.decodeResource(getResources(),R.drawable.fondo_pantalla_juego);

            circuloPacman.setColor(Color.TRANSPARENT);
            circuloPacman.setAntiAlias(true);
            circuloPacman.setFilterBitmap(true);
            circuloPacman.setDither(true);
            //circuloPacman.setStyle(Paint.Style.FILL_AND_STROKE);
            //pastilla.setColor(Color.GREEN);
            //pastilla.setStyle(Paint.Style.FILL_AND_STROKE);
            tituloPuntos.setTextSize(45);
            tituloPuntos.setTextAlign(Paint.Align.RIGHT);
            tituloPuntos.setColor(Color.BLACK);
            tituloPastillasBuenas.setTextSize(40);
            tituloPastillasBuenas.setTextAlign(Paint.Align.RIGHT);
            tituloPastillasBuenas.setColor(Color.BLACK);
            tituloPastillasMalas.setTextSize(40);
            tituloPastillasMalas.setTextAlign(Paint.Align.RIGHT);
            tituloPastillasMalas.setColor(Color.BLACK);
            puntos.setTextAlign(Paint.Align.RIGHT);
            puntos.setTextSize(100);
            puntos.setColor(Color.BLACK);
            vidaPastillaBuena1.setColor(colorPastillaBuena1);
            vidaPastillaMala1.setColor(colorPastillaMala1);
            vidaPastillaBuena2.setColor(colorPastillaBuena2);
            vidaPastillaMala2.setColor(colorPastillaMala2);
            vidaPastillaBuena3.setColor(colorPastillaBuena3);
            vidaPastillaMala3.setColor(colorPastillaMala3);

            //Pinto rectángulo con un ancho y alto de 1000 o de menos si la pantalla es menor.
            //canvas.drawRect(new Rect(0,0,(ancho),(alto)),fondo); //ELIMINO ESTE DRAWRECT PUESTO QUE PINTAREMOS CON DRAWBITMAP EL FONDO PANTALLA
            canvas.drawBitmap(fondoPantalla, null, new Rect(0,0,(ancho),(alto)), null);

            // PINTO A PACMAN
            rectPacman= new RectF((posX-radioPacman),(posY-radioPacman),(posX+radioPacman),(posY+radioPacman));
            canvas.drawOval(rectPacman,circuloPacman);
            canvas.drawBitmap(pacman, null, rectPacman, null);

            //PINTAMOS LA PASTILLA BUENA
            pastilla.setAntiAlias(true);
            pastilla.setFilterBitmap(true);
            pastilla.setDither(true);

            if (posPastillaY>alto) {
                posPastillaY=50;
                posPastillaX= random.nextInt(ancho);
            }
            rectMoneda = new RectF((posPastillaX-radioAspirina),(posPastillaY-radioAspirina),(posPastillaX+radioAspirina),(posPastillaY+radioAspirina));
            canvas.drawOval(rectMoneda,pastilla);
            canvas.drawBitmap(pastillaBuenaImagen, null, rectMoneda, null);

            //PINTAMOS LA PASTILLA MALA

            //pastillaMala.setColor(Color.RED); //ELIMINO ESTE COLOR PUESTO QUE HEMOS SUPERPUESTO IMAGEN DE PASTILLA MALA
            //pastillaMala.setStyle(Paint.Style.FILL_AND_STROKE); //ELIMINO ESTE ESTILO PUESTO QUE HEMOS SUPERPUESTO IMAGEN DE PASTILLA MALA
            pastillaMala.setAntiAlias(true);
            pastillaMala.setFilterBitmap(true);
            pastillaMala.setDither(true);
            if (posPastillaMalaY>alto) {
                posPastillaMalaY=50;
                posPastillaMalaX= random.nextInt(ancho);
            }
            rectPastillaFalsa = new RectF((posPastillaMalaX-radioAspirina),(posPastillaMalaY-radioAspirina),(posPastillaMalaX+radioAspirina),
                    (posPastillaMalaY+radioAspirina));
            canvas.drawOval(rectPastillaFalsa,pastillaMala);

            canvas.drawBitmap(pastillaMalaImagen, null, rectPastillaFalsa, null);

            // Calculo intersección
            if (RectF.intersects(rectPacman,rectPastillaFalsa)) {
                if(valorSonidos){
                    pastillaMalaSound.start();
                }
                puntuacion -= 3;
                posPastillaMalaY=50;
                posPastillaMalaX = random.nextInt(ancho);

                //CONTAMOS LAS PASTILLAS MALAS COGIDAS PARA FINALIZAR EL JUEGO AL LLEGAR A 3
                contadorPastillasMalasCogidas += 1;
                if(contadorPastillasMalasCogidas == 1){
                    colorPastillaMala1 = Color.GRAY;

                }else if(contadorPastillasMalasCogidas == 2){
                    colorPastillaMala2 = Color.GRAY;

                }
            }

            // Calculo intersección
            if (RectF.intersects(rectPacman,rectMoneda)) {
                if(valorSonidos){
                    pastillaBuenaSound.start();
                }
                puntuacion += 2;
                posPastillaY=60;
                posPastillaX= random.nextInt(ancho);
            }else{

                //CONTAMOS LAS PASTILLAS BUENAS ESCAPADAS PARA AL LLEGAR A 3 FINALIZAR EL JUEGO
                if(posPastillaY - radioAspirina == 0){
                    contadorPastillasBuenasEscapadas += 1;
                    if(contadorPastillasBuenasEscapadas == 2){
                        colorPastillaBuena1 = Color.GRAY;
                    }else if(contadorPastillasBuenasEscapadas == 3){
                        colorPastillaBuena2 = Color.GRAY;
                    }
                }
            }


            //SI LA PUNTUACIÓN <= 0 DETENGO LA MÚSICA Y EFECTOS, MUESTRO UN MENSAJE DE GAME OVER Y PONGO EFECTO SONIDO FINAL
            if (puntuacion < 0 || contadorPastillasMalasCogidas == 3 || contadorPastillasBuenasEscapadas == 4){
                gameloopSound.stop();
                gameloopSound.release();
                pastillaMalaSound.release();
                pastillaBuenaSound.release();
                Toast toast = Toast.makeText(getContext(), "GAME OVER", Toast.LENGTH_SHORT);
                toast.show();
                if(valorSonidos){
                    gameloopSound = MediaPlayer.create(getContext(),R.raw.dun);
                    gameloopSound.start();
                }

            }

            canvas.drawText(puntuacion.toString(), 150,150,puntos);
            canvas.drawText("PUNTOS", 200,50,tituloPuntos);
            canvas.drawText("PASTILLAS BUENAS", 650,50,tituloPastillasBuenas);
            canvas.drawText("PASTILLAS MALAS", 1080,50,tituloPastillasMalas);



            //POSICIONAMIENTO DE CONTADOR DE PASTILLAS BUENAS ESCAPADAS
            rectVidaPastillaBuena1= new RectF(350, 150, 300 , 100);
            rectVidaPastillaBuena2= new RectF(450, 150, 400 , 100);
            rectVidaPastillaBuena3= new RectF(550, 150, 500 , 100);
            canvas.drawOval(rectVidaPastillaBuena1, vidaPastillaBuena1);
            canvas.drawOval(rectVidaPastillaBuena2, vidaPastillaBuena2);
            canvas.drawOval(rectVidaPastillaBuena3, vidaPastillaBuena3);


            //POSICIONAMIENTO DE CONTADOR DE PASTILLAS MALAS COGIDAS
            rectVidaPastillaMala1= new RectF(800, 150, 750 , 100);
            rectVidaPastillaMala2= new RectF(900, 150, 850 , 100);
            rectVidaPastillaMala3= new RectF(1000, 150, 950 , 100);
            canvas.drawOval(rectVidaPastillaMala1, vidaPastillaMala1);
            canvas.drawOval(rectVidaPastillaMala2, vidaPastillaMala2);
            canvas.drawOval(rectVidaPastillaMala3, vidaPastillaMala3);
        }
}
