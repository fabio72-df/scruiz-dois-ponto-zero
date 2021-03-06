package com.fabiocarvalho.scruiz.utils;

import java.util.ArrayList;
import java.util.Random;

public class NumeroAleatorio {

    private Random nrAleatorio = new Random();

    private static ArrayList<Integer> repeat_list = new ArrayList();

    private int nrMax;

    private int nrSorteado;

    public NumeroAleatorio() {
    }
    public NumeroAleatorio(int nrSorteado) {
        this.nrSorteado = nrSorteado;
    }
    public int getNrSorteado() {
        nrSorteado = nrSort(nrMax);
        return nrSorteado;
    }

    public void setNrMax(int nrMax) {
        this.nrMax = nrMax;
    }

    private int nrSort(int nrMax) {

        if (repeat_list.size() == nrMax) {
            int ultNr = repeat_list.get(nrMax - 1);
            repeat_list = new ArrayList();
            repeat_list.add(ultNr);
        }

        int numero = nrAleatorio.nextInt(nrMax) + 1;

        while (true) {
            if (repeat_list.contains(numero)) {
                // TODO - Alterado nova versao com Array de questões
                //numero = nrAleatorio.nextInt(nrMax) + 1;
                numero = nrAleatorio.nextInt(nrMax);
            } else {
                repeat_list.add(numero);
                break;
            }
        }
        return numero;
    }


}
