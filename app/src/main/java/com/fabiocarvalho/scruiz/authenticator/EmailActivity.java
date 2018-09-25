package com.fabiocarvalho.scruiz.authenticator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.R;

public class EmailActivity extends ChooserActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        emImplementacao(getBaseContext());

    }
}
