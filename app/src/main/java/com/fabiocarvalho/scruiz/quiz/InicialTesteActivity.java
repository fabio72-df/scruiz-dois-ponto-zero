package com.fabiocarvalho.scruiz.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.BaseActivity;
import com.fabiocarvalho.scruiz.R;
import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;
import com.fabiocarvalho.scruiz.authenticator.LogoutActivity;
import com.fabiocarvalho.scruiz.utils.SobreActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicialTesteActivity
        extends BaseActivity {

    // Tabela MENU
    TableRow table1, table2, table3;

    // Menu Barra Superior
    private Menu mMenu;

    // Working
    private final String EFETUE_LOGIN = "Efetue LOGIN (menu superior)";

    // FireBase [autenticação]
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
    >> ON CREATE
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_teste);

        // Usuário [logado/offline]
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateMenuTitles();
            }
        };
        // Usuário [logado/offline] *** fim ***

        // BARRA SUPERIOR
        Toolbar myToolbar = findViewById(R.id.toolbar_Inicial);
        setSupportActionBar(myToolbar);
        // Não habilita seta de retorno
        //if (getSupportActionBar() != null) {
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //}
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // BARRA SUPERIOR --- fim ---

        // Table-Row - opções [menu]
        TableRow table1 = findViewById(R.id.table1);
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logado(EFETUE_LOGIN)) {
                    startActivity(new Intent
                            (InicialTesteActivity.this,QuizActivity.class));
                }
            }
        });
        TableRow table2 = findViewById(R.id.table2);
        table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logado(EFETUE_LOGIN)) {
                    startActivity(new Intent
                            (InicialTesteActivity.this,ConfigurarActivity.class));
                }
            }
        });
        TableRow table3 = findViewById(R.id.table3);
        table3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logado(EFETUE_LOGIN)) {
                    startActivity(new Intent
                            (InicialTesteActivity.this,RankingActivity.class));
                }
            }
        });

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
        // RASTREAR USUÁRIO LOGADO *** fim ***

        // Dados da Intent
        Intent i = getIntent();
        String userName = i.getStringExtra("#usuario");

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
    >> MENU DA BARRA SUPERIOR
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ppl, menu);

        mMenu = menu;
        updateMenuTitles();

        return true;
    }

    private void updateMenuTitles() {
        if (mMenu != null) {
            MenuItem menuItem = mMenu.findItem(R.id.menuPpl_1);
            if (logado("")) {
                menuItem.setTitle("Logout");
            } else {
                menuItem.setTitle("Login");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            // TODO case R.id.menuPpl_1:
            case R.id.menuPpl_1:
                if (logado("")) {
                    startActivity(new Intent(InicialTesteActivity.this,
                            LogoutActivity.class));
                } else {
                    startActivity(new Intent(InicialTesteActivity.this,
                            ChooserActivity.class));
                }
                return true;
            case R.id.menuPpl_2:
                startActivity(new Intent(InicialTesteActivity.this,
                        SobreActivity.class));
                return true;
            default:
                Toast.makeText(InicialTesteActivity.this, item.getItemId(),
                        Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    ////////////////////////////////////////////////////////////////////////////////////////////////
    >> DIVERSOS
    ////////////////////////////////////////////////////////////////////////////////////////////////
    */
    public boolean logado(String msg) {
        // FireBase [autenticação]
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            if (!msg.equals("")) {
                Toast.makeText(InicialTesteActivity.this,
                        msg, Toast.LENGTH_SHORT).show();
            }
            return false;
        } else {
            return true;
        }
    }
/*
////////////////////////////////////////////////////////////////////////////////////////////////////
>> FIM ---------------------------------------------------------------------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////////
*/
}
