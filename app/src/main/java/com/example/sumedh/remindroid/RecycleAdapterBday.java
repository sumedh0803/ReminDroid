package com.example.sumedh.remindroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class RecycleAdapterBday extends RecyclerView.Adapter<RecycleAdapterBday.Myholder> {

    List<DataModel> dataModelArrayList;

    public RecycleAdapterBday(List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView name,dt;

        public Myholder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            dt = itemView.findViewById(R.id.dt);
        }
    }

    @NonNull
    public RecycleAdapterBday.Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bday_row,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new RecycleAdapterBday.Myholder(view);
    }

    @Override
    public void onBindViewHolder(RecycleAdapterBday.Myholder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);
        holder.name.setText(dataModel.getBdayfname()+" "+dataModel.getBdaylname());
        holder.dt.setText(dataModel.getBdayDate());
    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }


}
