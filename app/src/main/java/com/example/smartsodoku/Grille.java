package com.example.smartsodoku;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

    public class Grille extends View {

        private int screenWidth;
        private int screenHeight;
        private int n;
        private int tailleCase=0;
        private int valisation = 0;

        private Paint paint1;   // Pour dessiner la grille (lignes noires)
        private Paint paint2;   // Pour le texte des cases fixes
        private Paint paint3;   // Pour dessiner les lignes rouges (grosse)
        private Paint paint4;   // Pour le texte noir des cases a modifier
        private Paint paint5;

        private int[][] matrix = new int[9][9];
        private boolean[][] fixIdx = new boolean[9][9];

        public Grille(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public Grille(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public Grille(Context context) {
            super(context);
            init();
        }

        private void init() {
            //Grille de depart
	    set("000105000140000670080002400063070010900000003010090520007200080026000035000409000");

            // Grille Gagnante
            //set("072145398145983672389762451263574819958621743714398526597236184426817935831459267");

            // Grille Perdante
            //set("672145198145983672389762451263574819958621743714398526597236184426817935831459267");

            paint1 = new Paint();
            paint1.setAntiAlias(true);
            paint1.setColor(Color.BLACK);
            paint1.setTextAlign(Paint.Align.CENTER);
           // paint1.setStrokeWidth(5);// Couleur noire

            paint2 = new Paint();
            paint2.setAntiAlias(true);
            paint2.setColor(Color.RED);// Couleur rouge
            // Taille du texte
            paint2.setTextAlign(Paint.Align.CENTER);// Centre le texte

            paint3 = new Paint();
            paint3.setAntiAlias(true);
            paint3.setStyle(Paint.Style.STROKE);
            paint3.setStrokeWidth(7);
            paint3.setColor(Color.RED);
            // Couleur rouge et grosses lignes

            // Paint 4 ?
            paint4 = new Paint();
            paint4.setAntiAlias(true);
            paint4.setColor(Color.BLACK);
            paint4.setStyle(Paint.Style.STROKE);
            paint4.setTextAlign(Paint.Align.CENTER);

            paint5 = new Paint();


        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (tailleCase > 3) {
                paint3.setStrokeWidth(tailleCase * 2);
            }
            screenWidth = getWidth();
            screenHeight = getHeight();
            int w = Math.min(screenWidth, screenHeight);
            w = w - (w%9);
            n = w / 9 ;

            // Dessiner 2 lignes rouges verticales et 2 lignes rouges horizontales

            canvas.drawLine(n * 3, 0 + tailleCase, n * 3, (9 * n) - tailleCase, paint3);
            canvas.drawLine(0 + tailleCase, n * 3, (n * 9) - tailleCase, n * 3, paint3);
            canvas.drawLine(n * 6, 0 + tailleCase, n * 6, (9 * n) - tailleCase, paint3);
            canvas.drawLine(0 + tailleCase, n * 6, (n * 9) - tailleCase, n * 6, paint3);

            // Les contenus des cases
            String s;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    s = "" + (matrix[j][i] == 0 ? "" : matrix[j][i]);
                    paint2.setTextSize((n - tailleCase) * 0.7f);
                    paint1.setTextSize((n - tailleCase) * 0.7f);

                    canvas.drawRect((i + tailleCase / 100f) * n, (j + tailleCase / 100f) * n, (i + 1 - tailleCase / 100f) * n, (j + 1 - tailleCase / 100f) * n, paint4);
                    if (fixIdx[j][i]) {
                        canvas.drawText(s, i * n + (n / 2), j * n
                                + (n * 0.75f), paint2);
                    } else {
                        canvas.drawText(s, i * n + (n / 2), j * n
                                + (n * 0.75f), paint1);
                    }
                }
            }
            if (valisation == 1) {
                if (tailleCase == 0) {
                    paint5.setStrokeWidth(5);
                } else {
                    paint5.setStrokeWidth(tailleCase * 4);
                }
                if (this.gagne()) {
                    paint5.setColor(Color.GREEN);
                    // Toast.makeText(Grille.class ,"felisitation !! ", Toast.LENGTH_SHORT).show();
                } else {
                    paint5.setColor(Color.RED);
                }
                canvas.drawLine(0, 0, (n * 9) - tailleCase, 0, paint5);
                canvas.drawLine(0, 0, 0, (n * 9) - tailleCase, paint5);
                canvas.drawLine((n * 9) - tailleCase, 0, (n * 9), (n * 9), paint5);
                canvas.drawLine(0, (n * 9) - tailleCase, (n * 9), (n * 9), paint5);
            }
        }

        public int getXFromMatrix(int x) {
            // Renvoie l'indice d'une case a partir du pixel x de sa position h
            return (x / n);
        }

        public int getYFromMatrix(int y) {
            // Renvoie l'indice d'une case a partir du pixel y de sa position v
            return (y / n);
        }

        public void set(String s, int i) {
            // Remplir la ieme ligne de la matrice matrix avec un vecteur String s
            int v;
            for (int j = 0; j < 9; j++) {
                v = s.charAt(j) - '0';
                matrix[i][j] = v;
                if (v == 0)
                    fixIdx[i][j] = false;
                else
                    fixIdx[i][j] = true;
            }
        }

        public void set(String s) {
            // Remplir la matrice matrix a partir d'un vecteur String s
            for (int i = 0; i < 9; i++) {
                set(s.substring(i * 9, i * 9 + 9), i);
            }
        }

        public void set(int x, int y, int v) {
            // Affecter la valeur v a la case (y, x)
            // y : ligne
            // x : colonne
            matrix[y][x] = v;
        }

        public boolean isNotFix(int x, int y) {
            // Renvoie si la case (y, x) n'est pas fixe
            // A completer
            return !fixIdx[y][x];
        }

        public boolean gagne() {
            // Verifier si la case n'est pas vide ou bien s'il existe
            // un numero double dans chaque ligne ou chaque colonne de la grille
            for (int v = 1; v <= 9; v++) {
                for (int i = 0; i < 9; i++) {
                    boolean bx = false;
                    boolean by = false;
                    for (int j = 0; j < 9; j++) {
                        if (matrix[i][j] == 0) return false;
                        if ((matrix[i][j] == v) && bx) return false;
                        if ((matrix[i][j] == v) && !bx) bx=true;
                        if ((matrix[j][i] == v) && by) return false;
                        if ((matrix[j][i] == v) && !by) by=true;
                    }
                }
            }
            // ------
            // Gagne
            return true;
        }

        public int getValisation() {
            return valisation;
        }
        public void setValisation(int v) {
             this.valisation = v;
        }
    }

