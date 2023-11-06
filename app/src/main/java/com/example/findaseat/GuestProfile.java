package com.example.findaseat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuestProfile extends AppCompatActivity {

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
        setContentView(R.layout.guest_profile);
    }
    public void toLoginScreen(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
    public void toSignUpScreen(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }
    public void toMapScreen(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void toProfileScreen(View view) {
        Intent intent = new Intent(this, GuestProfile.class);
        startActivity(intent);
    }
}