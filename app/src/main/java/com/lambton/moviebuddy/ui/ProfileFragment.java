package com.lambton.moviebuddy.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lambton.moviebuddy.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
CircleImageView profile_img;
EditText first_name,last_name;
Button save;

    public ProfileFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void selectedImage(String imagePath, String type, String thumbnailPath) {
//
//       // Picasso.get().load(new File(imagePath)).into(profile_img);
//
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        // Inflate the layout for this fragment
        profile_img=(CircleImageView)view.findViewById(R.id.profile_img);
        first_name=(EditText) view.findViewById(R.id.first_name);
        last_name=(EditText) view.findViewById(R.id.last_name);
//        profile_img.setOnClickListener(new View.OnClickListener() {
//            @Override
////            public void onClick(View view) {
////                gallery(getActivity());
////            }
//        });

        return view;
    }
}