package com.example.game;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userText; //editview con el nombre del usuario
    EditText passText; //editview con el password del usuario

    AlertDialog errorDialog; //mensaje de error

    String user; //usuario
    String pass; //contraseña

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //el juego está pensado para pantalla vertical, así que forzamos dicha posicion
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //reproduccion de la musica
        //creacion del objeto "mediapolayer"
        MediaPlayer mediaPlayer;
        //enlazamos la musica
        mediaPlayer = MediaPlayer.create(this, R.raw.battle);
        //lo iniciamos
        mediaPlayer.start();

        //localización user/password del login
        userText = findViewById(R.id.user);
        passText = findViewById(R.id.password);

        //logo principal
        ImageView logo = findViewById(R.id.mainLogo);
        //animacion logo principal
        Animation animacionLogo = AnimationUtils.loadAnimation(this, R.anim.animtitle);
        logo.startAnimation(animacionLogo);

        //listener boton "entrar"
        findViewById(R.id.buttonEntrar).setOnClickListener(this);

        //determinamos la combinación usuario/contraseña
        user = "user";
        pass = "user";
    }

    /**
     * Método que ejecuta acciones según el botón pulsdado
     *
     * @param v boton pulsado, a continuación se sacará la id para determinar cuál fué.
     */
    @Override
    public void onClick(View v) {

        //si el campo usuario esta vacio
        if(userText.getText().length() == 0){

            userText.setError("Completa este campo");
        }

        //si el campo contraseña esta vacio
        if(passText.getText().length() == 0){

            passText.setError("Completa este campo");
        }

        //si no estan vacios
        if(passText.getText().length() != 0 && userText.getText().length() != 0) {

            //y coinciden usuario y contraseña
            if (userText.getText().toString().equals(user) && passText.getText().toString().equals(pass)) {

                //activity del juego
                //intent con la información del activity
                Intent i = new Intent(this, Game.class);
                //nombre de usuario
                i.putExtra("user", user);
                //victorias (se resetean al desloguear)
                i.putExtra("victorias", 0);
                //derrotas (se resetean al desloguear)
                i.putExtra("derrotas", 0);

                //saltamos de activity con parametro Intent "i"
                startActivity(i);
                overridePendingTransition(R.anim.victderrtext, R.anim.victderr);

            } else { //si no coinciden usuario contraseña

                //se informa por pantalla del error
                //creación del "dialog" que se muestra en caso de usuario/contraseña erroneos
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Error");
                builder.setMessage("Combinación Usuario/Contraseña incorrecta");
                builder.setPositiveButton("Aceptar", null);
                errorDialog = builder.create();
                errorDialog.show();
            }
        }
    }
}