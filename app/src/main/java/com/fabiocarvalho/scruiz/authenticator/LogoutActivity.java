package com.fabiocarvalho.scruiz.authenticator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fabiocarvalho.scruiz.BaseActivity;
import com.fabiocarvalho.scruiz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutActivity extends BaseActivity {

    // ARQUIVO DE PREFERÊNCIAS
    private final String ARQ_PREF = "Arq_Pref";
    private final String ARQ_PREF_TP_AUTH = "tipoAutent";

    // FireBase [autenticação]
    private FirebaseUser mUser;

    Button sim_button, nao_button;

    TextView tpAuth_TextView, usuario_TextView;

    /*
    ------------------------------------------------------------------------------------------------
    >> ON CREATE
    ------------------------------------------------------------------------------------------------
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        // BARRA SUPERIOR
        Toolbar myToolbar = findViewById(R.id.toolbar_Logout);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // BARRA SUPERIOR *** fim ***

        // Bind dos botões
        sim_button = findViewById(R.id.logout_sim_button);
        nao_button = findViewById(R.id.logout_nao_button);
        // Bind dos botões *** fim ***

        // Bind das TextViews
        tpAuth_TextView = findViewById(R.id.tipoAuth_textTview);
        usuario_TextView = findViewById(R.id.usuario_textTview);
        // Bind das TextViews *** fim ***
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        String txtAux;
        switch (lerAppPreferences()){
            case 0:
                txtAux = "Google";
                break;
            case 1:
                txtAux = "E-mail";
                break;
            case 2:
                txtAux = "Anônimo";
                break;
            default:
                txtAux = "-";
                break;
        }
        tpAuth_TextView.setText(getString(R.string.logado_com_fmt, txtAux));
        usuario_TextView.setText(getString(R.string.usuario_fmt, mUser.getDisplayName()));

        sim_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (lerAppPreferences()){
                    // GOOGLE
                    case 0:
                        updateAppPreferences(mUser);
                        startActivity(new Intent(LogoutActivity.this,
                                GoogleActivity.class));
                        break;
                    case 1:
                    case 2:
                        emImplementacao(LogoutActivity.this);
                        break;
                    default:
                        startActivity(new Intent(LogoutActivity.this,
                                ChooserActivity.class));
                        break;
                }
            }
        });

        nao_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lerAppPreferences();
            }
        });

    }

    private void updateAppPreferences(FirebaseUser user) {
        if (user != null) {
            //appPreferences = new AppPreferences(0);
            SharedPreferences sharedPreferences = getSharedPreferences(ARQ_PREF, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("tipoAutent", -1);
            editor.commit();
            int tpAutentLido = -1;
            sharedPreferences = getSharedPreferences(ARQ_PREF, 0);
            if (sharedPreferences.contains(ARQ_PREF_TP_AUTH)) {
                tpAutentLido = sharedPreferences.getInt(ARQ_PREF_TP_AUTH, -1);
            }
        }
    }

    private int lerAppPreferences() {
        int tpAutentLido = -1;
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(ARQ_PREF, 0);
        if (sharedPreferences.contains(ARQ_PREF_TP_AUTH)) {
            tpAutentLido = sharedPreferences.getInt(ARQ_PREF_TP_AUTH, -1);
        }
        return tpAutentLido;
    }

/*
====================================================================================================
>> FIM DA ACTIVITY
====================================================================================================
*/
}
