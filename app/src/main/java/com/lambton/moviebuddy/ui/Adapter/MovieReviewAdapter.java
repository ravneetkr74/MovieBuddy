package com.lambton.moviebuddy.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Model.ImageConverter;
import com.lambton.moviebuddy.ui.Model.ReviewItem;

import java.util.List;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    public Context context;
    public List<ReviewItem> reviewItems;

    public MovieReviewAdapter(Context context, List<ReviewItem> reviewItems) {
        this.context = context;
        this.reviewItems = reviewItems;
    }

    @NonNull
    @Override
    public MovieReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_reviewitem,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapter.ViewHolder holder, int position) {
        holder.author_name.setText(reviewItems.get(position).getUsername());
        holder.rating.setText(""+reviewItems.get(position).getRating());
        holder.review.setText(reviewItems.get(position).getContent());
       // holder.img.setImageBitmap(ImageConverter.convertByteArray2Bitmap(reviewItems.get(position).getAvatar_path()));

    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author_name,rating,review;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author_name = (TextView) itemView.findViewById(R.id.author_name);
            rating = (TextView) itemView.findViewById(R.id.author_rating);
            review = (TextView) itemView.findViewById(R.id.author_review);
            img = (ImageView) itemView.findViewById(R.id.profile_img);


        }
    }
}
