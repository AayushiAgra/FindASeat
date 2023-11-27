package com.example.findaseat;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.findaseat.databinding.ActivityMapsBinding;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private ArrayList<Marker> markerList;

    private Map<String, String> building_to_desc;

    private String uscid = "";

    private Building[] buildings;

    private boolean filterOn = false;

    private boolean filterOpenNow = false;
    private boolean filterFavorites = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildings = new Building[]{};

        try {
            getBuildings();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Intent intent = getIntent();
        if (intent.getStringExtra("uscid") == null) {
            Button resButton = (Button) findViewById(R.id.buttonReserve);
            String text = "Create Account";
            resButton.setText(text);
            ImageView heart = (ImageView) findViewById(R.id.heart);
            heart.setVisibility(View.GONE);
        }
        else {
            uscid = intent.getStringExtra("uscid");
//            LinearLayout ll = (LinearLayout) findViewById(R.id.filtersLayout);
//            ll.setVisibility(View.VISIBLE);

        }

        filterOn = false;
        filterOpenNow = false;
        filterFavorites = false;

    }


    public void toMapScreen(View view) {
        if (uscid == ""){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("uscid", uscid);
            startActivity(intent);
        }

    }
    public void toProfileScreen(View view) {
        if (uscid == ""){
            Intent intent = new Intent(this, GuestProfile.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("uscid", uscid);
            startActivity(intent);
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        markerList = new ArrayList<>();
        building_to_desc = new HashMap<>();


        mMap = googleMap;

        //get latlong for corners for specified place
        LatLng one = new LatLng(34.025967, -118.292250);
        LatLng two = new LatLng(34.018439, -118.280133);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        //add them to builder
        builder.include(one);
        builder.include(two);

        LatLngBounds bounds = builder.build();

        //get width and height to current display screen
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        // 20% padding
        int padding = (int) (width * 0.20);

        //set latlong bounds
        mMap.setLatLngBoundsForCameraTarget(bounds);

        //move camera to fill the bound to screen
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        //set zoom to level to current so that you won't be able to zoom out viz. move outside bounds
        mMap.setMinZoomPreference(mMap.getCameraPosition().zoom);

        for (int i = 0; i < buildings.length; i++) {
            Building b = buildings[i];
            String b_name = b.buildingName;
            String b_desc = b.description;
            double latitude = b.latitude;
            double longitude = b.longitude;

            LatLng coords = new LatLng(latitude, longitude);
            Marker marker = googleMap.addMarker(new MarkerOptions()
                            .title(b_name)
                    .position(coords)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.unselected_marker)));

            marker.setTag(b_name);
            markerList.add(marker);
            building_to_desc.put(b_name, b_desc);
        }

        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);
    }

    public boolean onMarkerClick(Marker marker) {
        marker.setTitle("");
        String selected_name = String.valueOf(marker.getTag());
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.selected_marker));
        for (int i = 0; i < markerList.size(); i++) {
            Marker mk = markerList.get(i);
            String name = String.valueOf(mk.getTag());
//            System.out.println(selected_name);
            if (!name.equals(selected_name)) {
                mk.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.unselected_marker));
            }
        }

        LinearLayout b = findViewById(R.id.overlay);
        String desc = building_to_desc.get(selected_name);

        TextView building_name = (TextView) findViewById(R.id.buildingName);
        TextView building_desc = (TextView) findViewById(R.id.buildingDescription);

        building_name.setText(selected_name);
        building_desc.setText(desc);

        ImageView heart = (ImageView) findViewById(R.id.heart);
        try {
            setHeartFill(heart, selected_name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        b.setVisibility(View.VISIBLE);
        return false;
    }

    public void setHeartFill(View view, String bName) throws IOException {
        if (uscid.equals("")) {
            return;
        }
        ImageView heart = (ImageView) view;
        boolean isFave = getBuildingFavorite(bName);
        if (isFave) {
            heart.setTag("filled");
            heart.setImageResource(R.drawable.filledheart);
        }
        else {
            heart.setTag("unfilled");
            heart.setImageResource(R.drawable.unfilledheart);
        }
    }

    public boolean getBuildingFavorite(String bName) throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }
//        System.out.println("here");
        String urlStr = "http://34.125.226.6:8080/checkHeart?documentId=" + uscid + "&buildingId=" + bName;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        String result = content.toString();
        if (result.equals("true")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void clickButton(View view) {
        Intent intent = new Intent(this, Reservation.class);
        // add another intent for creating account/logging in

        Button b = (Button) findViewById(R.id.buttonReserve);
        String text = (String) b.getText();
        if (text.equals("Make Reservation")) {
            TextView tv1 = (TextView) findViewById(R.id.buildingName);
//            TextView tv2 = (TextView) findViewById(R.id.buildingDescription);

            String building_name = (String) tv1.getText();
            intent.putExtra("building_name", building_name);
            intent.putExtra("uscid", uscid);
            intent.putExtra("newres", "true");

//            String building_desc = (String) tv2.getText();
////            intent.putExtra("building_desc", building_desc);

            startActivity(intent);

        }
        else {
            Intent inte = new Intent(this, GuestProfile.class);
            startActivity(inte);
        }

    }

    public void clickOpenNow(View view) throws IOException {
        ImageView openBtn = (ImageView) findViewById(R.id.openNow);
        LinearLayout ll = (LinearLayout) findViewById(R.id.overlay);
        if (filterOpenNow) {
            openBtn.setImageResource(R.drawable.opennowunselected);
            filterOpenNow = false;
            ll.setVisibility(View.GONE);
        }
        else {
            openBtn.setImageResource(R.drawable.opennowselected);
            filterOpenNow = true;
            ll.setVisibility(View.GONE);
        }
        loadFilteredBuildings();
    }

    public void clickHeart(View view) throws IOException {
        ImageView heartBtn = (ImageView) findViewById(R.id.heart);
        String tag = (String) heartBtn.getTag();
        TextView tv = (TextView) findViewById(R.id.buildingName);
        String bName = (String) tv.getText();

        if (tag.equals("unfilled")) {
            // add to faves
            heartBtn.setImageResource(R.drawable.filledheart);
            heartBtn.setTag("filled");
            updateFavorites("heart", bName);
        }
        else {
            // remove from faves
            heartBtn.setImageResource(R.drawable.unfilledheart);
            heartBtn.setTag("unfilled");
            updateFavorites("unheart", bName);
        }

    }

    public void clickFavorites(View view) throws IOException {
        ImageView favBtn = (ImageView) findViewById(R.id.favorites);
        LinearLayout ll = (LinearLayout) findViewById(R.id.overlay);
        if (filterFavorites) {
            favBtn.setImageResource(R.drawable.favoritesunselected);
            filterFavorites = false;
            ll.setVisibility(View.GONE);
        }
        else {
            favBtn.setImageResource(R.drawable.favoritesselected);
            filterFavorites = true;
            ll.setVisibility(View.GONE);
        }
        loadFilteredBuildings();
    }

    public void updateFavorites(String op, String bName) throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }
//        System.out.println("here");
        String urlStr = "http://34.125.226.6:8080/" + op + "?documentId=" + uscid + "&buildingId=" + bName;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("PUT");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        System.out.println(status);

        con.disconnect();
    }

    public void loadFilteredBuildings() throws IOException {
        buildings = new Building[]{};
        filterBuildings(filterOpenNow, filterFavorites);

        mMap.clear();
        markerList.clear();

        for (int i = 0; i < buildings.length; i++) {
            Building b = buildings[i];
            String b_name = b.buildingName;
            String b_desc = b.description;
            double latitude = b.latitude;
            double longitude = b.longitude;

            LatLng coords = new LatLng(latitude, longitude);
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .title(b_name)
                    .position(coords)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.unselected_marker)));

            marker.setTag(b_name);
            markerList.add(marker);
            building_to_desc.put(b_name, b_desc);
        }

        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

    }


    public void clickToggle(View view) {
        ImageView toggle = (ImageView) findViewById(R.id.filterToggle);
        ImageView faves = (ImageView) findViewById(R.id.favorites);
        ImageView open = (ImageView) findViewById(R.id.openNow);
        LinearLayout ll = (LinearLayout) findViewById(R.id.filtersLayout);
        if (filterOn) {
            toggle.setImageResource(R.drawable.filter);
            faves.setVisibility(View.INVISIBLE);
            open.setVisibility(View.INVISIBLE);
            ll.setElevation(0);
            filterOn = false;
        }
        else {
            toggle.setImageResource(R.drawable.selectedfilter);
            if (uscid.equals("")) {
                faves.setVisibility(View.GONE);
            }
            else {
                faves.setVisibility(View.VISIBLE);
            }

            open.setVisibility(View.VISIBLE);
            ll.setElevation(10);

            filterOn = true;
        }
    }

    public void getBuildings() throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }
//        System.out.println("here");
        URL url = new URL("http://34.125.226.6:8080/getAllBuildings");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

//        System.out.println(status);

        Gson gson = new Gson();

        buildings = new Gson().fromJson(content.toString(), Building[].class);


        con.disconnect();

    }

    public void filterBuildings(boolean openNow, boolean hearts) throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy gfgPolicy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(gfgPolicy);
        }
//        System.out.println("here");
        String urlStr = "http://34.125.226.6:8080/getAllBuildingsFilter?documentId=" + uscid + "&openNow=" + openNow + "&hearts=" + hearts;
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        int status = con.getResponseCode();
        System.out.println(status);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

//        System.out.println(status);

        Gson gson = new Gson();

        buildings = new Gson().fromJson(content.toString(), Building[].class);


        con.disconnect();

    }

}