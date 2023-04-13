package com.example.genius;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.security.SecureRandom;
import java.util.Arrays;
public class telajogo extends AppCompatActivity {
    Button btnvoltar,btnred, btnblue, btnyellow, btngreen, btnreset;
    TextView t;
    int score=0, state=0, multi;
    int [] jogo;
    private SecureRandom secureRandom = new SecureRandom();
    private SharedPreferences sharedPreferences;
    private static final String PREF_SCORE = "pref_score", PREF_HIGH_SCORE = "pref_highscore";


    boolean gameover = false, start=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telajogo);

        btnvoltar = findViewById(R.id.btninicio);
        btnblue = findViewById(R.id.btnazul);
        btnred = findViewById(R.id.btnvermelho);
        btnreset = findViewById(R.id.btnreset);
        btngreen = findViewById(R.id.btnverde);
        btnyellow = findViewById(R.id.btnamarelo);
        t = findViewById(R.id.view);
        sharedPreferences = getPreferences(MODE_PRIVATE);

        int highScore = pontuacaoMax();
        TextView highScoreTextView = findViewById(R.id.pontMax);
        highScoreTextView.setText("Maior pontuação: " + highScore);


        int score = sharedPreferences.getInt(PREF_SCORE, 0);


        TextView view = findViewById(R.id.view);
        view.setText("Pontuação: " + score);

        btnreset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (state >= 1 && gameover == true)
                {
                    Intent intent = new Intent(getApplicationContext(), telajogo.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnred.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (state >= 1)
                {
                    jogadorEntrada(0);
                }

                Handler antibug = new Handler();
                antibug.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        btnblue.setClickable(true);
                    }
                }, 1000);
            }
        });

        btngreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (state >= 1)
                {
                    jogadorEntrada(1);
                }

                Handler antibug = new Handler();
                antibug.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        btnblue.setClickable(true);
                    }
                }, 1000);
            }
        });

        btnblue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (state >= 1)
                {
                    btnblue.setClickable(false);
                    jogadorEntrada(2);
                }

                Handler antibug = new Handler();
                antibug.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        btnblue.setClickable(true);
                    }
                }, 1000);
            }
        });

        btnyellow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (state >= 1)
                {
                    jogadorEntrada(3);
                }

                Handler antibug = new Handler();
                antibug.postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        btnblue.setClickable(true);
                    }
                }, 1000);
            }
        });

        btnvoltar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (gameover == false && start==false && state==0)
                {
                    state++;
                    start=true;
                    jogo = gerarSequencia();
                    t.setText( Integer.toString(jogo.length));
                    blinkcores(btnred,btnblue,btngreen,btnyellow);
                    Handler retornaclick = new Handler();
                    retornaclick.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            defineClickT(btnred, btnblue, btngreen, btnyellow);
                        }
                    }, multi);
                }
                else if (gameover == false && start==false && state >= 1)
                {
                    Handler comeca = new Handler();
                    comeca.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adicionarValor(secureRandom.nextInt(4));
                            t.setText( Integer.toString(jogo.length));
                            blinkcores(btnred,btnblue,btngreen,btnyellow);
                            Handler retornaclick = new Handler();
                            retornaclick.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    defineClickT(btnred, btnblue, btngreen, btnyellow);
                                }
                            }, multi);
                        }
                    }, 200);
                }
            }
        });
    }
    public void jogadorEntrada(int btnId)
    {
        if(!gameover){
            if (btnId == jogo[score]){
                score++;
                if (score == jogo.length){
                    score = 0;
                    Handler start = new Handler();
                    start.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adicionarValor(secureRandom.nextInt(4));
                            t.setText(Integer.toString(jogo.length));
                            blinkcores(btnred, btnblue, btngreen, btnyellow);
                            Handler retornaclick = new Handler();
                            retornaclick.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    defineClickT(btnred, btnblue, btngreen, btnyellow);
                                }
                            }, multi);
                        }
                    }, 1000);
                }
            }else {
                gameover = true;
                salvaPontuacao(state);
                defineClickF(btnred, btnblue, btngreen, btnyellow);
                Toast.makeText(getApplicationContext(), "GAME OVER! " + state + " pontos.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public int[] gerarSequencia()
    {
        jogo = new int[1];
        jogo[0] = secureRandom.nextInt(4);

        return jogo;
    }
    public void adicionarValor(int valor)
    {
        if (state <= 50)
        {
            state++;
            int[] novaSequencia = new int[state];
            for (int i = 0; i < jogo.length; i++) {
                novaSequencia[i] = jogo[i];
            }
            novaSequencia[(jogo.length)] = valor;
            jogo = novaSequencia;
        }
        else
        {
            gameover = true;
        }
    }
    private void blinkcores(Button red, Button blue, Button green, Button yellow)
    {
        defineClickF(red, blue, green, yellow);
        Handler blinkcores = new Handler();
        for (int i = 0; i < jogo.length; i++)
        {
            int fseq = i;
            blinkcores.postDelayed(new Runnable()
            {
                @Override
                public void run() {
                    switch (jogo[fseq])
                    {
                        case 0:
                            red.setBackgroundColor(Color.rgb(255,169,169));
                            red.setPressed(true);
                            break;
                        case 1:
                            green.setBackgroundColor(Color.rgb(169,255,169));
                            green.setPressed(true);
                            break;
                        case 2:
                            blue.setBackgroundColor(Color.rgb(169,169,255));
                            blue.setPressed(true);
                            break;
                        case 3:
                            yellow.setBackgroundColor(Color.rgb(255,255,169));
                            yellow.setPressed(true);
                            break;
                    }
                    Handler voltapadrao = new Handler();
                    voltapadrao.postDelayed(new Runnable()
                    {
                        @Override
                        public void run() {
                            yellow.setBackgroundColor(Color.rgb(255,255,0));
                            blue.setBackgroundColor(Color.rgb(0,0,255));
                            green.setBackgroundColor(Color.rgb(0,255,0));
                            red.setBackgroundColor(Color.rgb(255,0,0));
                            red.setPressed(false);
                            green.setPressed(false);
                            blue.setPressed(false);
                            yellow.setPressed(false);

                        }
                    }, 1000);
                }
            }, i*1300);
            multi = i *1600;
        }
    }
    private void defineClickF(Button a,Button b,Button c,Button d)
    {
        a.setClickable(false);
        b.setClickable(false);
        c.setClickable(false);
        d.setClickable(false);
    }
    private void defineClickT(Button a, Button b, Button c, Button d)
    {
        a.setClickable(true);
        b.setClickable(true);
        c.setClickable(true);
        d.setClickable(true);
    }

    private int pontuacaoMax() {
        return sharedPreferences.getInt(PREF_HIGH_SCORE, 0);
    }

    private void salvaPontuacao(int score) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(PREF_SCORE, score);
        editor.apply();

        int highScore = pontuacaoMax();
        if (score > highScore) {
            editor.putInt(PREF_HIGH_SCORE, score);
            editor.apply();
        }


    }
}