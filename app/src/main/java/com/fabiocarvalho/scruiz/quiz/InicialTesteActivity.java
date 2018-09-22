package com.fabiocarvalho.scruiz.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.R;
import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;

public class InicialTesteActivity extends AppCompatActivity {

    // Tabela
    TableRow table1, table2, table3, table4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_teste);

        // Dados da Intent
        Intent i = getIntent();
        String userName = i.getStringExtra("#usuario");

        // [BARRA SUPERIOR]
        Toolbar myToolbar = findViewById(R.id.toolbar_Inicial);
        setSupportActionBar(myToolbar);
        //if (getSupportActionBar() != null) {
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //}
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // [BARRA SUPERIOR] *** fim ***

        // Table-Row - opções [menu]
        TableRow table1 = findViewById(R.id.table1);
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicialTesteActivity.this,"Responder questões",Toast.LENGTH_SHORT).show();
            }
        });
        TableRow table2 = findViewById(R.id.table2);
        table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicialTesteActivity.this,"Configurar",Toast.LENGTH_SHORT).show();
            }
        });
        TableRow table3 = findViewById(R.id.table3);
        table3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicialTesteActivity.this,"Ranking",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ppl, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuPpl_1:
                startActivity(new Intent(InicialTesteActivity.this,ChooserActivity.class));
                return true;
            case R.id.menuPpl_2:
                Toast.makeText(InicialTesteActivity.this, "Sobre", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
