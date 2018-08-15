package com.fabiocarvalho.scruiz;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;
import com.fabiocarvalho.scruiz.utils.MinhaProgressBar;
import com.google.android.gms.common.api.BooleanResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.fabiocarvalho.scruiz.interfaces.*;

public class ScruizActivity extends AppCompatActivity {

    // Constantes
    private final String TAG_USUARIO = "#usuario";
    private final Class ESCOLHER_LOGIN = ChooserActivity.class;

    // TODO
    //Task [provisório]
    public MinhaProgressBar mpb;

    // ProgressBar
    protected ProgressBar mProgressBar;
    protected TextView textViewPB;

    // FireBase
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

    // Working
    int cntGeral;

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
    >> ON CREATE
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Usuário [logado/offline]
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = mAuth.getCurrentUser();
                if (mUser != null) {
                    // User is signed in
                    Log.d(TAG_USUARIO, "onAuthStateChanged:SIGNED-IN:" + mUser.getDisplayName());
                } else {
                    // User is signed out
                    Log.d(TAG_USUARIO, "onAuthStateChanged:SIGNED-OUT");
                }
            }
        };
        mUser = mAuth.getCurrentUser();
        // *** fim *** Usuário [logado/offline]

        // ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        textViewPB = (TextView) findViewById(R.id.textView_Carregando);
        // ProgressBar *** fim ***

    }

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
    >> ON START
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
    @Override
    protected void onStart() {
        super.onStart();
        // Rastrear sempre que o usuário fizer login ou logout:
        mAuth.addAuthStateListener(mAuthListener);

        telaCheia(true);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        ImageView mLogo = findViewById(R.id.img_LogoSplash);
        mLogo.setImageResource(R.drawable.te_transp_amarelo);
        mLogo.setAlpha(1.0F);
        mLogo.startAnimation(animation);

        // ProgressBar
        // (::para receber retorno enviar Interface (ProgressBarInterface)
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        textViewPB = (TextView) findViewById(R.id.textView_Carregando);
        ProgressBarInterface pbi = new ProgressBarInterface() {
            @Override
            public void retornoProgressBar(boolean isSucesso, String msg) {
                String string = "1ª chamada::: " + isSucesso + " -- " + msg;
                Toast.makeText(ScruizActivity.this, string, Toast.LENGTH_SHORT).show();
                telaCheia(false);
            }
        };
        mpb = new MinhaProgressBar(this, mProgressBar, textViewPB, pbi);
        mpb.execute();
        // ---[fim}--- progressBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Parar de rastrear log do usuário
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
    >> DIVERSOS
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
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
    }
/*
====================================================================================================
>> *** FIM  - ScruizActivity.java ***
====================================================================================================
*/
}


