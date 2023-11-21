package com.example.findaseat;

import static android.util.Base64.DEFAULT;
import static org.junit.Assert.assertEquals;

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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test6CurrentReservation {
    @Test
    public void getCurrentReservation() {

        // Phineas user
        String eIdNumber = "1111111111";

        String eCurrReservationId = "Prw3Gm6FiQfcu1txFWjQ";
        String etimeBlock1 = "W 14:00:00";
        String etimeBlock2 = "W 14:30:00";
        String elocation = "Leavey Library";
        boolean eCanceled = false;
        boolean eIndoor = false;
        String eDate = "2023-11-29";
        String aCurrReservationId;
        String atimeBlock1;
        String atimeBlock2;
        String alocation;
        boolean aCanceled;
        boolean aIndoor;
        String aDate;

        try { //https://www.baeldung.com/java-http-request

            //check if reservation id exists
            URL url1 = new URL("http://34.125.226.6:8080/getUser?documentId=" + eIdNumber);
            HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
            con1.setRequestMethod("GET");

            int status1 = con1.getResponseCode();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
            String inputLine1;
            StringBuffer content1 = new StringBuffer();
            while ((inputLine1 = in1.readLine()) != null) {
                content1.append(inputLine1);
            }
            in1.close();

            con1.disconnect();
            System.out.println(content1);
            HashMap<String, String> yourHashMap1 = new Gson().fromJson(content1.toString(), HashMap.class);
            aCurrReservationId = yourHashMap1.get("currentReservationId");


            //check current reservation details
            URL url = new URL("http://34.125.226.6:8080/getCurrentReservation?documentId=" + eIdNumber);
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
            HashMap<String, Object> yourHashMap = new Gson().fromJson(content.toString(), HashMap.class);
            alocation = (String) yourHashMap.get("buildingName");
            ArrayList<String> temp = (ArrayList<String>) yourHashMap.get("timeBlocks");
            if (temp.get(0).equals(etimeBlock1)) {
                atimeBlock1 = temp.get(0);
                atimeBlock2 = temp.get(1);
            } else {
                atimeBlock1 = temp.get(1);
                atimeBlock2 = temp.get(0);
            }
            aCanceled = (boolean) yourHashMap.get("canceled");
            aIndoor = (boolean) yourHashMap.get("indoor");
            aDate = (String) yourHashMap.get("date");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(eCurrReservationId, aCurrReservationId);
        assertEquals(etimeBlock1, atimeBlock1);
        assertEquals(etimeBlock2, atimeBlock2);
        assertEquals(elocation, alocation);
        assertEquals(eCanceled, aCanceled);
        assertEquals(eIndoor, aIndoor);
        assertEquals(eDate, aDate);


    }
}