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
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test15DoRefresh {
    @Test
    public void doRefresh() {

        // Doof user
        String eIdNumber = "0987654098";

        String etimeBlock1 = "[\"W 14:00:00\"]";
        String elocation = "School of Cinematic Arts (SCA)";
        boolean eCanceled = false;
        boolean eIndoor = false;
        String eCanceledStr = "Completed";
        String eIndoorStr = "Outdoor";
        String eDate = "2023-11-15";
        String[][] apastreservations;
        String eTime = "14:00:00 - 14:30:00";

        String aCurrReservationId;
        String atimeBlock;
        String alocation;
        boolean aCanceled;
        boolean aIndoor;
        String aDate;
        String aResponse;

        try { //https://www.baeldung.com/java-http-request

            //make an old reservation
            URL url = new URL("http://34.125.226.6:8080/makeReservation");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");

            String jsonInputString = "{\"idNumber\": \"" + eIdNumber +"\", \"buildingName\": \"" + elocation + "\", \"timeBlocks\":" + etimeBlock1
                    + ", \"indoor\": \"" + eIndoor +"\", \"isCanceled\": \"" + false + "\", \"date\": \"" + eDate + "\"}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                aResponse = response.toString();
                assertEquals(aResponse, "true");
            }
            catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //check current reservation details
            URL url1 = new URL("http://34.125.226.6:8080/getCurrentReservation?documentId=" + eIdNumber);
            HttpURLConnection con1 = (HttpURLConnection) url1.openConnection();
            con1.setRequestMethod("GET");

            int status = con1.getResponseCode();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
            String inputLine1;
            StringBuffer content1 = new StringBuffer();
            while ((inputLine1 = in1.readLine()) != null) {
                content1.append(inputLine1);
            }
            in1.close();

            con1.disconnect();
            System.out.println(content1);
            aResponse = content1.toString();
            assertNotEquals(aResponse, "");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //doRefresh
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

        //check that currReservation is empty
        //check current reservation details
        try {
            URL url2 = new URL("http://34.125.226.6:8080/getCurrentReservation?documentId=" + eIdNumber);
            HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
            con2.setRequestMethod("GET");

            int status2 = con2.getResponseCode();
            BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            String inputLine2;
            StringBuffer content2 = new StringBuffer();
            while ((inputLine2 = in2.readLine()) != null) {
                content2.append(inputLine2);
            }
            in2.close();

            con2.disconnect();
            System.out.println(content2);
            aResponse = content2.toString();
            assertEquals(aResponse, "");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //check past reservations
        try { //https://www.baeldung.com/java-http-request
            URL url = new URL("http://34.125.226.6:8080/getPastReservations?documentId=" + eIdNumber);
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
            apastreservations = new String[yourHashMapArray.length][5];
            for (int i = 0; i < yourHashMapArray.length; i++){
                apastreservations[i][0] = (String) yourHashMapArray[i].get("buildingName");
                boolean a = (boolean) yourHashMapArray[i].get("canceled");
                if (a){
                    apastreservations[i][1] = "Canceled";
                }
                else{
                    apastreservations[i][1] = "Completed";
                }
                apastreservations[i][2] = yourHashMapArray[i].get("date").toString();
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
                apastreservations[i][3] = add;

                a = (boolean) yourHashMapArray[i].get("indoor");
                if (a){
                    apastreservations[i][4] = "Indoor";
                }
                else{
                    apastreservations[i][4] = "Outdoor";
                }

            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(apastreservations[apastreservations.length-1][0], elocation);
        assertEquals(apastreservations[apastreservations.length-1][1], eCanceledStr);
        assertEquals(apastreservations[apastreservations.length-1][2], eDate);
        assertEquals(apastreservations[apastreservations.length-1][3], eTime);
        assertEquals(apastreservations[apastreservations.length-1][4], eIndoorStr);

    }
}