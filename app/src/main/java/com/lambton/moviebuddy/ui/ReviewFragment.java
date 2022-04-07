package com.lambton.moviebuddy.ui;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lambton.moviebuddy.MainActivity;
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
    ImageView hamburger;
    MainActivity mainActivity;
    DrawerLayout drawer;
    TextView txt_title,sub_title;


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
        mainActivity=(MainActivity)getActivity();

        hamburger=mainActivity.findViewById(R.id.hamburger);
        drawer = mainActivity.findViewById(R.id.drawer_layout);
        txt_title = mainActivity.findViewById(R.id.txt_title);
        sub_title = mainActivity.findViewById(R.id.sub_title);
        sub_title.setVisibility(View.GONE);
        txt_title.setText("All Movies");
        hamburger.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_dehaze_24));
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);

            }
        });


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