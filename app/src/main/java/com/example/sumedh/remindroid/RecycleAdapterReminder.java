package com.example.sumedh.remindroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class RecycleAdapterReminder extends RecyclerView.Adapter<RecycleAdapterReminder.Myholder> {
    List<DataModel> dataModelArrayList;
    CardView remCard;

    public RecycleAdapterReminder(List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }



    class Myholder extends RecyclerView.ViewHolder{
        TextView title,dt,time;
        CardView remCard;


        public Myholder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            dt = itemView.findViewById(R.id.dt);
            time = itemView.findViewById(R.id.time);
            remCard = itemView.findViewById(R.id.remCard);

        }
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_row,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);
        holder.title.setText(dataModel.getRemTitle());
        holder.dt.setText(dataModel.getRemDate());
        holder.time.setText(dataModel.getRemTime());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }




}
