package com.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cerqueira.mellina.testeandroidsantander.R;

import java.util.List;

public class MoreInfoAdapter extends RecyclerView.Adapter<MoreInfoAdapter.ItemHolder>  {

    private List<MoreInfo> moreInfos;
    private Context context;
    private static final int TITULO = 0;


    public MoreInfoAdapter(Context context, List<MoreInfo> moreInfos) {
        this.context = context;
            this.moreInfos = moreInfos;

    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_more_info, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        MoreInfo moreInfo = moreInfos.get(position);
        holder.txtPeriod.setText(moreInfo.getPeriod());
        if(position == TITULO){
            holder.txtFund.setText(String.valueOf(moreInfo.getFund()+""));
            holder.txtFund.setTextColor(context.getResources().getColor(R.color.gray));
            holder.txtCDI.setText(String.valueOf(moreInfo.getCDI()+""));
            holder.txtCDI.setTextColor(context.getResources().getColor(R.color.gray));
        }else{
            holder.txtFund.setText(String.valueOf(moreInfo.getFund()+"%"));
            holder.txtCDI.setText(String.valueOf(moreInfo.getCDI()+"%"));

        }

    }

    @Override
    public int getItemCount() {
        return moreInfos.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        TextView txtPeriod;
        TextView txtFund;
        TextView txtCDI;


        ItemHolder(View view) {
            super(view);
            txtPeriod = view.findViewById(R.id.txtPeriod);
            txtFund = view.findViewById(R.id.txtFund);
            txtCDI = view.findViewById(R.id.txtCDI);

        }

    }
}
