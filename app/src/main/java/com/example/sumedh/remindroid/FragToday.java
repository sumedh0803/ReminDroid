package com.example.sumedh.remindroid;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragToday extends Fragment {

    RecyclerView rvrem,rvtodo,rvbday;
    DBHelper database;
    RecycleAdapterReminder recyclerRem;
    RecycleAdapterTodo recyclerTodo;
    RecycleAdapterBday recyclerBday;
    List<DataModel> datamodel1,datamodel2,datamodel3;
    TextView norem,nobday,notodo;
    SwipeRefreshLayout pullToRefresh;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.frag_today,container,false);

        pullToRefresh = view.findViewById(R.id.swipe_refresh_layout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayData(view,getContext());
                pullToRefresh.setRefreshing(false);
            }
        });

        displayData(view,getContext());
        return view;
    }

    public void displayData(View view, Context context)
    {
        datamodel1 =new ArrayList<DataModel>();
        datamodel2 =new ArrayList<DataModel>();
        datamodel3 =new ArrayList<DataModel>();

        norem = view.findViewById(R.id.norem);
        notodo = view.findViewById(R.id.notodo);
        nobday = view.findViewById(R.id.nobday);

        rvrem = view.findViewById(R.id.recycler_view_rem_today);
        rvtodo = view.findViewById(R.id.recycler_view_todo_today);
        rvbday = view.findViewById(R.id.recycler_view_bday_today);

        database = new DBHelper(context);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        String formattedDate = df.format(c);
        //Toast.makeText(getActivity(),formattedDate,Toast.LENGTH_SHORT).show();

        datamodel1 = database.getRemindersToday(formattedDate);
        datamodel2 = database.getTodoToday(formattedDate);
        datamodel3 = database.getBdayToday(formattedDate);

        if(datamodel1.isEmpty())
        {
            rvrem.setVisibility(View.GONE);
            norem.setVisibility(View.VISIBLE);
        }
        if(datamodel2.isEmpty())
        {
            rvtodo.setVisibility(View.GONE);
            notodo.setVisibility(View.VISIBLE);
        }
        if(datamodel3.isEmpty())
        {
            rvbday.setVisibility(View.GONE);
            nobday.setVisibility(View.VISIBLE);
        }

        recyclerRem = new RecycleAdapterReminder(datamodel1);
        recyclerTodo = new RecycleAdapterTodo(datamodel2);
        recyclerBday = new RecycleAdapterBday(datamodel3);

        LinearLayoutManager reLayoutManager1 = new LinearLayoutManager(getActivity());
        LinearLayoutManager reLayoutManager2 = new LinearLayoutManager(getActivity());
        LinearLayoutManager reLayoutManager3 = new LinearLayoutManager(getActivity());


        rvrem.setLayoutManager(reLayoutManager1);
        rvtodo.setLayoutManager(reLayoutManager2);
        rvbday.setLayoutManager(reLayoutManager3);

        rvrem.setItemAnimator(new DefaultItemAnimator());
        rvtodo.setItemAnimator(new DefaultItemAnimator());
        rvbday.setItemAnimator(new DefaultItemAnimator());

        rvrem.setAdapter(recyclerRem);
        rvtodo.setAdapter(recyclerTodo);
        rvbday.setAdapter(recyclerBday);

        pullToRefresh.setRefreshing(false);
    }
}
