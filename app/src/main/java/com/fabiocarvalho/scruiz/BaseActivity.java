package com.fabiocarvalho.scruiz;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.SearchEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;
import com.fabiocarvalho.scruiz.authenticator.LoginAnonimoActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.security.Principal;

public class BaseActivity
        extends AppCompatActivity {

    // FireBase [autenticação]
    private FirebaseAuth mAuth;

    // ---------------------------------------------------------------------------------------------
    // Tela Cheia
    // ---------------------------------------------------------------------------------------------
    public void telaCheia(boolean liga) {
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

    // ---------------------------------------------------------------------------------------------
    // "Em Implementação"
    // ---------------------------------------------------------------------------------------------
    public void emImplementacao(Context c) {
        Toast.makeText(c,
                getString(R.string.em_implementacao), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(c, ChooserActivity.class);
        startActivity(i);
    }

    /* ---------------------------------------------------------------------------------------------
    // ALERTA LOGOUT
    // ---------------------------------------------------------------------------------------------
    // ***********     FORA DE USO   *************************
    // ---------------------------------------------------------------------------------------------
    public void alertaLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Mensagem
        builder.setTitle("Atenção");
        builder.setMessage("Confirma LOGOUT?:");
        // Add the buttons
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mAuth = FirebaseAuth.getInstance();
                if (mAuth != null) {
                    mAuth.signOut();
                }
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // NÃO FAZ NADA
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }    */


/*
====================================================================================================
>> FIM DA ACTIVITY
====================================================================================================
*/
}
