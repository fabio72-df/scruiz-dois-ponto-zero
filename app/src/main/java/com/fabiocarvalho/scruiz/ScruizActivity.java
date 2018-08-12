package com.fabiocarvalho.scruiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.GONE;

public class ScruizActivity extends AppCompatActivity {

    // Constantes
    private final String TAG_USUARIO = "#usuario logado";
    private final Class ESCOLHER_LOGIN = ChooserActivity.class;

    // FireBase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // UI
    private ProgressBar mProgressBar;

    // Working
    private int i;
    private boolean pronto;

    /*
    ================================================================================================
    >> ON CREATE
    ================================================================================================
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = mAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG_USUARIO, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG_USUARIO, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            Toast.makeText(ScruizActivity.this, mUser.getUid(), Toast.LENGTH_SHORT).show();
        }

        telaCheia(true);
        iniciarProgressBar();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        ImageView mLogo = findViewById(R.id.img_LogoSplash);
        mLogo.setImageResource(R.drawable.te_transp_amarelo);
        mLogo.setAlpha(1.0F);
        mLogo.startAnimation(animation);

    }

    /*
        ================================================================================================
        >> DIVERSOS
        ==============================================================================================*/
    // ---------------------------------------------------------------------------------------------
    // Tela Cheia
    // ---------------------------------------------------------------------------------------------
    private void telaCheia(boolean liga) {
        // --- TELA CHEIA --- (esconde Barra Superior)
        // Hide the status bar.
        View decorView = getWindow().getDecorView();
        int uiOptions;
        if (liga) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        } else {
            uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        }
        decorView.setSystemUiVisibility(uiOptions);
        if (getSupportActionBar() != null) {
            if (liga) {
                getSupportActionBar().show();
            } else {
                getSupportActionBar().hide();
            }
        }
        // --- TELA CHEIA --- (fim)
    }
    // ---------------------------------------------------------------------------------------------
    // Progress Bar
    // ---------------------------------------------------------------------------------------------
    private void iniciarProgressBar() {
        i = 0;
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setIndeterminate(false);
        mProgressBar.setProgress(i);
        // Contagem = 8 segundos
        new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
                i = i + 10;
                mProgressBar.setProgress(i);
            }
            public void onFinish() {
                mProgressBar.setProgress(100);
                mProgressBar.setVisibility(GONE);
                telaCheia(false);
                Intent i = new Intent(ScruizActivity.this, ESCOLHER_LOGIN);
                startActivity(i);
            }
        }.start();
    }

    /*
    ================================================================================================
    >> *** FIM  - ScruizActivity.java ***
    ================================================================================================
    */
}
