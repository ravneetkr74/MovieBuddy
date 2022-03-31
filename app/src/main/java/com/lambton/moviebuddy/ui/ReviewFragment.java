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
import com.lambton.moviebuddy.ui.Model.MoviePojo;

import java.util.ArrayList;
import java.util.List;


public class ReviewFragment extends Fragment {

    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    List<MoviePojo> drawerModelList;

    String title[] = {"The Adam Project", "Blacklight", "The Batman", "Gold","Hotel Transylvania: Transformania","Desperate Riders","Pursuit","Uncharted","The Godfather"};
    Integer titleId[]={696806,823625,414906,760926,585083,928999,871799,335787,238};


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

            MoviePojo mObj = new MoviePojo();
            mObj.setName(title[i]);
            mObj.setId(titleId[i]);
            drawerModelList.add(mObj);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
       reviewAdapter = new ReviewAdapter(getContext(), drawerModelList) {
            @Override
            protected void onClickView(int pos) {

                MovieReviews mapsFragment=new MovieReviews();
                Bundle bundle = new Bundle();
                bundle.putInt("id", drawerModelList.get(pos).getId());
                mapsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, mapsFragment).
                        addToBackStack(mapsFragment.getClass().getName()).commit();

            }
        };

        recyclerView.setAdapter(reviewAdapter);
        return view;
    }
}