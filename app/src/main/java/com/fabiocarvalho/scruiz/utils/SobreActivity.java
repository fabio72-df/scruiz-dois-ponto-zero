package com.fabiocarvalho.scruiz.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.R;
import com.fabiocarvalho.scruiz.quiz.InicialTesteActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        // BARRA SUPERIOR
        Toolbar myToolbar = findViewById(R.id.toolbar_Sobre);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        // BARRA SUPERIOR --- fim ---

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        // TODO RETIRAR
        Toast.makeText(SobreActivity.this,"SOBRE --voltando---",Toast.LENGTH_SHORT).show();
        intent.setClass(SobreActivity.this,InicialTesteActivity.class);
        startActivity(intent);

    }

}
