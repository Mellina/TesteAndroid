package com.UI;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.view.ViewCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.AsyncTask.ReadContatoJSONTask;
import com.Type;
import com.TypeField;
import com.cerqueira.mellina.testeandroidsantander.R;
import com.entities.Componente;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.Type.*;


public class FormularioFragment extends android.support.v4.app.Fragment {

    private static final String URLCellsJSON = "https://floating-mountain-50292.herokuapp.com/cells.json";

    public static final boolean CRIA_COMPONENTE = true;

    private ConstraintLayout constraintLayout;
    private List<Componente> componentes;
    private Context context;
    private ReadContatoJSONTask task;

    private final List<View> componentesUI = new ArrayList<>();

    private OnButtonSendListener listener;
    private final TextWatcher onChangedListenerText = new TextWatcher() {

        int tamanho_anterior = 0;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            tamanho_anterior = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            adicionaMascaraTelefoneEdittext(s);
        }

        //Adiciona a mascara (xx)xxxx-xxxx e (xx)xxxxx-xxxx enquanto o usuario digita
        private void adicionaMascaraTelefoneEdittext(Editable s) {
            //
            if (tamanho_anterior < s.length()) {
                if (s.length() == 1) {
                    if (Character.isDigit(s.charAt(0))) {
                        s.insert(0, "(");
                    }
                } else if (s.length() == 3) {
                    s.append(")");
                } else if (s.length() == 8) {
                    s.append("-");
                } else if (s.length() > 13) {

                    if (Character.isDigit(s.charAt(9))) {
                        s.replace(8, 9, s.charAt(9) + "");
                    }
                    s.replace(9, 10, "-");

                }
            }
        }

    };
    private final View.OnClickListener onClickListenerButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (entradaDeDadosEValidaEnviar()) {
                if (listener != null) {
                    listener.onSend();
                }
            }
        }
    };

    private final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            boolean ATUALIZA_COMPONENTE = false;
            if (isChecked) {

                for (int i = 0; i < componentesUI.size(); i++) {
                    int id = componentesUI.get(i).getId();
                    if (id == 4) {
                        EditText ed = (EditText) componentesUI.get(i);
                        componentes.get(i).setHidden(false);
                        ed.setVisibility(View.VISIBLE);
                        criaEAtualizaDinamicamenteComponentesNaTela(componentes, ATUALIZA_COMPONENTE);
                    }
                }
            } else {
                for (int i = 0; i < componentesUI.size(); i++) {
                    int id = componentesUI.get(i).getId();
                    if (id == 4) {
                        componentes.get(i).setHidden(true);
                        EditText ed = (EditText) componentesUI.get(i);
                        ed.setVisibility(View.INVISIBLE);
                        criaEAtualizaDinamicamenteComponentesNaTela(componentes, ATUALIZA_COMPONENTE);
                    }
                }
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //pega o Contexto da Activity onde o Fragment foi iniciado
        context = getActivity();

        View view = criaERetornaConstraintLayout();

        if (estaConectadoNaInternet()) {
            //Inicia a leitura do JSON
            task = new ReadContatoJSONTask(this);
            task.execute(URLCellsJSON);
        }

        return view;
    }

    private boolean estaConectadoNaInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private View criaERetornaConstraintLayout() {
        constraintLayout = new ConstraintLayout(context);
        constraintLayout.setId(R.id.idFragmentFormulario);
        constraintLayout.setBackgroundColor(context.getResources().getColor(android.R.color.white));

        constraintLayout.setPadding(context.getResources().getInteger(R.integer.padding_lateral), 0, context.getResources().getInteger(R.integer.padding_lateral), 0);
        return constraintLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null) {
            // Cancela a AsyncTask responsavel pela leitura do JSON quando a tela é destruida
            task.cancel(true);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Verifica se a Activity implementou a interface do ButtonSend
        if (!(activity instanceof OnButtonSendListener)) {
            throw new RuntimeException("A activity deve implementar a interface FormularioFragment.OnButtonSendListener");
        }
        //Atribui a activity ao listener do ButtonSend
        listener = (OnButtonSendListener) activity;
    }

    public void criaObjetosComponente(List<Componente> componentes, Componente p, JSONObject cell) throws JSONException {
        p.setId(cell.getInt("id"));
        p.setType(cell.getInt("type"));
        if (cell.getString("message") != null)
            p.setMessage(cell.getString("message"));
        if (cell.getString("typefield") != null)
            p.setTypefield(cell.getString("typefield"));
        p.setHidden(cell.getBoolean("hidden"));
        p.setTopSpacing(cell.getDouble("topSpacing"));
        if (cell.getString("show") != null)
            p.setShow(cell.getString("show"));
        p.setRequired(cell.getBoolean("required"));
        componentes.add(p);
    }

    public void criaEAtualizaDinamicamenteComponentesNaTela(List<Componente> s, boolean criaComponente) {

        for (int i = 0; i < s.size(); i++) {

            Componente c = s.get(i);
            int idComponenteAtual = c.getId();
            int idComponenteAnterior = constraintLayout.getId();
            if (idComponenteAtual != 1) {

                //pega o id do ultimo componente que não está escondido
                if (s.get(i - 1).isHidden()) {
                    idComponenteAnterior = s.get(i - 2).getId();
                } else {
                    idComponenteAnterior = s.get(i - 1).getId();
                }
            }

            if (criaComponente) {
                criaComponentesDeAcordoComOTipo(c);
            }

            adicionaComponentesNoLayout(c, idComponenteAtual, idComponenteAnterior);
        }

    }

    private void criaComponentesDeAcordoComOTipo(Componente c) {

        Type tipo = Type.returnaEnumPorValor(c.getType());

        switch (tipo) {
            case FIELD:
                criaComponenteField(c);
                break;
            case TEXT:
                criaComponenteText(c);
                break;
            case CHECKBOX:
                criaComponenteCheckBox(c);
                break;
            case BUTTON:
                criaComponenteButton(c);
                break;
            default:
                break;
        }
    }

    private void criaComponenteButton(Componente c) {
        ConstraintLayout.LayoutParams clpcontactUs;
        Button button = new Button(context);
        button.setText(c.getMessage());
        button.setOnClickListener(onClickListenerButton);
        button.setId(Integer.parseInt(String.valueOf(c.getId())));
        clpcontactUs = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(clpcontactUs);
        constraintLayout.addView(button);
        componentesUI.add(button);
    }

    private void criaComponenteCheckBox(Componente c) {
        ConstraintLayout.LayoutParams clpcontactUs;
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(c.getMessage());
        checkBox.setId(Integer.parseInt(String.valueOf(c.getId())));
        checkBox.setTextAppearance(context, R.style.TextViewDarkGrayBold);


        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        clpcontactUs = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        checkBox.setLayoutParams(clpcontactUs);
        constraintLayout.addView(checkBox);
        componentesUI.add(checkBox);
    }

    private void criaComponenteText(Componente c) {
        ConstraintLayout.LayoutParams clpcontactUs;
        TextView textView = new TextView(context);
        textView.setText(c.getMessage());
        textView.setId(Integer.parseInt(String.valueOf(c.getId())));
        textView.setTextAppearance(context, R.style.TextViewDarkGrayBold);
        clpcontactUs = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(clpcontactUs);
        constraintLayout.addView(textView);
        componentesUI.add(textView);
    }

    private void criaComponenteField(Componente c) {
        ConstraintLayout.LayoutParams clpcontactUs;
        EditText editText = new EditText(context);
        editText.setHint(c.getMessage());
        editText.setId(Integer.parseInt(String.valueOf(c.getId())));
        if (c.isHidden()) {
            editText.setVisibility(View.INVISIBLE);
        }

        if (c.getTypefield() != null) {
            editText.setInputType(retornaInputType(c));
        }

        if (c.getTypefield().equals(TypeField.TELNUMBER)) {
            //adiciona listener para acessar texto durante a digitacao do usuario
            editText.addTextChangedListener(onChangedListenerText);
            //adiciona restricao de no maximo 14 digitos
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(14)});
        }
        clpcontactUs = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        editText.setLayoutParams(clpcontactUs);
        constraintLayout.addView(editText);
        componentesUI.add(editText);
    }

    private void adicionaComponentesNoLayout(Componente c, int idComponenteAtual, int idComponenteAnterior) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        if (idComponenteAtual == 1) {
            constraintSet.connect(idComponenteAtual, ConstraintSet.TOP, idComponenteAnterior, ConstraintSet.TOP, (int) c.getTopSpacing());
        } else {
            constraintSet.connect(idComponenteAtual, ConstraintSet.TOP, idComponenteAnterior, ConstraintSet.BOTTOM, (int) c.getTopSpacing());
        }
        constraintSet.connect(idComponenteAtual, ConstraintSet.LEFT, constraintLayout.getId(), ConstraintSet.LEFT, 0);
        constraintSet.connect(idComponenteAtual, ConstraintSet.RIGHT, constraintLayout.getId(), ConstraintSet.RIGHT, 0);

        constraintSet.applyTo(constraintLayout);
    }

    private boolean entradaDeDadosEValidaEnviar() {

        boolean dadosInvalidos = false;
        String textoDigitado;
        EditText ed;

        for (int i = 0; i < componentesUI.size(); i++) {

           TypeField typeField =  TypeField.returnaEnumPorValor(componentes.get(i).getTypefield());
            switch (typeField) {

                case TEXT: {
                    ed = (EditText) componentesUI.get(i);
                    textoDigitado = String.valueOf(ed.getText());

                    //verifica se o campo de texto esta vazio
                    if (textoDigitado.trim().isEmpty()) {
                        //altera a cor do edittext para vermelho
                        ViewCompat.setBackgroundTintList(ed, getResources().getColorStateList(R.color.red));
                        dadosInvalidos = true;
                    } else {
                        //altera a cor do edittext para verde
                        ViewCompat.setBackgroundTintList(ed, getResources().getColorStateList(R.color.green));
                    }
                    break;
                }
                case TELNUMBER: {
                    ed = (EditText) componentesUI.get(i);
                    textoDigitado = String.valueOf(ed.getText());
                    //verifica se o texto esta no formato de telefone
                    if (!ENumeroDeTelefone(textoDigitado)) {
                        //altera a cor do edittext para vermelho
                        ViewCompat.setBackgroundTintList(ed, getResources().getColorStateList(R.color.red));
                        dadosInvalidos = true;
                    } else {
                        //altera a cor do edittext para verde
                        ViewCompat.setBackgroundTintList(ed, getResources().getColorStateList(R.color.green));
                    }
                    break;
                }
                case EMAIL: {
                    ed = (EditText) componentesUI.get(i);
                    //verifica se o campo email esta visivel
                    if (ed.getVisibility() == View.VISIBLE) {
                        textoDigitado = String.valueOf(ed.getText());
                        //verifica se o texto digitado esta no formato de email
                        if (!EEmail(textoDigitado)) {
                            //altera a cor do edittext para vermelho
                            ViewCompat.setBackgroundTintList(ed, getResources().getColorStateList(R.color.red));
                            dadosInvalidos = true;
                        } else {
                            //altera a cor do edittext para verde
                            ViewCompat.setBackgroundTintList(ed, getResources().getColorStateList(R.color.green));
                        }
                    }
                    break;
                }
            }
        }

        return !dadosInvalidos;
    }

    //Expressão regular para (##) ####-#### || (##) #####-####
    boolean ENumeroDeTelefone(String numeroTelefone) {
        return numeroTelefone.matches("\\(\\d\\d\\)\\d\\d\\d\\d\\-\\d\\d\\d\\d") ||
                numeroTelefone.matches("\\(\\d\\d\\)\\d\\d\\d\\d\\d\\-\\d\\d\\d\\d");
    }

    //Expressão regular para xxx@xxx.xxx
    boolean EEmail(String email) {
        return email.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    private int retornaInputType(Componente c) {

        String inputType = c.getTypefield();
        if (inputType.equals(TypeField.TEXT))
            return InputType.TYPE_CLASS_TEXT;

        if (inputType.equals(TypeField.TELNUMBER))
            return InputType.TYPE_CLASS_PHONE;

        if (inputType.equals(TypeField.EMAIL))
            return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;

        return InputType.TYPE_CLASS_TEXT;
    }

    public List<Componente> getComponentes() {
        return componentes;
    }

    public void setComponentes(ArrayList<Componente> componentes) {
        this.componentes = componentes;
    }

    //Interface criada para a activity implementar o botao enviar
    public interface OnButtonSendListener {
        void onSend();
    }
}
