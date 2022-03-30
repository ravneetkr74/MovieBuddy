package com.lambton.moviebuddy.ui.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Model.DrawerModel;
import com.lambton.moviebuddy.ui.Model.MoviePojo;

import java.util.List;

public abstract class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    Context mContext;
    List<MoviePojo> mList;

    public MoviesAdapter(Context mContext, List<MoviePojo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MoviePojo mObj = mList.get(position);
        holder.txtTitle.setText(mObj.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               selectCurrItem(position);
                onClickView(position);
            }
        });

        if(mObj.isSelected())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.check.setImageDrawable(mContext.getDrawable(R.drawable.ic_baseline_radio_button_checked_24));
            }
        }
        else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.check.setImageDrawable(mContext.getDrawable(R.drawable.ic_baseline_radio_button_unchecked_24));
            }
        }

    }
    private void selectCurrItem(int position) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            if (i == position)
                mList.get(i).setSelected(true);
            else
                mList.get(i).setSelected(false);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        ImageView check;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.movie_name);
            check = (ImageView) itemView.findViewById(R.id.check);

        }
    }
    protected abstract void onClickView(int pos);

}
