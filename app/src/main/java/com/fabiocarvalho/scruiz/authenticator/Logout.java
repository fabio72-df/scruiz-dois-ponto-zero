package com.fabiocarvalho.scruiz.authenticator;

public class Logout {

    public String logout(String TIPO_AUTENTICACAO) {

        String retorno = null;
        switch (TIPO_AUTENTICACAO) {
            case "Google":
                retorno = "Google";

            case "E-mail":
                retorno = "E-mail";


            case "Anonimo":
                retorno = "Anonimo";
        }
        return retorno;
    }

}
