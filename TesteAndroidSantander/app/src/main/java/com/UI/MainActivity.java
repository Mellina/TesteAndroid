package com.UI;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cerqueira.mellina.testeandroidsantander.R;

public class MainActivity extends FragmentActivity implements BotoesInferioresFragment.OnSetTitleListener, BotoesInferioresFragment.OnOpenFragmentListener, FormularioFragment.OnButtonSendListener {

    private TituloFragment tituloFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tituloFragment = (TituloFragment) getFragmentManager().findFragmentById(R.id.layout_titulo);

        FragmentManager fm = this.getSupportFragmentManager();

         FragmentTransaction ft = fm.beginTransaction();

        FormularioFragment lp = new FormularioFragment();
        ft.replace(R.id.layout_principal, lp, "layout_contato");

        ft.commit();
    }

    @Override
    public void onSetTitle(String text) {
        tituloFragment.setTitleFragment(text);
    }

    @Override
    public void onOpenFragment(String text) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (text.equals("Investimento")) {
            InvestimentoFragment inf = new InvestimentoFragment();
            ft.replace(R.id.layout_principal, inf, "layout_investimento");

            ft.commit();
        }
        if (text.equals("Contato")) {
            FormularioFragment lp = new FormularioFragment();
            ft.replace(R.id.layout_principal, lp, "layout_contato");
            ft.commit();
        }

    }

    @Override
    public void onSend() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MensagemEnviadaFragment menf = new MensagemEnviadaFragment();
        ft.replace(R.id.layout_principal, menf, "layout_mensagem_enviada");
        ft.commit();
    }
}
