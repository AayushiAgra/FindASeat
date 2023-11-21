package com.example.findaseat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Test2BadLogin {
    @Test
    public void badLogin() {

        boolean isvalid = true;

        try { //https://www.baeldung.com/java-http-request

            String studentid = "1111111112";
            String pass = "perr";
            URL url = new URL("http://34.125.226.6:8080/loginUser?documentId=" + studentid + "&pass=" + pass);
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
            if (content.toString().equals("false")){
                isvalid = false;
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertEquals(false, isvalid);
    }
}