package com.fabiocarvalho.scruiz.quiz;

import java.util.ArrayList;

public class Questionario {

    private static ArrayList<Questao> arrayQuestoes;

    public Questionario() {
    }

    public static ArrayList<Questao> getArrayQuestoes() {
        return arrayQuestoes;
    }

    public static void setArrayQuestoes(ArrayList<Questao> arrayQuestoes) {
        Questionario.arrayQuestoes = arrayQuestoes;
    }
}