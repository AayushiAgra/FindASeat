package com.example.findaseat;

import static org.junit.Assert.assertEquals;

import android.widget.ImageView;

import com.google.gson.Gson;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test5GetUserInformation {
    @Test
    public void getUserInformation() {

        String eName = "Phineas Flynn";
        String eAffiliation = "STUDENT";
        String eIdNumber = "1111111111";

        String aName = "";
        String aAffiliation = "";
        String aIdNumber = "";

        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://34.125.226.6:8080/getUser?documentId=" + eIdNumber);
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
            aName = yourHashMap.get("name");
            aAffiliation = yourHashMap.get("affiliation");
            aIdNumber = yourHashMap.get("idNumber");
//            ImageView imview = (ImageView) findViewById(R.id.personpic);
//            new LoadImage(imview, uscid).execute();
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

        assertEquals(eName, aName);
        assertEquals(eAffiliation, aAffiliation);
        assertEquals(eIdNumber, aIdNumber);
    }
}