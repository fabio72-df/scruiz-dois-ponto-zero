package com.fabiocarvalho.scruiz.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.R;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        // BARRA SUPERIOR
        Toolbar myToolbar = findViewById(R.id.toolbar_Ranking);
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
        Toast.makeText(RankingActivity.this,"QUIZ --voltando---",Toast.LENGTH_SHORT).show();
        intent.setClass(RankingActivity.this,InicialTesteActivity.class);
        startActivity(intent);

    }

}
