package com.fabiocarvalho.scruiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;
import com.fabiocarvalho.scruiz.quiz.InicialTesteActivity;
import com.fabiocarvalho.scruiz.utils.MinhaProgressBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.fabiocarvalho.scruiz.interfaces.*;

public class ScruizActivity
        extends BaseActivity {

    // Constantes
    private final String TAG_USUARIO = "#usuario";

    // TODO: Enquanto mostra ProgressBar, atualizar QUESTÕES
    // Task [???provisório???]
    public MinhaProgressBar mpb;

    // ProgressBar
    protected ProgressBar mProgressBar;
    protected TextView textViewPB;
    // --- fim -- ProgressBar

    // FireBase [autenticação]
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
                    Log.d(TAG_USUARIO,
                            "onAuthStateChanged:SIGNED-IN:" + mUser.getDisplayName());
                } else {
                    // User is signed out
                    Log.d(TAG_USUARIO,
                            "onAuthStateChanged:SIGNED-OUT");
                }
            }
        };
        // Usuário [logado/offline] *** fim ***

        // ProgressBar (associa(bind) para objetos da view)
        mProgressBar = findViewById(R.id.progressBar);
        textViewPB = findViewById(R.id.textView_Carregando);
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
        // RASTREAR USUÁRIO LOGADO (sempre que o usuário fizer login ou logout)
        mAuth.addAuthStateListener(mAuthListener);
        if (mAuth != null) {
            mUser = mAuth.getCurrentUser();
        }
        // RASTREAR USUÁRIO LOGADO *** fim ***

        telaCheia(true);

        // Animation (Logotipo)
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_top_to_center);
        ImageView mLogo = findViewById(R.id.img_LogoSplash);
        mLogo.setImageResource(R.drawable.te_transp_amarelo);
        mLogo.setAlpha(1.0F);
        mLogo.startAnimation(animation);
        // Animation *** fim ***

        // ProgressBar
        /*
           Aqui está sendo utilizada uma interface (ProgressBarInterface) para que possa ser
            tratado o retorno  (quando acaba)
         */
        mProgressBar = findViewById(R.id.progressBar);
        textViewPB = findViewById(R.id.textView_Carregando);
        ProgressBarInterface pbi = new ProgressBarInterface() {
            @Override
            // Implementa ação para retorno da ProgressBar
            public void retornoProgressBar(boolean isSucesso, String msg) {
                boolean logado = false;
                String userName = "";
                Intent intent;
                // USUÁRIO LOGADO -->> DIRECIONA PARA: intent = InicialTeste
                if (mUser != null) {
                    logado = true;
                    userName = mUser.getDisplayName();
                    intent = new Intent(ScruizActivity.this, InicialTesteActivity.class);
                // USUÁRIO NÃO LOGADO -->> DIRECIONA PARA: intent = Chooser
                } else {
                    userName = "[off-line]";
                    intent = new Intent(ScruizActivity.this, ChooserActivity.class);
                }
                textViewPB.setText(userName);
                telaCheia(false);
                intent.putExtra("#usuario", userName);
                intent.putExtra("#logado", logado);
                startActivity(intent);
            }
        };
        mpb = new MinhaProgressBar(this, mProgressBar, textViewPB, pbi);
        mpb.execute();
        // ProgressBar *** fim ***

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
====================================================================================================
>> *** FIM  - ScruizActivity.java ***
====================================================================================================
*/
}


