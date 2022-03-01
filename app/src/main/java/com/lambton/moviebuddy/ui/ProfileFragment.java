package com.lambton.moviebuddy.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Model.ImageConverter;
import com.lambton.moviebuddy.ui.Model.Tickets;
import com.lambton.moviebuddy.ui.Model.User;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {
CircleImageView profile_img;
EditText first_name,last_name;
private static final int CAMERA_REQUEST = 102;
private static final int GALLERY_REQUEST = 101;
private static final int REQUEST_CODE = 1;
Button save;
Bitmap image;
DaoHelper daoHelper;
Boolean from=false;

    public ProfileFragment() {
        // Required empty public constructor
    }




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
        save=(Button) view.findViewById(R.id.save);
        daoHelper = DaoHelper.getInstance(getContext());
        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  openAttachments();
            }
        });
        if(from){
            getUpdatedUser();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(from){
                    List<User> userList = daoHelper.getUserInterface().getAll();
                        User user = userList.get(0);
                        user.setFirst_name(first_name.getText().toString());
                        user.setLast_name(last_name.getText().toString());
                        if(image != null){
                            user.setUser_image(ImageConverter.convertImage2ByteArray(image));
                        }
                        daoHelper.getUserInterface().update(user);

                }else {
                    User user = new User(first_name.getText().toString(), last_name.getText().toString(), ImageConverter.convertImage2ByteArray(image));
                    daoHelper.getUserInterface().insert(user);
                    from=true;
                }
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {
            image = (Bitmap) data.getExtras().get("data");
            profile_img.setImageBitmap(image);

        }
        else if(reqCode == GALLERY_REQUEST && resultCode == RESULT_OK){

            Uri uri = data.getData();

                try {
                    image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    profile_img.setImageBitmap(image);
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        else{

        }
    }

    private void getUpdatedUser() {
        List<User> notes = daoHelper.getUserInterface().getAll();
            User user = notes.get(0);
            first_name.setText(user.getFirst_name());
           last_name.setText(user.getLast_name());
            byte[] data = user.getUser_image();
            if (data != null) {
                image = ImageConverter.convertByteArray2Bitmap(data);
                profile_img.setImageBitmap(image);
            }


    }
    public void openAttachments() {

        final CharSequence[] items = { "Camera", "Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Attachment");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Camera")) {
                    CaptureImage();
                } else if (items[item].equals("Gallery")) {
                    OpenGallery();
                }
                else if(items[item].equals("Cancel")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }


    public void CaptureImage() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    public void OpenGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }
}