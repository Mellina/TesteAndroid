package com.UI;

import android.support.annotation.NonNull;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.cerqueira.mellina.testeandroidsantander.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasBackground;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FormularioFragmentAndroidTest {

    //Ids vindos do JSON
    private static final int NOME = 2;
    private static final int TELEFONE = 6;
    private static final int EMAIL = 4;
    private static final int CHECKBOX_EMAIL = 3;
    private static final int BOTAO_ENVIAR = 7;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void TestBotaoEnviarSemEmail_TestePositivo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText("2134567890"), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click()); //clica no botao
        onView(withId(R.id.mensagemEnviadaFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void TestBotaoEnviarComEmail_TestePositivo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(4)).perform(typeText("mellina@gmail.com"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText("2134567890"), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click()); //clica no botao
        onView(withId(R.id.mensagemEnviadaFragment)).check(matches(isDisplayed()));
    }

    @Test
    public void TestBotaoEnviarNomeEmBranco_TesteNegativo(){

        onView(withId(NOME)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(4)).perform(typeText("mellina@gmail.com"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText("2134567890"), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click());
        onView(withId(R.id.mensagemEnviadaFragment)).check((doesNotExist()));
    }


    @Test
    public void TestBotaoEnviarEmailEmBranco_TesteNegativo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(4)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText("2134567890"), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click());
        onView(withId(R.id.mensagemEnviadaFragment)).check((doesNotExist()));
    }

    @Test
    public void TestBotaoEnviarTelefoneEmBranco_TesteNegativo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(4)).perform(typeText("mellina@gmail.com"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText(""), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click());
        onView(withId(R.id.mensagemEnviadaFragment)).check((doesNotExist()));
    }

    @Test
    public void TestBotaoEnviarEmailIncorreto_TesteNegativo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(4)).perform(typeText("mellinagmail.com"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText(""), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click());
        onView(withId(R.id.mensagemEnviadaFragment)).check((doesNotExist()));
    }

    @Test
    public void TestBotaoEnviarTelefonelIncorreto_TesteNegativo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText("33>%4"), closeSoftKeyboard());
        onView(withText("Enviar")).perform(click());
        onView(withId(R.id.mensagemEnviadaFragment)).check((doesNotExist()));
    }

    @Test
    public void TestCheckboxEmailEmailEmBranco_TesteNegativo(){

        onView(withId(NOME)).perform(typeText("Mellina"), closeSoftKeyboard());
        onView(withId(TELEFONE)).perform(typeText("2134567890"), closeSoftKeyboard());
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(EMAIL)).check(matches(isDisplayed()));
        onView(withText("Enviar")).perform(click());
        onView(withId(R.id.mensagemEnviadaFragment)).check((doesNotExist()));
    }

    @Test
    public void TestCheckboxEmailVisivel_TesteNegativo(){

        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(EMAIL)).check(matches(isDisplayed()));
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(EMAIL)).check(matches(not(isDisplayed())));
    }

    @Test
    public void TestCores_TesteNegativo(){

        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(EMAIL)).check(matches(isDisplayed()));
        onView(withId(CHECKBOX_EMAIL)).perform(click());
        onView(withId(EMAIL)).check(matches(not(isDisplayed())));
    }

}