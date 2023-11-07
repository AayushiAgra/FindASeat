package com.example.findaseat;

import static android.util.Base64.DEFAULT;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Base64;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

public class SignUp  extends AppCompatActivity {
    Bitmap profpic = null;
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
        setContentView(R.layout.signup);
    }
    public void toLoginScreen(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    public void toMainScreen(View view) {
        Intent intent = new Intent(this, GuestProfile.class);
        startActivity(intent);
    }
    public void submitSignUp(View view) {
        TextInputLayout namel= findViewById(R.id.name);
        String name= (namel.getEditText()).getText().toString();
        TextInputLayout uscid= findViewById(R.id.uscid);
        String studentid = (uscid.getEditText()).getText().toString();
        TextInputLayout affl= findViewById(R.id.affiliation);
        String affiliation = (affl.getEditText()).getText().toString();
        TextInputLayout passl= findViewById(R.id.password);
        String pass = (passl.getEditText()).getText().toString();
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_focused}, // focused
                new int[] { android.R.attr.state_hovered}, // hovered
                new int[] { android.R.attr.state_enabled}, // enabled
                new int[] { }  //
        };

        int[] colors = new int[] {
                Color.parseColor("#880000"),
                Color.parseColor("#880000"),
                Color.parseColor("#880000"),
                Color.parseColor("#880000")
        };

        int[] colors2 = new int[] {
                R.color.mtrl_textinput_default_box_stroke_color,
                R.color.mtrl_textinput_default_box_stroke_color,
                R.color.mtrl_textinput_default_box_stroke_color,
                R.color.mtrl_textinput_default_box_stroke_color
        };


        ColorStateList myColorList = new ColorStateList(states, colors);
        ColorStateList myColorList2 = new ColorStateList(states, colors2);
        boolean tosubmit = true;
        if (name.equals("")){
            tosubmit = false;
            namel.setBoxStrokeColorStateList(myColorList);
        }
        else{
            namel.setBoxStrokeColorStateList(myColorList2);
        }
        if (studentid.matches("\\d+") && studentid.length() == 10){

            uscid.setBoxStrokeColorStateList(myColorList2);
        }
        else{
            tosubmit = false;
            uscid.setBoxStrokeColorStateList(myColorList);
        }
        if (affiliation.equalsIgnoreCase("Undergraduate Student")){
            affiliation = "STUDENT";
            affl.setBoxStrokeColorStateList(myColorList2);
        }
        else if (affiliation.equalsIgnoreCase("Graduate Student")){
            affiliation = "GRADUATE STUDENT";
            affl.setBoxStrokeColorStateList(myColorList2);
        }
        else if (affiliation.equalsIgnoreCase("Faculty")){
            affiliation = "FACULTY";
            affl.setBoxStrokeColorStateList(myColorList2);
        }
        else if (affiliation.equalsIgnoreCase("Staff")){
            affiliation = "STAFF";
            affl.setBoxStrokeColorStateList(myColorList2);
        }
        else{
            tosubmit = false;
            affl.setBoxStrokeColorStateList(myColorList);
        }
        if (pass.equals("")){
            tosubmit = false;
            passl.setBoxStrokeColorStateList(myColorList);
        }
        else{
            passl.setBoxStrokeColorStateList(myColorList2);
        }
        if (profpic == null){
            tosubmit = false;
            TextView proftext = findViewById(R.id.proftext);
            proftext.setTextColor(Color.parseColor("#880000"));
            View seperator = findViewById(R.id.separator);
            seperator.setBackgroundColor(Color.parseColor("#880000"));
        }
        else{
            TextView proftext = findViewById(R.id.proftext);
            proftext.setTextColor(Color.parseColor("#8A00C2"));
            View seperator = findViewById(R.id.separator);
            seperator.setBackgroundColor(Color.parseColor("#8A00C2"));
        }
        if (tosubmit){


            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //your codes here

            }
            try { //https://www.baeldung.com/java-http-request


                URL url = new URL("http://34.125.226.6:8080/createUser");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestProperty("Content-Type", "application/json");
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            profpic.compress(Bitmap.CompressFormat.PNG, 100, bao);
            byte[] ba = bao.toByteArray();
                String str = Base64.encodeToString(ba, DEFAULT);
                str = str.replaceAll("\n", "%");
                String jsonInputString = "{\"name\": \"" + name +"\", \"affiliation\": \"" + affiliation + "\", \"idNumber\": \"" + studentid + "\", \"image\": \"" + str +"\", \"password\": \"" + pass + "\"}";
                System.out.println(jsonInputString);
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
                    if (response.toString().equals("false")){
                        System.out.println("here");
                        TextView errortext = findViewById(R.id.errorText);
                        errortext.setVisibility(View.VISIBLE);
                        uscid.setBoxStrokeColorStateList(myColorList);
                        return;

                    }
                    System.out.println(response.toString());
                }





//
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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

    public void toMapScreen(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void toProfileScreen(View view) {
        Intent intent = new Intent(this, GuestProfile.class);
        startActivity(intent);
    }
    public void addPicture(View view) {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();

                        try {
                            profpic
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);

                            ((ImageView)findViewById(R.id.profpic)).setImageBitmap(
                                    profpic);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
}
