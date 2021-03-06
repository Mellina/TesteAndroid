package com.UI;


import android.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.AsyncTask.ReadInvestimentoJSONTask;
import com.adapters.Info;
import com.adapters.InfoAdapter;
import com.adapters.MoreInfo;
import com.adapters.MoreInfoAdapter;
import com.cerqueira.mellina.testeandroidsantander.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InvestimentoFragment extends android.support.v4.app.Fragment {

    private static final String URLFundJson = "https://floating-mountain-50292.herokuapp.com/fund.json";
    private Context context;

    private TextView title;
    private TextView fundName;
    private TextView whatIs;
    private TextView definition;
    private TextView riskTitle;
    private ImageView imageRisk;
    private TextView infoTitle;
    private Button btnInvestir;
    private View barraDivider;
    private View barraDivider2;
    private RecyclerView recyclerViewMoreInfo;
    private RecyclerView recyclerViewInfo;

    private List<MoreInfo> moreInfo;
    private List<Info> info;
    private ReadInvestimentoJSONTask task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_investimento, container, false);
        context = getActivity();

        adicionaComponentesdoLayoutXML(view);
        alteraVisibilidadeComponentes(View.INVISIBLE);

        inicializaLists();

        if (estaConectadoNaInternet()) {
            //Inicia a leitura do JSON
            task = new ReadInvestimentoJSONTask(this);
            task.execute(URLFundJson);

            alteraVisibilidadeComponentes(View.VISIBLE);
        }
        return view;
    }

    private boolean estaConectadoNaInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    private void inicializaLists() {
        moreInfo = new ArrayList<>();
        info = new ArrayList<>();
    }

    private void alteraVisibilidadeComponentes(int visible) {
        btnInvestir.setVisibility(visible);
        barraDivider.setVisibility(visible);
        barraDivider2.setVisibility(visible);
        imageRisk.setVisibility(visible);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (task != null) {
            // Cancela a AsyncTask responsavel pela leitura do JSON quando a tela é destruida
            task.cancel(true);
        }

    }

    private void adicionaComponentesdoLayoutXML(View view) {
        title = view.findViewById(R.id.txtTitle);
        fundName = view.findViewById(R.id.txtfundName);
        whatIs = view.findViewById(R.id.txtWhatIs);
        definition = view.findViewById(R.id.txtDefinition);
        riskTitle = view.findViewById(R.id.txtRiskTitle);
        imageRisk = view.findViewById(R.id.imageRisk);
        infoTitle = view.findViewById(R.id.txtInfoTitle);
        recyclerViewMoreInfo = view.findViewById(R.id.recycler_view);
        recyclerViewInfo = view.findViewById(R.id.recycler_view_info);
        btnInvestir = view.findViewById(R.id.btnInvestir);
        barraDivider = view.findViewById(R.id.divider);
        barraDivider2 = view.findViewById(R.id.divider2);
        recyclerViewMoreInfo.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewInfo.setLayoutManager(new LinearLayoutManager(context));

    }

    public void preencheRecyclerListViewComInfoMoreInfo(List<MoreInfo> moreInfos, List<Info> infos) {
        MoreInfoAdapter adapter = new MoreInfoAdapter(context, moreInfos);
        recyclerViewMoreInfo.setAdapter(adapter);

        InfoAdapter adapterInfo = new InfoAdapter(context, infos);
        recyclerViewInfo.setAdapter(adapterInfo);
    }

    public void preencheArrayMoreInfoJSON(Map<String, String> textosIniciais, JSONObject screenJSON) throws JSONException {
        preencheMapaTextosJSON(textosIniciais, screenJSON);

        JSONObject moreInfoJSON = screenJSON.getJSONObject("moreInfo");
        JSONObject monthJSON = moreInfoJSON.getJSONObject("month");
        JSONObject yearJSON = moreInfoJSON.getJSONObject("year");
        JSONObject months12JSON = moreInfoJSON.getJSONObject("12months");

        moreInfo.add(new MoreInfo("", "Fund", "CDI"));
        moreInfo.add(new MoreInfo(getString(R.string.month), String.valueOf(monthJSON.get("fund")), String.valueOf(monthJSON.get("CDI"))));
        moreInfo.add(new MoreInfo(getString(R.string.year), String.valueOf(yearJSON.get("fund")), String.valueOf(yearJSON.get("CDI"))));
        moreInfo.add(new MoreInfo(getString(R.string.months12), String.valueOf(months12JSON.get("fund")), String.valueOf(months12JSON.get("CDI"))));
    }

    public void preencheArrayInfoDownInfoJSON(JSONObject screenJSON) throws JSONException {
        JSONArray infoJSON = screenJSON.getJSONArray("info");

        for (int i = 0; i < infoJSON.length(); i++) {
            JSONObject infoObject = infoJSON.getJSONObject(i);
            info.add(new Info(String.valueOf(infoObject.get("name")), String.valueOf(infoObject.get("data"))));
        }

        JSONArray downinfoJSON = screenJSON.getJSONArray("downInfo");

        for (int i = 0; i < downinfoJSON.length(); i++) {
            JSONObject infoObject = downinfoJSON.getJSONObject(i);
            info.add(new Info(String.valueOf(infoObject.get("name")), String.valueOf(infoObject.get("data"))));
        }
    }

    private void preencheMapaTextosJSON(Map<String, String> textosIniciais, JSONObject screenJSON) throws JSONException {
        textosIniciais.put("title", String.valueOf(screenJSON.get("title")));
        textosIniciais.put("fundName", String.valueOf(screenJSON.get("fundName")));
        textosIniciais.put("whatIs", String.valueOf(screenJSON.get("whatIs")));
        textosIniciais.put("definition", String.valueOf(screenJSON.get("definition")));
        textosIniciais.put("riskTitle", String.valueOf(screenJSON.get("riskTitle")));
        textosIniciais.put("risk", String.valueOf(screenJSON.get("risk")));
        textosIniciais.put("infoTitle", String.valueOf(screenJSON.get("infoTitle")));
    }

    public void adicionaInformacoesIniciaisNaTela(Map<String, String> textosIniciais) {
        title.setText(textosIniciais.get("title"));
        fundName.setText(textosIniciais.get("fundName"));
        whatIs.setText(textosIniciais.get("whatIs"));
        definition.setText(textosIniciais.get("definition"));
        definition.setText(textosIniciais.get("definition"));
        riskTitle.setText(textosIniciais.get("riskTitle"));
        adicionaImagemRiskNaTela(Integer.parseInt(textosIniciais.get("risk")));
        infoTitle.setText(textosIniciais.get("infoTitle"));
    }

    private void adicionaImagemRiskNaTela(int risk) {

        switch (risk) {
            case 1:
                imageRisk.setImageDrawable(context.getResources().getDrawable(R.drawable.risk_1));
                break;
            case 2:
                imageRisk.setImageDrawable(context.getResources().getDrawable(R.drawable.risk_2));
                break;
            case 3:
                imageRisk.setImageDrawable(context.getResources().getDrawable(R.drawable.risk_3));
                break;
            case 4:
                imageRisk.setImageDrawable(context.getResources().getDrawable(R.drawable.risk_4));
                break;
            case 5:
                imageRisk.setImageDrawable(context.getResources().getDrawable(R.drawable.risk_5));
                break;
        }

    }

    public List<MoreInfo> getMoreInfo() {
        return moreInfo;
    }

    public List<Info> getInfo() {
        return info;
    }
}
