package com.example.sumedh.remindroid;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragBday extends Fragment {

    RecyclerView rv;
    DBHelper database;
    RecycleAdapterBday recycler;
    List<DataModel> datamodel;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_bday,container,false);

        datamodel =new ArrayList<DataModel>();
        rv = view.findViewById(R.id.recycler_view);
        database = new DBHelper(getContext());
        datamodel = database.getBday();
        recycler =new RecycleAdapterBday(datamodel);
        LinearLayoutManager reLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(reLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(recycler);

        return view;
    }
}
