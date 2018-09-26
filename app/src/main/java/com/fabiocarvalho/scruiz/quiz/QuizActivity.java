package com.fabiocarvalho.scruiz.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fabiocarvalho.scruiz.BaseActivity;
import com.fabiocarvalho.scruiz.R;
import com.fabiocarvalho.scruiz.ScruizActivity;
import com.fabiocarvalho.scruiz.authenticator.ChooserActivity;
import com.fabiocarvalho.scruiz.interfaces.ProgressBarInterface;
import com.fabiocarvalho.scruiz.utils.MinhaProgressBar;
import com.fabiocarvalho.scruiz.utils.NumeroAleatorio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.logging.Handler;

import static android.view.View.GONE;


public class QuizActivity extends BaseActivity {

    // -----[CONSTANTES]-----
    final static String TAG = "#ERRO";

    // -----[FIREBASE]-----
    // private DatabaseReference mUsuarioReference;
    // private DatabaseReference mQuestoesReference;

    // -----[UI]-----
    public TextView textViewPontos, textViewNrQuestao, textViewQuestao;
    public CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    private Button respButton;
    // TODO ::: Ver se pode retirar isso:
    // private Handler handler;

    // ProgressBar
    protected MinhaProgressBar mpb;
    protected ProgressBar mProgressBar;
    protected TextView textViewPB;
    // --- fim -- ProgressBar

    // ----[Working]----
    public ArrayList<Questao> arrayQuestoes = new ArrayList<>();
    public int qstSorteada, pontos, gdaPtosAnonimo;
    private NumeroAleatorio numeroAleatorio = new NumeroAleatorio();

    /*
    ------------------------------------------------------------------------------------------------
    >> ON CREATE
    ------------------------------------------------------------------------------------------------
    */
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

        // ----- [BIND UI] -----
        textViewNrQuestao = findViewById(R.id.textViewNrQuestao);
        textViewQuestao = findViewById(R.id.textViewQuestao);
        textViewPontos = findViewById(R.id.textViewPontos);
        respButton = findViewById(R.id.respButton);
        // --Check-boxies
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        // -- ProgressBar
        mProgressBar = findViewById(R.id.progressBarQst);
        textViewPB = findViewById(R.id.textView_CarregandoQst);
        // ----- [BIND UI] ----- *** fim ***

    }

    /*
    ------------------------------------------------------------------------------------------------
    >> ON START
    ------------------------------------------------------------------------------------------------
    */
    @Override
    protected void onStart() {
        super.onStart();

        if (arrayQuestoes.size() <= 0) {
            carregarQuestoes();
        }

        // RESET UI
        sumirQuestao();

        // Mostra uma questão:
        // ProgressBar
        /* Aqui está sendo utilizada uma interface (ProgressBarInterface) para que possa ser
            tratado o retorno  (quando acaba)  */
        respButton.setVisibility(GONE);
        ProgressBarInterface pbi = new ProgressBarInterface() {
            @Override
            // Implementa ação para retorno da ProgressBar
            public void retornoProgressBar(boolean isSucesso, String msg) {
                mProgressBar.setVisibility(GONE);
                textViewPB.setVisibility(GONE);
                if (arrayQuestoes.size() > 0) {
                    exibirQuestao();
                }else{
                    textViewQuestao.setText(getString(R.string.clique_para_exibir_qst));
                    respButton.setVisibility(View.VISIBLE);
                }
            }
        };
        mpb = new MinhaProgressBar(this, mProgressBar, textViewPB, pbi);
        mpb.execute();
        // ProgressBar *** fim ***

        respButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (respButton.getImeActionLabel().toString().equals("responder")) {
                    if (corrigirQuestao(qstSorteada)) {
                        checkBox1.setClickable(false);
                        checkBox2.setClickable(false);
                        checkBox3.setClickable(false);
                        checkBox4.setClickable(false);
                        checkBox5.setClickable(false);
                        respButton.setBackgroundResource(R.color.grey_500);
                        respButton.setImeActionLabel("proxima", 2);
                    }
                } else {
                    checkBox1.setClickable(true);
                    checkBox2.setClickable(true);
                    checkBox3.setClickable(true);
                    checkBox4.setClickable(true);
                    checkBox5.setClickable(true);
                    respButton.setBackgroundResource(R.color.red);
                    respButton.setImeActionLabel("responder", 2);
                    sumirQuestao();
                    exibirQuestao();
                }
            }
        });


    }

    /*
    ------------------------------------------------------------------------------------------------
    >> ON BACK PRESSED
    ------------------------------------------------------------------------------------------------
    */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.setClass(QuizActivity.this, InicialTesteActivity.class);
        startActivity(intent);
    }

    /*
    ------------------------------------------------------------------------------------------------
    >> CARREGAR QUESTIONARIO (Array)
    ------------------------------------------------------------------------------------------------
    */
    private void carregarQuestoes() {
        DatabaseReference mQuestoesReference = FirebaseDatabase.getInstance().getReference().child("questoes");
        Query consultaQuestoes = mQuestoesReference;
        consultaQuestoes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Questao questao = child.getValue(Questao.class);
                    arrayQuestoes.add(questao);
                    Questionario.setArrayQuestoes(arrayQuestoes);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "ERRO LEITURA QUESTOES--> " + databaseError.getMessage());
                Toast.makeText(QuizActivity.this,
                        "Sistema Indisponível", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sumirQuestao() {
        textViewNrQuestao.setVisibility(View.INVISIBLE);
        textViewQuestao.setVisibility(View.INVISIBLE);
        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
        checkBox5.setVisibility(View.INVISIBLE);
        respButton.setVisibility(View.INVISIBLE);
    }
    /*
    ------------------------------------------------------------------------------------------------
    >> EXIBIR QUESTÃO (sortear e mostrar na tela)
    ------------------------------------------------------------------------------------------------
    */
    public void exibirQuestao() {

        // TODO ::: retirar?
        //btnInc.setVisibility(GONE);

        // ----- [TORNA VISÍVEL] -----
        textViewNrQuestao.setVisibility(View.VISIBLE);
        textViewQuestao.setVisibility(View.VISIBLE);
        checkBox1.setVisibility(View.VISIBLE);
        checkBox2.setVisibility(View.VISIBLE);
        checkBox3.setVisibility(View.VISIBLE);
        checkBox4.setVisibility(View.VISIBLE);
        checkBox5.setVisibility(View.VISIBLE);
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox1.setTextColor(Color.BLACK);
        checkBox2.setTextColor(Color.BLACK);
        checkBox3.setTextColor(Color.BLACK);
        checkBox4.setTextColor(Color.BLACK);
        checkBox5.setTextColor(Color.BLACK);
        respButton.setVisibility(View.VISIBLE);
        // ----- [TORNA VISÍVEL] ----- *** fim ***

        respButton.setImeActionLabel("responder", 1);

        if (Questionario.getArrayQuestoes() != null) {
            numeroAleatorio.setNrMax(Questionario.getArrayQuestoes().size());
            qstSorteada = numeroAleatorio.getNrSorteado();

            // Formata nº da questão
            String gdaTxt = "Cód.Questão: QST" + String.valueOf(qstSorteada);
            SpannableString content = new SpannableString(gdaTxt);
            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            textViewNrQuestao.setText(content);
            // Formata nº da questão *** fim ***

            // Texto da Questão (sorteada)
            gdaTxt = Questionario.getArrayQuestoes().get(qstSorteada).getTxtQuestao();
            textViewQuestao.setText(gdaTxt);
            gdaTxt = Questionario.getArrayQuestoes().get(qstSorteada).getTxtOpc1();
            checkBox1.setText(Questionario.getArrayQuestoes().get(qstSorteada).getTxtOpc1());
            checkBox2.setText(Questionario.getArrayQuestoes().get(qstSorteada).getTxtOpc2());
            gdaTxt = Questionario.getArrayQuestoes().get(qstSorteada).getTxtOpc3();
            if (!gdaTxt.isEmpty()) {
                checkBox3.setVisibility(View.VISIBLE);
                checkBox3.setText(gdaTxt);
            }else{
                checkBox3.setVisibility(GONE);
            }
            gdaTxt = Questionario.getArrayQuestoes().get(qstSorteada).getTxtOpc4();
            if (!gdaTxt.isEmpty()) {
                checkBox4.setVisibility(View.VISIBLE);
                checkBox4.setText(gdaTxt);
            }else{
                checkBox4.setVisibility(GONE);
            }
            gdaTxt = Questionario.getArrayQuestoes().get(qstSorteada).getTxtOpc5();
            if (!gdaTxt.isEmpty()) {
                checkBox5.setVisibility(View.VISIBLE);
                checkBox5.setText(gdaTxt);
            }else{
                checkBox5.setVisibility(GONE);
            }
        }
    }


    private boolean corrigirQuestao(int qn) {
        Boolean acertou = true;
        int qtCertas = 0;
        int qtRespondidas = 0;
        if (Questionario.getArrayQuestoes().get(qn).isOpc1Certa()) {
            qtCertas += 1;
            if (!checkBox1.isChecked()) {
                acertou = false;
            }
        }
        if (Questionario.getArrayQuestoes().get(qn).isOpc2Certa()) {
            qtCertas += 1;
            if (!checkBox2.isChecked()) {
                acertou = false;
            }
        }
        if (Questionario.getArrayQuestoes().get(qn).isOpc3Certa()) {
            qtCertas += 1;
            if (!checkBox3.isChecked()) {
                acertou = false;
            }
        }
        if (Questionario.getArrayQuestoes().get(qn).isOpc4Certa()) {
            qtCertas += 1;
            if (!checkBox4.isChecked()) {
                acertou = false;
            }
        }
        if (Questionario.getArrayQuestoes().get(qn).isOpc5Certa()) {
            qtCertas += 1;
            if (!checkBox5.isChecked()) {
                acertou = false;
            }
        }
        // RESPOSTAS
        if (checkBox1.isChecked()) {
            qtRespondidas += 1;
        }
        if (checkBox2.isChecked()) {
            qtRespondidas += 1;
        }
        if (checkBox3.isChecked()) {
            qtRespondidas += 1;
        }
        if (checkBox4.isChecked()) {
            qtRespondidas += 1;
        }
        if (checkBox5.isChecked()) {
            qtRespondidas += 1;
        }
        String gdaTxt = "";
        String gdaTxtAux;
        if (qtRespondidas == 0) {
            if (!Questionario.getArrayQuestoes().get(qn).isAceitaEmBranco()) {
                if (Questionario.getArrayQuestoes().get(qn).isInformarQtQuestoes()) {
                    gdaTxtAux = " opção.";
                    if (qtCertas > 1) {
                        gdaTxtAux = " opções.";
                    }
                    gdaTxt = "Assinale " + Integer.toString(qtCertas) + gdaTxtAux;
                } else {
                    gdaTxt = "Assinale sua resposta!";
                }
            }
            Toast.makeText(QuizActivity.this, gdaTxt, Toast.LENGTH_SHORT).show();
            return false;
        }
        if ((qtRespondidas != qtCertas) && gdaTxt.equals("")) {
            if (Questionario.getArrayQuestoes().get(qn).isInformarQtQuestoes()) {
                gdaTxtAux = " opção.";
                if (qtCertas > 1) {
                    gdaTxtAux = " opções.";
                }
                gdaTxt = "Assinale " + Integer.toString(qtCertas) + gdaTxtAux;
            } else {
                gdaTxt = "Assinale sua resposta!";
            }
            Toast.makeText(QuizActivity.this, gdaTxt, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gdaTxt.equals("")) {
            if (Questionario.getArrayQuestoes().get(qn).isOpc1Certa()) {
                checkBox1.setTextColor(Color.BLUE);
            }
            if (Questionario.getArrayQuestoes().get(qn).isOpc2Certa()) {
                checkBox2.setTextColor(Color.BLUE);
            }
            if (Questionario.getArrayQuestoes().get(qn).isOpc3Certa()) {
                checkBox3.setTextColor(Color.BLUE);
            }
            if (Questionario.getArrayQuestoes().get(qn).isOpc4Certa()) {
                checkBox4.setTextColor(Color.BLUE);
            }
            if (Questionario.getArrayQuestoes().get(qn).isOpc4Certa()) {
                checkBox5.setTextColor(Color.BLUE);
            }
            pontos = 0;
            if (acertou) {
                Toast.makeText(QuizActivity.this, "Parabéns! Está CORRETO.", Toast.LENGTH_SHORT).show();
                pontos = 1;
            } else {
                Toast.makeText(QuizActivity.this, "Ops! Continue tentando...", Toast.LENGTH_SHORT).show();
                /* TODO ::: IMPLEMENTAR PONTUAÇÃO
                if (mClasseUsuarios.getpontosUsuario() > 0) {
                    pontos = -1;
                } */
            }
        }
        /* TODO ::: IMPLEMENTAR PONTUAÇÃO
        if (mClasseUsuarios.getNmUsuario().equals("anonimo")) {
            if (pontos < 0) {
                if (gdaPtosAnonimo > 0) {
                    gdaPtosAnonimo = gdaPtosAnonimo + pontos;
                }
            } else {
                gdaPtosAnonimo = gdaPtosAnonimo + pontos;
            }
            gdaTxt = "Pontuação atual: " + gdaPtosAnonimo;
            textViewPontos.setText(gdaTxt);
            gdaTxt = "";
            pontos = 1;
        }
        // ATUALIZA PONTUAÇÃO
        mUsuarioReference.child("pontosUsuario")
                .setValue(mClasseUsuarios.getpontosUsuario() + pontos); */

        return true;
    }

/*
====================================================================================================
>> FIM Activity
====================================================================================================
*/
}
