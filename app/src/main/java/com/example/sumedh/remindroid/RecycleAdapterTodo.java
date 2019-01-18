package com.example.sumedh.remindroid;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class RecycleAdapterTodo extends RecyclerView.Adapter<RecycleAdapterTodo.Myholder> {
    List<DataModel> dataModelArrayList;

    public RecycleAdapterTodo(List<DataModel> dataModelArrayList) {
        this.dataModelArrayList = dataModelArrayList;
    }

    class Myholder extends RecyclerView.ViewHolder{
        TextView title,dt,time,checked;

        public Myholder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            dt = (TextView) itemView.findViewById(R.id.dt);
            time = (TextView) itemView.findViewById(R.id.time);
            checked = (TextView) itemView.findViewById(R.id.checked);
        }
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_todo,null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(Myholder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);
        holder.title.setText(dataModel.getTodoTitle());
        holder.dt.setText(dataModel.getTodoDate());
        holder.time.setText(dataModel.getTodoTime());
        int checkStat = dataModel.getTodoChecked();
        CharSequence cs = null;
        if(checkStat == 1)
        {
            cs = "Reminder: Set";
        }
        else
        {
            cs = "Reminder: Not Set";
        }
        holder.checked.setText(cs);

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }
}
