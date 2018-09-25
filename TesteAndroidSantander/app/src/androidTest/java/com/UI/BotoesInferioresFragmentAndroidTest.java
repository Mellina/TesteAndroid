package com.UI;


import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.cerqueira.mellina.testeandroidsantander.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BotoesInferioresFragmentAndroidTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //@Rule
    //  public FragmentTestRule<?, InvestimentoFragment> mInvestimentoFragmentTestRule = new FragmentTestRule<>(MainActivity.class, InvestimentoFragment.class);

    //  @Rule
    //  public FragmentTestRule<?, FormularioFragment> mFormularioFragmentTestRule = new FragmentTestRule<>(MainActivity.class, FormularioFragment.class);


    /*@Before
    public void init(){
         mainActivityActivityTestRule.getActivity().getFragmentManager().beginTransaction();
    }*/


    @Test
    public void TestBotaoContato(){
        onView(withId(R.id.btnContato)).check(matches(isDisplayed())); //checa se a view esta visivel
        onView(withId(R.id.btnContato)).perform(click()); //clica no botao
        onView(withId(R.id.fragmentFormulario)).check(matches(isDisplayed())); //Verifica se o fragment do formulario esta sendo exibido
    }

   @Test
    public void TestBotaoInvestimento(){
        onView(withId(R.id.btnInvestimento)).check(matches(isDisplayed()));
        onView(withId(R.id.btnInvestimento)).perform(click());
        onView(withId(R.id.fragmentInvestimento)).check(matches(isDisplayed()));
    }
}