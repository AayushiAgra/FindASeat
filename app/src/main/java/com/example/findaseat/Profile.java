package com.example.findaseat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Profile extends AppCompatActivity {
    String uscid = null;
    String buildname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Intent intent = getIntent();
        uscid = intent.getStringExtra("uscid");
        String name = "Anna Smith";
        String affiliation = "STUDENT";
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://34.125.226.6:8080/refreshReservations");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();



        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://34.125.226.6:8080/getUser?documentId=" + uscid);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            System.out.println(content);
            HashMap<String, String> yourHashMap = new Gson().fromJson(content.toString(), HashMap.class);
            name = yourHashMap.get("name");
            affiliation = yourHashMap.get("affiliation");
            ImageView imview = (ImageView) findViewById(R.id.personpic);
            new LoadImage(imview, uscid).execute();
//            String image = yourHashMap.get("image");
//            image = image.replaceAll("%", "\n");
//            if (image != null){
//                byte[] im = Base64.decode(image, Base64.DEFAULT);
//                System.out.println(im);
//                Bitmap bmp = BitmapFactory.decodeByteArray(im, 0, im.length);
//                ImageView imview = (ImageView) findViewById(R.id.personpic);
//                imview.setImageBitmap(Bitmap.createScaledBitmap(bmp, 62, 80, false));
//            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //temp values for the variables below

        String[] currentreservation = {"Taper Hall", "Upcoming", "10/24/2024", "12:00-14:00 PST"};
        String[][] pastreservations = {{"Doheny Library", "Completed", "10/26/2024", "12:00-14:00 PST"}, {"Doheny Library", "Completed", "10/26/2024", "12:00-14:00 PST"}, {"Doheny Library", "Completed", "10/26/2024", "12:00-14:00 PST"}};

        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://34.125.226.6:8080/getPastReservations?documentId=" + uscid);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            System.out.println(content);
            HashMap<String, Object>[] yourHashMapArray = new Gson().fromJson(content.toString(), HashMap[].class);
            pastreservations = new String[yourHashMapArray.length][5];
            for (int i = 0; i < yourHashMapArray.length; i++){
                pastreservations[i][0] = (String) yourHashMapArray[i].get("buildingName");
                boolean a = (boolean) yourHashMapArray[i].get("canceled");
                if (a){
                    pastreservations[i][1] = "Canceled";
                }
                else{
                    pastreservations[i][1] = "Completed";
                }
                pastreservations[i][2] = yourHashMapArray[i].get("date").toString();
                ArrayList<String> temp = (ArrayList<String>) yourHashMapArray[i].get("timeBlocks");
                String min = "24:00:00";
                String max = "00:00:00";
                for (int j = 0; j < temp.size(); j++){
                    String parsed = temp.get(j).substring(2);
                    if (parsed.compareTo(min) < 0){
                        min = parsed;
                    }
                    if (parsed.compareTo(max) > 0){
                        max = parsed;
                    }
                }
                String add = min;
                add += " - ";
                if (max.charAt(3) == '3'){
                    if (max.charAt(1) == '9'){
                        char x = (char)(max.charAt(0) + 1);
                        max = x + "0:0"+ max.substring(4);
                        System.out.println("1");
                    }
                    else{
                        char x = (char)(max.charAt(1) + 1);

                        String t = max.charAt(0)+ ""+ x + ":0" + max.substring(4);
                        max = t;

                    }
                }
                else{
                    max = max.charAt(0)+ "" + max.charAt(1) + "" + max.charAt(2) + "3" + max.substring(4);
                }
                add += max;
                pastreservations[i][3] = add;

                a = (boolean) yourHashMapArray[i].get("indoor");
                if (a){
                    pastreservations[i][4] = "Indoor";
                }
                else{
                    pastreservations[i][4] = "Outdoor";
                }

            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://34.125.226.6:8080/getCurrentReservation?documentId=" + uscid);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            System.out.println(content);
            if (content.toString().equals("")){
                currentreservation = null;
            }
            else{
                HashMap<String, Object> yourHashMapArray = new Gson().fromJson(content.toString(), HashMap.class);
                currentreservation = new String[5];

                    currentreservation[0] = (String) yourHashMapArray.get("buildingName");
                    buildname = currentreservation[0];
                    currentreservation[1] = "Current";
                currentreservation[2] = yourHashMapArray.get("date").toString();
                ArrayList<String> temp = (ArrayList<String>) yourHashMapArray.get("timeBlocks");
                String min = "24:00:00";
                String max = "00:00:00";
                for (int i = 0; i < temp.size(); i++){
                    String parsed = temp.get(i).substring(2).trim();
                    if (parsed.compareTo(min) < 0){
                        min = parsed;
                    }
                    if (parsed.compareTo(max) > 0){
                        max = parsed;
                    }
                }
                String add = min;
                add += " - ";
                if (max.charAt(3) == '3'){
                    if (max.charAt(1) == '9'){
                        char x = (char)(max.charAt(0) + 1);
                        max = x + "0:0"+ max.substring(4);
                        System.out.println("1");
                    }
                    else{
                        char x = (char)(max.charAt(1) + 1);

                        String t = max.charAt(0)+ ""+ x + ":0" + max.substring(4);
                        max = t;
                        System.out.println("2");

                    }
                }
                else{
                    System.out.println("3");
                    max = max.charAt(0)+ "" + max.charAt(1) + "" + max.charAt(2) + "3" + max.substring(4);
                }
                add += max;
                currentreservation[3] = add;
                    boolean a = (boolean) yourHashMapArray.get("indoor");
                    if (a){
                        currentreservation[4] = "Indoor";
                    }
                    else{
                        currentreservation[4] = "Outdoor";
                    }


            }


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TextView studentid = findViewById(R.id.studentid);
        studentid.setText(uscid);

        TextView nameview = findViewById(R.id.studentname);
        nameview.setText(name);
        TextView affileview = findViewById(R.id.studentaffil);
        affileview.setText(affiliation);
        if (currentreservation == null){
            androidx.cardview.widget.CardView x = findViewById(R.id.currentreservationbox);
            x.setVisibility(View.GONE);
        }
        else{
            androidx.cardview.widget.CardView x = findViewById(R.id.currentreservationbox);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.CurrResName);
            buildingname.setText(currentreservation[0]);
            TextView status = findViewById(R.id.currresstatus);
            status.setText("Status: " + currentreservation[1]);
            TextView date = findViewById(R.id.currresdate);
            date.setText("Date: " + currentreservation[2]);
            TextView time = findViewById(R.id.currrestime);
            time.setText("Time: " + currentreservation[3]);
            TextView location = findViewById(R.id.currreslocation);
            location.setText("Location: " + currentreservation[4]);
        }
        if (pastreservations.length > 0){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox9);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname9);
            buildingname.setText(pastreservations[0][0]);
            TextView status = findViewById(R.id.paststatus9);
            status.setText("Status: " + pastreservations[0][1]);
            TextView date = findViewById(R.id.pastdate9);
            date.setText("Date: " + pastreservations[0][2]);
            TextView time = findViewById(R.id.pasttime9);
            time.setText("Time: " + pastreservations[0][3]);
            TextView location = findViewById(R.id.pastlocation9);
            location.setText("Location: " + pastreservations[0][4]);
        }
        if (pastreservations.length > 1){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox8);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname8);
            buildingname.setText(pastreservations[1][0]);
            TextView status = findViewById(R.id.paststatus8);
            status.setText("Status: " + pastreservations[1][1]);
            TextView date = findViewById(R.id.pastdate8);
            date.setText("Date: " + pastreservations[1][2]);
            TextView time = findViewById(R.id.pasttime8);
            time.setText("Time: " + pastreservations[1][3]);
            TextView location = findViewById(R.id.pastlocation8);
            location.setText("Location: " + pastreservations[1][4]);
        }
        if (pastreservations.length > 2){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox7);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname7);
            buildingname.setText(pastreservations[2][0]);
            TextView status = findViewById(R.id.paststatus7);
            status.setText("Status: " + pastreservations[2][1]);
            TextView date = findViewById(R.id.pastdate7);
            date.setText("Date: " + pastreservations[2][2]);
            TextView time = findViewById(R.id.pasttime7);
            time.setText("Time: " + pastreservations[2][3]);
            TextView location = findViewById(R.id.pastlocation7);
            location.setText("Location: " + pastreservations[2][4]);
        }
        if (pastreservations.length > 3){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox6);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname6);
            buildingname.setText(pastreservations[3][0]);
            TextView status = findViewById(R.id.paststatus6);
            status.setText("Status: " + pastreservations[3][1]);
            TextView date = findViewById(R.id.pastdate6);
            date.setText("Date: " + pastreservations[3][2]);
            TextView time = findViewById(R.id.pasttime6);
            time.setText("Time: " + pastreservations[3][3]);
            TextView location = findViewById(R.id.pastlocation6);
            location.setText("Location: " + pastreservations[3][4]);
        }
        if (pastreservations.length > 4){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox5);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname5);
            buildingname.setText(pastreservations[4][0]);
            TextView status = findViewById(R.id.paststatus5);
            status.setText("Status: " + pastreservations[4][1]);
            TextView date = findViewById(R.id.pastdate5);
            date.setText("Date: " + pastreservations[4][2]);
            TextView time = findViewById(R.id.pasttime5);
            time.setText("Time: " + pastreservations[4][3]);
            TextView location = findViewById(R.id.pastlocation5);
            location.setText("Location: " + pastreservations[4][4]);
        }
        if (pastreservations.length > 5){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox4);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname4);
            buildingname.setText(pastreservations[5][0]);
            TextView status = findViewById(R.id.paststatus4);
            status.setText("Status: " + pastreservations[5][1]);
            TextView date = findViewById(R.id.pastdate4);
            date.setText("Date: " + pastreservations[5][2]);
            TextView time = findViewById(R.id.pasttime4);
            time.setText("Time: " + pastreservations[5][3]);
            TextView location = findViewById(R.id.pastlocation4);
            location.setText("Location: " + pastreservations[5][4]);
        }
        if (pastreservations.length > 6){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox3);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname3);
            buildingname.setText(pastreservations[6][0]);
            TextView status = findViewById(R.id.paststatus3);
            status.setText("Status: " + pastreservations[6][1]);
            TextView date = findViewById(R.id.pastdate3);
            date.setText("Date: " + pastreservations[6][2]);
            TextView time = findViewById(R.id.pasttime3);
            time.setText("Time: " + pastreservations[6][3]);
            TextView location = findViewById(R.id.pastlocation3);
            location.setText("Location: " + pastreservations[6][4]);
        }
        if (pastreservations.length > 7){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox2);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname2);
            buildingname.setText(pastreservations[7][0]);
            TextView status = findViewById(R.id.paststatus2);
            status.setText("Status: " + pastreservations[7][1]);
            TextView date = findViewById(R.id.pastdate2);
            date.setText("Date: " + pastreservations[7][2]);
            TextView time = findViewById(R.id.pasttime2);
            time.setText("Time: " + pastreservations[7][3]);
            TextView location = findViewById(R.id.pastlocation2);
            location.setText("Location: " + pastreservations[7][4]);
        }
        if (pastreservations.length > 8){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox1);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname1);
            buildingname.setText(pastreservations[8][0]);
            TextView status = findViewById(R.id.paststatus1);
            status.setText("Status: " + pastreservations[8][1]);
            TextView date = findViewById(R.id.pastdate1);
            date.setText("Date: " + pastreservations[8][2]);
            TextView time = findViewById(R.id.pasttime1);
            time.setText("Time: " + pastreservations[8][3]);
            TextView location = findViewById(R.id.pastlocation1);
            location.setText("Location: " + pastreservations[8][4]);
        }
        if (pastreservations.length > 9){
            androidx.cardview.widget.CardView x = findViewById(R.id.pastbox0);
            x.setVisibility(View.VISIBLE);
            TextView buildingname = findViewById(R.id.pastname0);
            buildingname.setText(pastreservations[9][0]);
            TextView status = findViewById(R.id.paststatus0);
            status.setText("Status: " + pastreservations[9][1]);
            TextView date = findViewById(R.id.pastdate0);
            date.setText("Date: " + pastreservations[9][2]);
            TextView time = findViewById(R.id.pasttime0);
            time.setText("Time: " + pastreservations[9][3]);
            TextView location = findViewById(R.id.pastlocation0);
            location.setText("Location: " + pastreservations[9][4]);
        }


    }
    public void toLoginScreen(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    public void toSignUpScreen(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void currResOuterToggle(View view) {
        ImageView tog = findViewById(R.id.currresoutertoggle);
        View totog = findViewById(R.id.togglecurritems);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle0(View view) {
        ImageView tog = findViewById(R.id.pasttoggle0);
        View totog = findViewById(R.id.pasttext0);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle1(View view) {
        ImageView tog = findViewById(R.id.pasttoggle1);
        View totog = findViewById(R.id.pasttext1);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle2(View view) {
        ImageView tog = findViewById(R.id.pasttoggle2);
        View totog = findViewById(R.id.pasttext2);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle3(View view) {
        ImageView tog = findViewById(R.id.pasttoggle3);
        View totog = findViewById(R.id.pasttext3);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle4(View view) {
        ImageView tog = findViewById(R.id.pasttoggle4);
        View totog = findViewById(R.id.pasttext4);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle5(View view) {
        ImageView tog = findViewById(R.id.pasttoggle5);
        View totog = findViewById(R.id.pasttext5);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle6(View view) {
        ImageView tog = findViewById(R.id.pasttoggle6);
        View totog = findViewById(R.id.pasttext6);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle7(View view) {
        ImageView tog = findViewById(R.id.pasttoggle7);
        View totog = findViewById(R.id.pasttext7);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle8(View view) {
        ImageView tog = findViewById(R.id.pasttoggle8);
        View totog = findViewById(R.id.pasttext8);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }

    public void toMapScreen(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("uscid", uscid);
        startActivity(intent);
    }
    public void toProfileScreen(View view) {
        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("uscid", uscid);
        startActivity(intent);
    }
    public void pastResToggle9(View view) {
        ImageView tog = findViewById(R.id.pasttoggle9);
        View totog = findViewById(R.id.pasttext9);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void pastResToggle(View view) {
        ImageView tog = findViewById(R.id.pastrestoggle);
        View totog = findViewById(R.id.pastrestoggleitems);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }
    public void currResToggle(View view) {
        ImageView tog = findViewById(R.id.currentreservationtoggle);
        View totog = findViewById(R.id.currresinfo);
        if (totog.getVisibility() == View.VISIBLE){
            tog.setImageResource(R.drawable.downarrow);
            totog.setVisibility(View.GONE);
        }
        else{
            tog.setImageResource(R.drawable.uparrow);
            totog.setVisibility(View.VISIBLE);
        }

    }

    public void editReservation(View view) {
        Intent intent = new Intent(this, Reservation.class);
        intent.putExtra("uscid", uscid);
        intent.putExtra("building_name", buildname);

        startActivity(intent);
    }
    public void logOut(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void cancelReservation(View view) {
        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://34.125.226.6:8080/cancelReservation?documentId=" + uscid);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            con.disconnect();
            System.out.println(content);
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("uscid", uscid);
            startActivity(intent);



        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}