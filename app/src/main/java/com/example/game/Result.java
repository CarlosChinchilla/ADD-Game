package com.example.game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.SecureRandom;

public class Result extends AppCompatActivity implements View.OnClickListener {

    TextView userText; //textview con el nombre del usuario
    TextView nVic; //textview con el número de victorias
    TextView nDer; //textview con el número de derrotas

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //el juego está pensado para pantalla vertical, así que forzamos dicha posicion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //creamos el objeto que se encargará de la elección aleatoria del oponente
        SecureRandom rand = new SecureRandom();
        //inicializamos la variable que contendrá el valor de la elección del oponente
        String eleccionOp = "";

        //creamos e importamos el bundle con los extras del intent
        Bundle extras = getIntent().getExtras();

        //intanciamos el extra con el nombre del usuario
        String user = extras.getString("user");
        //instanciamos el extra con la eleccion del jugador
        String eleccion = extras.getString("eleccion");
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

        //imagen con el resultado del juego
        ImageView resultado = findViewById(R.id.textRes);
        //IMAGENES JUGADOR
        ImageView assUser = findViewById(R.id.assUs);
        ImageView dragUser = findViewById(R.id.dragUs);
        ImageView dragbornUser = findViewById(R.id.dragboUs);
        //IMAGENES OPONENTE
        ImageView assOp = findViewById(R.id.assOp);
        ImageView dragOp = findViewById(R.id.dragOp);
        ImageView dragbornOp = findViewById(R.id.dragboOp);

        //generamos la eleccion del oponente aleatoriamente
        int oponente = rand.nextInt(3);

        //mostramos el nombre del usuario en el textview indicado
        userText.setText(user);

        //animacion numero victorias/derrotas
        Animation animacionVicDer = AnimationUtils.loadAnimation(this, R.anim.victderr);
        animacionVicDer.setFillAfter(true); //mantenemos el tamaño tras la animación
        //animacion del texto victoria/derrota/empate
        Animation animacionRes = AnimationUtils.loadAnimation(this, R.anim.victderrtext);
        resultado.startAnimation(animacionRes);

        //mostramos la eleccion del jugador
        switch (eleccion){

            case "asesino":
                //anulamos la transparencia de la imagen
                assUser.setAlpha((float) 1.0);
                break;

            case "dragon":
                //anulamos la transparencia de la imagen
                dragUser.setAlpha((float) 1.0);
                break;

            case "dragonborn":
                //anulamos la transparencia de la imagen
                dragbornUser.setAlpha((float) 1.0);
                break;
        }
        //mostramos la eleccion del oponente
        switch (oponente){

            case 0:
                eleccionOp = "asesino";
                //anulamos la transparencia de la imagen
                assOp.setAlpha((float) 1.0);
                break;

            case 1:
                eleccionOp = "dragon";
                //anulamos la transparencia de la imagen
                dragOp.setAlpha((float) 1.0);
                break;

            case 2:
                eleccionOp = "dragonborn";
                //anulamos la transparencia de la imagen
                dragbornOp.setAlpha((float) 1.0);
                break;
        }

        //condiciones VICTORIA/DERROTA/EMPATE
            //victoria
        if((eleccion.equals("asesino") && eleccionOp.equals("dragonborn")) || (eleccion.equals("dragonborn") && eleccionOp.equals("dragon")) || (eleccion.equals("dragon") && eleccionOp.equals("asesino"))){
            resultado.setImageResource(R.drawable.vict); //mostramos en pantalla la imagen de victoria
            victorias++; //sumamos 1 al número de victorias
            nVic.startAnimation(animacionVicDer);

            //derrota
        }else if((eleccionOp.equals("asesino") && eleccion.equals("dragonborn")) || (eleccionOp.equals("dragonborn") && eleccion.equals("dragon")) || (eleccionOp.equals("dragon") && eleccion.equals("asesino"))){
            resultado.setImageResource(R.drawable.der); //mostramos en pantalla la imagen de derrota
            derrotas++; //sumamos 1 al número de derrotas
            nDer.startAnimation(animacionVicDer);

            //empate
        }else{
            resultado.setImageResource(R.drawable.emp); //mostramos en pantalla la imagen de empate
        }

        //mostramos el número de victorias en el textview indicado
        nVic.setText(Integer.toString(victorias));
        //mostramos el número de derrotas en el textview indicado
        nDer.setText(Integer.toString(derrotas));

        //listeners
        //boton logout
        findViewById(R.id.buttonLogout).setOnClickListener(this);
        //boton atrás
        findViewById(R.id.buttonBack).setOnClickListener(this);
    }

    /**
     * Método que ejecuta acciones según el botón pulsdado
     *
     * @param v boton pulsado, a continuación se sacará la id para determinar cuál fué.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //botón "salir", salimos a la pantalla de login
            case R.id.buttonLogout:
                //activity del login
                //intent con la información del activity
                Intent i = new Intent(this, MainActivity.class);
                //saltamos de activity con parametro Intent "i"
                startActivity(i);
                break;

            //botón "Volver a jugar", regresamos al activity "Game"
            case R.id.buttonBack:

                //activity del juego
                //intent con la información del activity
                Intent g = new Intent(this, Game.class);
                //nombre del usuario
                g.putExtra("user", userText.getText().toString());
                //número de victorias
                g.putExtra("victorias", Integer.parseInt((String) nVic.getText()));
                //número de derrotas
                g.putExtra("derrotas", Integer.parseInt((String) nDer.getText()));
                //saltamos de activity con parametro Intent "g"
                startActivity(g);

                break;
        }
    }
}