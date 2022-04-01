package com.lambton.moviebuddy.ui.Adapter;

import android.content.Context;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

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
        StringBuilder urlString = new StringBuilder(reviewItems.get(position).getAvatar_path());
        urlString.deleteCharAt(0);
        Log.w("IMAGES",reviewItems.get(position).getAvatar_path());
        Picasso.get()
                .load(urlString.toString())
                .resize(50, 50)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(holder.img);

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
    public  boolean contains(StringBuilder sb, String findString){

        /*
         * if the substring is found, position of the match is
         * returned which is >=0, if not -1 is returned
         */
        return sb.indexOf(findString) > -1;
    }
}
