package com.lambton.moviebuddy.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Adapter.MovieReviewAdapter;
import com.lambton.moviebuddy.ui.Adapter.ReviewAdapter;
import com.lambton.moviebuddy.ui.Adapter.ShowAllTicketsAdapter;
import com.lambton.moviebuddy.ui.Model.Tickets;

import java.util.List;


public class ShowAllTickets extends Fragment {

    RecyclerView recyclerView;
    ShowAllTicketsAdapter showAllTicketsAdapter;
    List<Tickets> list;
    ImageView hamburger;
    DaoHelper daoHelper;
    public ShowAllTickets() {
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
        View view=inflater.inflate(R.layout.fragment_show_all_tickets, container, false);
        hamburger=(ImageView) view.findViewById(R.id.hamburger);
        hamburger.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_back_24));
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookTicketsFragment bookTicketsFragment=new BookTicketsFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_container, bookTicketsFragment).
                        addToBackStack(bookTicketsFragment.getClass().getName()).commit();
            }
        });
        daoHelper = DaoHelper.getInstance(getContext());
           list=daoHelper.getTicketInterface().getAll();
        showAllTicketsAdapter = new ShowAllTicketsAdapter(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(showAllTicketsAdapter);
        return view;
    }
}