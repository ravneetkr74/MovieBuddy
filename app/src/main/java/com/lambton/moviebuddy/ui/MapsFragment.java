package com.lambton.moviebuddy.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.lambton.moviebuddy.MainActivity;
import com.lambton.moviebuddy.R;
import com.lambton.moviebuddy.ui.Model.ImageConverter;
import com.lambton.moviebuddy.ui.Model.JsonParser;
import com.lambton.moviebuddy.ui.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MapsFragment extends Fragment {

    FusedLocationProviderClient client;
    Location userlocation;
    SupportMapFragment smf;
    GoogleMap mymap;
    Button locate_me;
    TextView name;
    MainActivity mainActivity;
    CircleImageView main_img;
    DaoHelper daoHelper;
    Bitmap image;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            mymap=googleMap;


        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        locate_me=(Button)view.findViewById(R.id.locate_me);
        mainActivity=(MainActivity)getActivity();
        main_img = mainActivity.findViewById(R.id.main_img);
        name = mainActivity.findViewById(R.id.name);
        daoHelper = DaoHelper.getInstance(getContext());

        client = LocationServices.getFusedLocationProviderClient(requireActivity());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{(Manifest.permission.ACCESS_FINE_LOCATION)},
                    101);
        }
        locate_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                        "location="+userlocation.getLatitude()+","+userlocation.getLongitude()+
                        "&radius=10000" +
                        "&keyword=movie+theater" +  //with hospital it's working
                        "&sensor=true" +
                        "&key="+getResources().getString(R.string.google_maps_key);

                new PlaceTask().execute(url);
            }
        });

        checkProfile();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       smf = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (smf != null) {
            smf.getMapAsync(callback);

        }


    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    userlocation=location;

                    LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

                    mymap.addMarker(new MarkerOptions().position(sydney).title("Here I am").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    mymap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    mymap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15.5f),4000,null);

                    Toast.makeText(getContext(),"Location Found !!!",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getContext(),"Please Enable GPS And Internet !!!",Toast.LENGTH_LONG).show();
            }
        });

    }

    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data=null;
            try {
                 data=downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParserTask().execute(s);
        }
    }


    private String downloadUrl(String string) throws IOException {
        //Initialize url
        URL ul=new URL(string);
        //Initialize connection
        HttpURLConnection connection=(HttpURLConnection)ul.openConnection();
        //connect connetion
        connection.connect();
        //Initialize inputstream
        InputStream inputStream=connection.getInputStream();
        //Initialize buffer reader
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
        //String builder
        StringBuilder builder=new StringBuilder();
        String line="";
        while ((line=bufferedReader.readLine())!=null){
            //Append line
            builder.append(line);
        }
        //get append data
        String data=builder.toString();
        //close reader
        bufferedReader.close();
        return data;
    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            JsonParser jsonParser=new JsonParser();
            List<HashMap<String,String>> mapList=null;
            JSONObject jsonObject=null;
            try {
                 jsonObject=new JSONObject(strings[0]);
                 mapList=jsonParser.parseResult(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
           // mymap.clear();
            for (int i=0;i<hashMaps.size();i++){
                HashMap<String,String> hashmaplist= hashMaps.get(i);
                double lat= Double.parseDouble(hashmaplist.get("lat"));
                double lng= Double.parseDouble(hashmaplist.get("lng"));
                String name=hashmaplist.get("name");
                LatLng latLng=new LatLng(lat,lng);
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(name);
                mymap.addMarker(markerOptions);
                mymap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mymap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.5f),4000,null);

                mymap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        BookTicketsFragment bookTicketsFragment=new BookTicketsFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame_container, bookTicketsFragment).
                                addToBackStack(bookTicketsFragment.getClass().getName()).commit();
                        return true;
                    }
                });

            }
        }
    }

    private void checkProfile() {
        List<User> users = daoHelper.getUserInterface().getAll();
        if(users.size()!=0){
            User user = users.get(0);
            name.setText(user.getFirst_name());
            byte[] data = user.getUser_image();
            if (data != null) {
                image = ImageConverter.convertByteArray2Bitmap(data);
                main_img.setImageBitmap(image);

            }
        }


    }
}