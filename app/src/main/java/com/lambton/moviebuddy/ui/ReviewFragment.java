package com.lambton.moviebuddy.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Adapter.DrawerAdapter;
import com.lambton.moviebuddy.ui.Adapter.ReviewAdapter;
import com.lambton.moviebuddy.ui.Model.DrawerModel;

import java.util.ArrayList;
import java.util.List;


public class ReviewFragment extends Fragment {

    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    List<DrawerModel> drawerModelList;

    String title[] = {"Mission Imposible", "Avengers", "Antman", "Mission Imposible","The Meg","Iron Man","Deadpool","Doctor Strange"};


    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        drawerModelList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {

            DrawerModel mObj = new DrawerModel();
            mObj.setName(title[i]);
            drawerModelList.add(mObj);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
       reviewAdapter = new ReviewAdapter(getContext(), drawerModelList) {
            @Override
            protected void onClickView(int pos) {

            }
        };

        recyclerView.setAdapter(reviewAdapter);
        return view;
    }
}