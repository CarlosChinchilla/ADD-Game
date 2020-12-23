package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity implements View.OnClickListener {

    TextView userText; //textview con el nombre del usuario
    TextView nVic; //textview con el número de victorias
    TextView nDer; //textview con el número de derrotas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //el juego está pensado para pantalla vertical, así que forzamos dicha posicion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //creamos e importamos el bundle con los extras del intent
        Bundle extras = getIntent().getExtras();

        //intanciamos el extra con el nombre del usuario
        String user = extras.getString("user");

        //instanciamos el extra con las vistorias del jugador
        int victorias = extras.getInt("victorias");
        //instanciamos el extra con las derrotas del jugador
        int derrotas = extras.getInt("derrotas");

        //textView con el nombre del usuario
        userText = findViewById(R.id.textUser);
        //textview con el número de victorias
        nVic = findViewById(R.id.nVic);
        //textview con el número de derrotas
        nDer = findViewById(R.id.nDer);

        //mostramos el nombre del usuario en el textview indicado
        userText.setText(user);
        //mostramos el número de victorias en el textview indicado
        nVic.setText(Integer.toString(victorias));
        //mostramos el número de derrotas en el textview indicado
        nDer.setText(Integer.toString(derrotas));

        //animacion "Eige tu jugada"
        TextView elige = findViewById(R.id.choose);
        Animation animacionElige = AnimationUtils.loadAnimation(this, R.anim.hint);
        animacionElige.setFillAfter(true); //mantenemos el tamaño tras la animación
        elige.startAnimation(animacionElige);

        //animacion "VS"
        TextView vs = findViewById(R.id.textVS);
        Animation animacionVs = AnimationUtils.loadAnimation(this, R.anim.victderrtext);
        vs.startAnimation(animacionVs);

        //listeners
        //boton logout
        findViewById(R.id.buttonLogout).setOnClickListener(this);
        //boton de la eleccion de "asesino"
        findViewById(R.id.assUs).setOnClickListener(this);
        //boton de la eleccion de "dragon"
        findViewById(R.id.dragUs).setOnClickListener(this);
        //boton de la eleccion de "dragonborn"
        findViewById(R.id.dragboUs).setOnClickListener(this);

    }

    /**
     * Método que ejecuta acciones según el botón pulsdado
     *
     * @param v boton pulsado, a continuación se sacará la id para determinar cuál fué.
     */
    @Override
    public void onClick(View v) {

        //inicializamos la variable que contendrá la elección del jugador
        String eleccion = "";

        switch (v.getId()) {

            //botón "salir", salimos a la pantalla de login
            case R.id.buttonLogout:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;

            //botón de elección "asesino"
            //se guarda la elección y se llama el método "resultado" con la elección como parámetro
            case R.id.assUs:
                eleccion = "asesino"; //se guarda la elección
                resultado(eleccion); //se llama el método "resultado" con la elección como parámetro
                break;

            //botón de elección "dragon"
            // se guarda la elección y se llama el método "resultado" con la elección como parámetro
            case R.id.dragUs:
                eleccion = "dragon"; //se guarda la elección
                resultado(eleccion); //se llama el método "resultado" con la elección como parámetro
                break;

            //botón de elección "dragonborn"
            case R.id.dragboUs:
                eleccion = "dragonborn"; //se guarda la elección
                resultado(eleccion); //se llama el método "resultado" con la elección como parámetro
                break;

        }
    }

    /**
     * Método que crea y rellena el intent, y abre el activity "Result"
     *
     * @param eleccion variable String con la elección del jugador
     */
    public void resultado(String eleccion){

        //Activity del resultado
        //intent con la información del activity
        Intent r = new Intent(this, Result.class);
        //nombre del usuario
        r.putExtra("user", userText.getText().toString());
        //elección del jugador
        r.putExtra("eleccion", eleccion);
        //número de victorias
        r.putExtra("victorias", Integer.parseInt((String) nVic.getText()));
        //número de derrotas
        r.putExtra("derrotas", Integer.parseInt((String) nDer.getText()));
        //saltamos de activity con parametro Intent "r"
        startActivity(r);
    }
}
