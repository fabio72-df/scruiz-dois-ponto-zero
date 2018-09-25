package com.fabiocarvalho.scruiz.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.BaseActivity;
import com.fabiocarvalho.scruiz.R;

public class QuizActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // BARRA SUPERIOR
        Toolbar myToolbar = findViewById(R.id.toolbar_Quiz);
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
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent();
        Toast.makeText(QuizActivity.this,"QUIZ --voltando---",Toast.LENGTH_SHORT).show();
        intent.setClass(QuizActivity.this,InicialTesteActivity.class);
        startActivity(intent);

    }
}
