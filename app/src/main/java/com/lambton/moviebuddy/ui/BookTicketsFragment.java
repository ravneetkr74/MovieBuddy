package com.lambton.moviebuddy.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Adapter.MoviesAdapter;
import com.lambton.moviebuddy.ui.Adapter.ReviewAdapter;
import com.lambton.moviebuddy.ui.Model.DrawerModel;
import com.lambton.moviebuddy.ui.Model.Tickets;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class BookTicketsFragment extends Fragment {

    EditText select_time;
    EditText quantity;
    RecyclerView movies;
    MoviesAdapter moviesAdapter;
    List<DrawerModel> drawerModelList;
    DaoHelper daoHelper;
    Button book;
    double lat=30.22;
    double lng=30.22;
    String name,time;
    int quant;

    String title[] = {"Mission Imposible", "Avengers", "Antman", "Mission Imposible","The Meg","Iron Man","Deadpool","Doctor Strange"};






    public BookTicketsFragment() {
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
        View view=inflater.inflate(R.layout.fragment_book_tickets, container, false);
        select_time=(EditText) view.findViewById(R.id.select_time);
        quantity=(EditText) view.findViewById(R.id.quantity);
        movies=(RecyclerView)view.findViewById(R.id.recyclerview);
        book=(Button) view.findViewById(R.id.book);

        daoHelper = DaoHelper.getInstance(getContext());
        select_time.setInputType(InputType.TYPE_NULL);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), select_time);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setGravity(Gravity.RIGHT);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        select_time.setText(item.getTitle());
                        time= (String) item.getTitle();
                        Toast.makeText(getActivity(),"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });

                popup.show();


        }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tickets tickets=new Tickets(name,time,quant,lat,lng);
                daoHelper.getTicketInterface().insert(tickets);
                MapsFragment mapsFragment=new MapsFragment();
                getFragmentManager().beginTransaction().replace(R.id.frame_container, mapsFragment).
                        addToBackStack(mapsFragment.getClass().getName()).commit();
            }
        });

        quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder mainDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.numberdialog, null);
                mainDialog.setView(dialogView);

                final Button cancel = (Button) dialogView.findViewById(R.id.cancel);
                final Button save = (Button) dialogView.findViewById(R.id.set);
                final NumberPicker np = (NumberPicker) dialogView.findViewById(R.id.numberPicker1);
                final AlertDialog alertDialog = mainDialog.create();
                alertDialog.show();
                np.setMaxValue(100);
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        Log.i("value is",""+i1);
                        quant = i1;
                    }
                });


                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        quantity.setText(String.valueOf(np.getValue()));
                        quant = np.getValue();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        drawerModelList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {

            DrawerModel mObj = new DrawerModel();
            mObj.setName(title[i]);
            if(i==0){
                mObj.setSelected(false);
            }
            drawerModelList.add(mObj);
        }
        movies.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        moviesAdapter = new MoviesAdapter(getContext(), drawerModelList) {
            @Override
            protected void onClickView(int pos) {
                         name=drawerModelList.get(pos).getName();
            }
        };

        movies.setAdapter(moviesAdapter);

        return view;
        }


}