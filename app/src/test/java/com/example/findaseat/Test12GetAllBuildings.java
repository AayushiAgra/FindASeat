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
public class Test12GetAllBuildings {
    @Test
    public void getAllBuildings() throws IOException {
        Building[] buildings;
        ArrayList<String> eNames = new ArrayList<String>();
        ArrayList<Double> eLats = new ArrayList<Double>();
        ArrayList<Double> eLongs = new ArrayList<Double>();

        eNames.add("Doheny Memorial Library");
        eLats.add(34.02017827866693);
        eLongs.add(-118.28372327794975);
        eNames.add("Dr. Joseph Medicine Crow Center for International and Public Affairs (CPA)");
        eLats.add(34.021254672681025);
        eLongs.add(-118.28399159877394);
        eNames.add("Leavey Library");
        eLats.add(34.02184849324917);
        eLongs.add(-118.28290336889022);
        eNames.add("Ronald Tutor Campus Center (RTCC)");
        eLats.add(34.02034519432595);
        eLongs.add(-118.28637549484489);
        eNames.add("Ronald Tutor Hall");
        eLats.add(34.02013351626869);
        eLongs.add(-118.28992536888245);
        eNames.add("Salvatori Computer Science Center (SAL)");
        eLats.add(34.019539424234296);
        eLongs.add(-118.28954283528519);
        eNames.add("School of Cinematic Arts (SCA)");
        eLats.add(34.02358245621351);
        eLongs.add(-118.28656739463155);
        eNames.add("Taper Hall");
        eLats.add(34.02222962787753);
        eLongs.add(-118.28457048865779);
        eNames.add("Verna and Peter Dauterive Hall (VPD)");
        eLats.add(34.018947320636556);
        eLongs.add(-118.28396756231447);
        eNames.add("Wallis Annenberg Hall (ANN)");
        eLats.add(34.02075651966122);
        eLongs.add(-118.28710206744456);

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
        assertEquals(buildings.length, 10);

        for (Building b : buildings) {
            int i = eNames.indexOf(b.buildingName);
            assertNotEquals(i, -1);
            assertEquals(b.latitude, eLats.get(i), 0.1);
            assertEquals(b.longitude, eLongs.get(i), 0.1);
        }
    }
}