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
public class Test8GetPastReservations {
    @Test
    public void getPastReservations() {

        // Ferb user
        String eIdNumber = "2222222222";
        String[][] apastreservations;

        String[][] eRes = new String[3][5];
        eRes[0][0] = "Leavey Library";
        eRes[0][3] = "12:30:00 - 13:30:00";
        eRes[0][2] = "2023-11-13";
        eRes[0][1] = "Canceled";
        eRes[0][4] = "Outdoor";
        eRes[1][0] = "Ronald Tutor Campus Center (RTCC)";
        eRes[1][3] = "15:00:00 - 16:00:00";
        eRes[1][2] = "2023-11-15";
        eRes[1][1] = "Canceled";
        eRes[1][4] = "Indoor";
        eRes[2][0] = "Doheny Memorial Library";
        eRes[2][3] = "17:00:00 - 18:30:00";
        eRes[2][2] = "2023-11-14";
        eRes[2][1] = "Canceled";
        eRes[2][4] = "Indoor";


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

        assertEquals(apastreservations.length, 3);
        assertEquals(apastreservations[0].length, 5);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(apastreservations[i][j], eRes[i][j]);
            }
        }

    }
}