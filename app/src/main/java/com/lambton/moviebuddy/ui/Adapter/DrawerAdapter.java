package com.lambton.moviebuddy.ui.Adapter;

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

import java.util.List;

public abstract class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    Context mContext;
    List<DrawerModel> mList;

    public DrawerAdapter(Context mContext, List<DrawerModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerAdapter.ViewHolder holder, int position) {
        DrawerModel mObj = mList.get(position);
        holder.txtTitle.setText(mObj.getName());


        if(mObj.isSelected())
        {
            holder.txtTitle.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        }
        else {

            holder.txtTitle.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectCurrItem(position);
                onClickView(position);
            }
        });



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
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.item_name);

        }
    }

    protected abstract void onClickView(int pos);

}
