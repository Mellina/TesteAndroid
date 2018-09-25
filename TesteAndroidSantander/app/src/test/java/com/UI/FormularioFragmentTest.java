package com.UI;

import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class FormularioFragmentTest {

    static FormularioFragment formularioFragment;

    @BeforeClass
    public static void instanciaFormularioFragment() {
        formularioFragment = new FormularioFragment();
    }

    @Test
    public void testEEmail_valido() {

        assertTrue(formularioFragment.EEmail("mellina@gmail.com"));
        assertTrue(formularioFragment.EEmail("m@gmail.com.br"));
        assertTrue(formularioFragment.EEmail("mellina_carvalho@gmail.com"));
        assertTrue(formularioFragment.EEmail("mellina.cerqueira@ibm.com"));
    }

    @Test
    public void testEEmail_invalido() {

        assertFalse(formularioFragment.EEmail("mellinagmail.com"));
        assertFalse(formularioFragment.EEmail("m@gmailcombr"));
        assertFalse(formularioFragment.EEmail("mellina_carvalho@gmail"));
    }

    @Test
    public void testENumeroDeTelefone_valido() {

        assertTrue(formularioFragment.ENumeroDeTelefone("(21)3434-3434"));
    }
}