package com.example.findaseat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imv;
    String uscid;


    public LoadImage(ImageView imv, String uscid) {
        this.imv = imv;
        this.uscid = uscid;

    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;
        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://172.20.10.2:8080/getUser?documentId=" + uscid);
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
            String image = yourHashMap.get("image");
            image = image.replaceAll("%", "\n");
            if (image != null){
                byte[] im = Base64.decode(image, Base64.DEFAULT);
                System.out.println(im);
                Bitmap bmp = BitmapFactory.decodeByteArray(im, 0, im.length);
                return bmp;

            }
            return null;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void onPostExecute(Bitmap result) {


        if(result != null && imv != null){

            imv.setImageBitmap(Bitmap.createScaledBitmap(result, 62, 80, false));
        }else{
            imv.setVisibility(View.GONE);
        }
    }

}
