package com.lambton.moviebuddy.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Model.Tickets;

import java.util.List;

public class ShowAllTicketsAdapter extends RecyclerView.Adapter<ShowAllTicketsAdapter.ViewHolder> {
    public Context context;
    public List<Tickets> list;

    public ShowAllTicketsAdapter(Context context, List<Tickets> list) {
        this.context=context;
        this.list=list;

    }

    @NonNull
    @Override
    public ShowAllTicketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_reviewitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllTicketsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
