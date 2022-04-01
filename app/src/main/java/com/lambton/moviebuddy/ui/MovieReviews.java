package com.lambton.moviebuddy.ui;

import static android.text.Html.FROM_HTML_MODE_LEGACY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Adapter.MovieReviewAdapter;
import com.lambton.moviebuddy.ui.Interface.ApiClient;
import com.lambton.moviebuddy.ui.Interface.ApiInterface;
import com.lambton.moviebuddy.ui.Model.ReviewItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MovieReviews extends Fragment {

    RecyclerView recyclerView;
    MovieReviewAdapter movieReviewAdapter;
    List<ReviewItem> itemList;
    ApiInterface api;
    int id=0;

    public MovieReviews() {
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
        View view = inflater.inflate(R.layout.fragment_movie_reviews, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        api= ApiClient.apiInteface();
        id = getArguments().getInt("id");
        getReviews();
        return view;
    }


    public void getReviews() {

        api.getReviews(id,getResources().getString(R.string.ApiKey)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

//                    String stringresponse = null;
//                    try {
//                        stringresponse = response.body().string();
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        //mProgressBarHandler.hide();
//                        hideProgress();
//                    }

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());

                            int arrayLength = jsonObject.getJSONArray("results").length();
                            if (arrayLength == 0) {
                                Toast.makeText(getContext(),"Oops There is no item!",Toast.LENGTH_SHORT).show();
                            } else {
                                itemList = new ArrayList<>();
                                for (int i = 0; i < arrayLength; i++) {

                                    JSONObject object =jsonObject.getJSONArray("results").getJSONObject(i).getJSONObject("author_details");
                                    ReviewItem items = new ReviewItem();

                                    String rating="";
                                    if(object.get("rating")!=null) {
                                        rating = object.getString("rating");
                                    }
                                    String avatar_path = "";

                                    if (object.get("avatar_path") == null) {

                                    }else {
                                        avatar_path = object.getString("avatar_path");
                                    }
                                    String username = "";

                                    if (object.get("username") != null) {
                                        username = object.getString("username");
                                    }
                                    JSONObject jsonObject1 =jsonObject.getJSONArray("results").getJSONObject(i);
                                    String content = "";

                                    if (jsonObject1.get("content") != null) {
                                        content = jsonObject1.getString("content");
                                    }
                                    items.setAvatar_path(avatar_path);
                                    items.setRating(rating);
                                    items.setUsername(username);
                                    items.setContent(content);
                                    itemList.add(items);
                                }
                                movieReviewAdapter = new MovieReviewAdapter(getContext(), itemList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setAdapter(movieReviewAdapter);
                            }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();

                    }
                } else {

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}