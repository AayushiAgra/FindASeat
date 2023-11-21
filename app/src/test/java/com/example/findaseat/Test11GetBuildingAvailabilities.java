package com.example.findaseat;

import static android.util.Base64.DEFAULT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.graphics.Bitmap;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test11GetBuildingAvailabilities {
    @Test
    public void getAvailabilities() throws IOException {

        String eBuilding = "Verna and Peter Dauterive Hall (VPD)";
        boolean eIndoorBool = true;
        String res_week = "2023-11-27";

        double eindoorSeats = 400;
        double eoutdoorSeats = 65;

        HashMap<String, Object> avails_indoor = new HashMap<>();
        String url_string = "http://34.125.226.6:8080/getBuildingAvailability?buildingName=" + eBuilding + "&isIndoor=" + eIndoorBool + "&weekDateStr=" + res_week;
        if(url_string.contains(" "))
            url_string = url_string.replace(" ", "%20");
        URL url0 = new URL(url_string);
        HttpURLConnection con0 = (HttpURLConnection)url0.openConnection();
        con0.setRequestMethod("GET");
        int status0 = con0.getResponseCode();
        BufferedReader in0 = new BufferedReader(new InputStreamReader(con0.getInputStream(), Charset.forName("UTF-8")));
        String inputLine0;
        StringBuffer content0 = new StringBuffer();
        while ((inputLine0 = in0.readLine()) != null) {
            content0.append(inputLine0);
        }
        in0.close();

//        System.out.println("STATUS CODE FOR NEW THINGY");
        System.out.println(status0);


        Gson gson = new Gson();
//
        avails_indoor = new Gson().fromJson(content0.toString(), HashMap.class);
        Iterator inIt = avails_indoor.entrySet().iterator();
        while (inIt.hasNext()) {
            Map.Entry avail = (Map.Entry)inIt.next();
            double availSeat = (double) avail.getValue();

            assertEquals(availSeat, eindoorSeats, 0.1);
        }

        eIndoorBool = false;
        HashMap<String, Object> avails_outdoor = new HashMap<>();
        String url_string2 = "http://34.125.226.6:8080/getBuildingAvailability?buildingName=" + eBuilding + "&isIndoor=" + eIndoorBool + "&weekDateStr=" + res_week;
        if(url_string2.contains(" "))
            url_string2 = url_string2.replace(" ", "%20");
        URL url2 = new URL(url_string2);
        HttpURLConnection con2 = (HttpURLConnection)url2.openConnection();
        con2.setRequestMethod("GET");
        int status2 = con2.getResponseCode();
        BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream(), Charset.forName("UTF-8")));
        String inputLine2;
        StringBuffer content2 = new StringBuffer();
        while ((inputLine2 = in2.readLine()) != null) {
            content2.append(inputLine2);
        }
        in2.close();

//        System.out.println("STATUS CODE FOR NEW THINGY");
        System.out.println(status2);


//
        avails_outdoor = new Gson().fromJson(content2.toString(), HashMap.class);
        Iterator outIt = avails_outdoor.entrySet().iterator();
        while (outIt.hasNext()) {
            Map.Entry avail = (Map.Entry)outIt.next();
            double availSeat = (double) avail.getValue();

            assertEquals(availSeat, eoutdoorSeats, 0.1);
        }
    }
}