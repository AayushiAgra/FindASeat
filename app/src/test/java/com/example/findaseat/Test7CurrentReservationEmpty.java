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
public class Test7CurrentReservationEmpty {
    @Test
    public void getCurrentReservationEmpty() {

        // Ferb user
        String eIdNumber = "0987654098";
        String result;

        try { //https://www.baeldung.com/java-http-request

            //check if reservation id exists
            URL url1 = new URL("http://34.125.226.6:8080/getCurrentReservation?documentId=" + eIdNumber);
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
            result = content1.toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(result, "");


    }
}