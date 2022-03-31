package com.lambton.moviebuddy.ui.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Model.DrawerModel;
import com.lambton.moviebuddy.ui.Model.MoviePojo;

import java.util.List;

public abstract class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context mContext;
    List<MoviePojo> mList;

    public ReviewAdapter(Context mContext, List<MoviePojo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        MoviePojo mObj = mList.get(position);
        holder.txtTitle.setText(mObj.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickView(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.movie_name);
        }
    }
    protected abstract void onClickView(int pos);

}
