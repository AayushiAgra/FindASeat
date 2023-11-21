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
import java.util.TimeZone;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test13CancelReservation {
    @Test
    public void cancelReservation() throws IOException {

        // Major Monogram user
        String eIdNumber = "3333333333";
        String eBuilding = "Dr. Joseph Medicine Crow Center for International and Public Affairs (CPA)";
        String eTimeBlock1 = "Th 14:00:00";
        String eTimeBlock2 = "Th 14:30:00";
        boolean eIndoorBool = false;
        String eResponse = "true";
        String aResponse;
        String res_week = "2023-11-27";

        double prevSeat1;
        double prevSeat2;

        //get previous seat availability
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
        prevSeat1 = (double) avails_indoor.get(eTimeBlock1);
        prevSeat2 = (double) avails_indoor.get(eTimeBlock2);

        //cancel reservation
        URL url = new URL("http://34.125.226.6:8080/cancelReservation?documentId=" + eIdNumber);
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
        aResponse = content.toString();
        assertEquals(aResponse, eResponse);

        //check current reservation details
        URL url2 = new URL("http://34.125.226.6:8080/getCurrentReservation?documentId=" + eIdNumber);
        HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
        con2.setRequestMethod("GET");

        int status2 = con.getResponseCode();
        BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
        String inputLine2;
        StringBuffer content2 = new StringBuffer();
        while ((inputLine2 = in2.readLine()) != null) {
            content2.append(inputLine2);
        }
        in2.close();

        con2.disconnect();
        System.out.println(content2);
        String aCurrReservation = content2.toString();
        assertEquals(aCurrReservation, "");

//        boolean isIndoor = true;
        HashMap<String, Object> avails_indoor_new = new HashMap<>();
        String url_string3 = "http://34.125.226.6:8080/getBuildingAvailability?buildingName=" + eBuilding + "&isIndoor=" + eIndoorBool + "&weekDateStr=" + res_week;
        if(url_string3.contains(" "))
            url_string3 = url_string3.replace(" ", "%20");
        URL url3 = new URL(url_string3);
        HttpURLConnection con3 = (HttpURLConnection)url3.openConnection();
        con3.setRequestMethod("GET");
        int status3 = con3.getResponseCode();
        BufferedReader in3 = new BufferedReader(new InputStreamReader(con3.getInputStream()));
        String inputLine3;
        StringBuffer content3 = new StringBuffer();
        while ((inputLine3 = in3.readLine()) != null) {
            content3.append(inputLine3);
        }
        in3.close();

//        System.out.println("STATUS CODE FOR NEW THINGY");
        System.out.println(status3);


//
        avails_indoor_new = new Gson().fromJson(content3.toString(), HashMap.class);
        double newSeat1 = (double) avails_indoor_new.get(eTimeBlock1);
        double newSeat2 = (double) avails_indoor_new.get(eTimeBlock2);

        newSeat1--;
        newSeat2--;

        assertEquals(newSeat1, prevSeat1, 0.1);
        assertEquals(newSeat2, prevSeat2, 0.1);
    }
}