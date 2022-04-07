package com.lambton.moviebuddy.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.showtickets_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllTicketsAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getMovie_name());
        holder.quantity.setText(""+list.get(position).getQuantity());
        holder.time.setText(""+list.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,quantity,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
