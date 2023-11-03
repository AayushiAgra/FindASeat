package com.example.findaseat;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LogIn  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    public void toMainScreen(View view) {
        Intent intent = new Intent(this, GuestProfile.class);
        startActivity(intent);
    }
    public void toMapScreen(View view) {
        Intent intent = new Intent(this, GuestProfile.class);
        startActivity(intent);
    }
    public void toSignUpScreen(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void logInSubmit(View view) {
        TextInputLayout uscid= findViewById(R.id.uscid);
        String studentid = (uscid.getEditText()).getText().toString();
        TextInputLayout passl= findViewById(R.id.password);
        String pass = (passl.getEditText()).getText().toString();
        boolean isvalid = true;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }
        try { //https://www.baeldung.com/java-http-request


            URL url = new URL("http://172.20.10.6:8080/loginUser?documentId=" + studentid + "&pass=" + pass);
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
                System.out.println("here");
                isvalid = false;
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (isvalid){
            TextView errortext = findViewById(R.id.errorText);
            errortext.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(this, Profile.class);
            intent.putExtra("uscid", studentid);
            startActivity(intent);
        }
        else{
            TextView errortext = findViewById(R.id.errorText);
            errortext.setVisibility(View.VISIBLE);
        }
    }
}
