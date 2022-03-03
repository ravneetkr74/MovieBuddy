package com.lambton.moviebuddy;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.lambton.moviebuddy.ui.Adapter.DrawerAdapter;
import com.lambton.moviebuddy.ui.BookTicketsFragment;
import com.lambton.moviebuddy.ui.DaoHelper;
import com.lambton.moviebuddy.ui.MapsFragment;
import com.lambton.moviebuddy.ui.Model.DrawerModel;
import com.lambton.moviebuddy.ui.Model.ImageConverter;
import com.lambton.moviebuddy.ui.Model.User;
import com.lambton.moviebuddy.ui.ProfileFragment;
import com.lambton.moviebuddy.ui.ReviewFragment;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    RecyclerView lst_menu_items;
    ImageView hamburger, setting;
    TextView txt_title,sub_title,name;
    List<DrawerModel> drawerModelList;
    CircleImageView main_image;
    DaoHelper daoHelper;
    Bitmap image;

    String title[] = {"Home", "Profile", "Payment","Movie Reviews"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        lst_menu_items = findViewById(R.id.lst_menu_items);
        hamburger = findViewById(R.id.hamburger);
        setting = findViewById(R.id.setting);
        txt_title = findViewById(R.id.txt_title);
        sub_title = findViewById(R.id.sub_title);
        name = findViewById(R.id.name);
        main_image = findViewById(R.id.main_img);
        txt_title.setText("Movie Buff");
        sub_title.setText("Find movie theatre nearby");
        lst_menu_items.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        drawerModelList = new ArrayList<>();
        daoHelper = DaoHelper.getInstance(this);
        MapsFragment fragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();


        for (int i = 0; i < title.length; i++) {

            DrawerModel mObj = new DrawerModel();
            mObj.setName(title[i]);
            if (i == 0) {
                mObj.setSelected(true);
            }
            drawerModelList.add(mObj);
        }
        DrawerAdapter adapter = new DrawerAdapter(this, drawerModelList) {
            @Override
            protected void onClickView(int pos) {
                selectItem(pos);
            }
        };

        lst_menu_items.setAdapter(adapter);

        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);

            }
        });
        checkProfile();

    }


    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {

            case 0:
                fragment = new MapsFragment();
                txt_title.setText("Movie Buff");
                sub_title.setVisibility(View.VISIBLE);
                sub_title.setText("Find movie theatre nearby");

                break;
            case 1:
                fragment = new ProfileFragment();
                sub_title.setVisibility(View.GONE);
                txt_title.setText("Profile");

                break;
            case 2:
                fragment = new BookTicketsFragment();
                txt_title.setText("Book Tickets");
                sub_title.setVisibility(View.GONE);


                break;
            case 3:
                fragment = new ReviewFragment();
                txt_title.setText("Review Movies");
                sub_title.setVisibility(View.GONE);


                break;
        }
        if (fragment != null)
            changeFragment(fragment);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void changeFragment(Fragment fragment) {


        if (fragment != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).
                    addToBackStack(fragment.getClass().getName()).commit();

        }
    }

    @Override
    public void onBackPressed() {

        if(getCurrentFrag() instanceof MapsFragment){
            finish();
        }
        else {
            changeFragment(new MapsFragment());
        }
    }

    public Fragment getCurrentFrag(){
        return getSupportFragmentManager().findFragmentById(R.id.frame_container);
    }


    private void checkProfile() {
        List<User> users = daoHelper.getUserInterface().getAll();
        if(users.size()!=0){
            User user = users.get(0);
            name.setText(user.getFirst_name());
            byte[] data = user.getUser_image();
            if (data != null) {
                image = ImageConverter.convertByteArray2Bitmap(data);
                main_image.setImageBitmap(image);

            }
        }


    }

}