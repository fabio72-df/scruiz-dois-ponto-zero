package com.fabiocarvalho.scruiz.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fabiocarvalho.scruiz.R;
import com.fabiocarvalho.scruiz.ScruizActivity;
import com.fabiocarvalho.scruiz.interfaces.ProgressBarInterface;

public class MinhaProgressBar extends AsyncTask<Object, Object, String> {

    private ProgressBarInterface progressBarInterface;

    private ProgressBar progressBar;
    private TextView texto;
    private static int total = 0;
    private static int PROGRESSO = 20;


    public MinhaProgressBar(Context context, ProgressBar progressBar, TextView texto, ProgressBarInterface progressBarInterface) {
        this.progressBar = progressBar;
        this.texto = texto;
        this.progressBarInterface = progressBarInterface;
    }

    public MinhaProgressBar(Context context, ProgressBar progressBar, TextView texto) {
        this.progressBar = progressBar;
        this.texto = texto;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setProgress(0);
        total = 0;
        texto.setText("0%");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            Thread.sleep(1000);
            for (int i = 0; i < 5; i++) {
                publishProgress();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        total += PROGRESSO;
        progressBar.incrementProgressBy(PROGRESSO);
        String gdaText = "Carregando informações [" + total + "%" + "]";
        texto.setText(gdaText);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        progressBar.setVisibility(ProgressBar.INVISIBLE);
        texto.setText(R.string.pronto);
        texto.setGravity(Gravity.CENTER_HORIZONTAL);

        if (progressBarInterface != null) {
            progressBarInterface.retornoProgressBar(true, "FIM Progress");
        }

        super.onPostExecute(result);

    }
}