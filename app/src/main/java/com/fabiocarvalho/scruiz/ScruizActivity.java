package com.fabiocarvalho.scruiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import static android.view.View.GONE;

public class ScruizActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private Button btnSiga;

    // Working
    private int i;
    /*
    ================================================================================================
    >> ON CREATE
    ================================================================================================
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scruiz);
        setContentView(R.layout.activity_splash_screen);

        // --- TELA CHEIA --- (esconde Barra Superior)
        // TODO verificar se fica...
        // View decorView = getWindow().getDecorView();
        // Hide the status bar.
        // int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        // decorView.setSystemUiVisibility(uiOptions);
        if (getSupportActionBar() != null){
            Log.d("#barra = ","getSupportAction");
            getSupportActionBar().hide();
        }
        // --- TELA CHEIA --- (fim)

        // --- Botão Inicial ---
        btnSiga = findViewById(R.id.btnSiga);
        btnSiga.setEnabled(false);
        btnSiga.setText(R.string.iniciando_aguarde);
        btnSiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ScruizActivity.this, TesteActivity.class);
                startActivity(i);
            }
        });
        // --- Botão Inicial --- [FIM]

        iniciaProgressBar();

    }
    /*
    ================================================================================================
    >> ON BACK PRESSED
    ================================================================================================
    */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mProgressBar.setProgress(0);
        iniciaProgressBar();
    }

    /*
    ================================================================================================
    >> OUTROS MÉTODOS
    ================================================================================================
    */
    private void iniciaProgressBar(){
        // --- PROGRESS BAR ---
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(i);
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                i = i + 10;
                mProgressBar.setProgress(i);
            }
            public void onFinish() {
                mProgressBar.setProgress(100);
                btnSiga.setEnabled(true);
                btnSiga.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                btnSiga.setText(R.string.pronto_continuar);
                mProgressBar.setVisibility(GONE);
            }
        }.start();
        // --- PROGRESS BAR --- [FIM]
    }


}
