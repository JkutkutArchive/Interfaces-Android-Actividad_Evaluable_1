package com.jkutkut.a03_componentes_dinamicos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutBotones;
    private int contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutBotones = findViewById(R.id.layoutBotones);

        Button botonCrearBotones = findViewById(R.id.crearBoton);
        Button btnProgressBar = findViewById(R.id.btnProgressBar);

        botonCrearBotones.setOnClickListener((v) -> {
            Button botonCreado = new Button(this);
            ActionBar.LayoutParams layoutParams;
            layoutParams = new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            botonCreado.setLayoutParams(layoutParams);
            botonCreado.setGravity(Gravity.TOP);
            botonCreado.setText("Boton Creado " + contador++);
            botonCreado.setOnClickListener(v2 -> {
                Button boton = (Button) v2;
                Log.v("MainActivity", "Pulsado el boton " + boton.getText());
                Toast.makeText(this,
                        "Ha pulsado el boton " + boton.getText(),
                        Toast.LENGTH_SHORT).show();
            });
            botonCreado.setOnLongClickListener(v3 -> {
                ViewGroup vg = (ViewGroup)v3.getParent();
                vg.removeView(v3);
                return true;
            });
            layoutBotones.addView(botonCreado);
        });

        btnProgressBar.setOnClickListener(this::download);
    }

    public void download(View view){
        ProgressDialog progress=new ProgressDialog(this);
        progress.setMessage("Downloading Music");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    int jumpTime = 0;
                    sleep(2000);
                    progress.setIndeterminate(false);
                    while(jumpTime < totalProgressTime) { // Progress time
                        System.out.println("jumpTime: " + jumpTime);
                        progress.setProgress(jumpTime);
                        sleep(200);
                        jumpTime += 5;
                    }
                    progress.dismiss(); // Hide progressbar
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }
}