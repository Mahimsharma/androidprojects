package com.example.doto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.doto.Utils.LogInHandler;

public class SplashScreen extends AppCompatActivity {

    LogInHandler logInHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logInHandler = LogInHandler.getInstance(this);
        Handler handler = new Handler();
        if (logInHandler.checkCredentials())
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            },1000);
        }
        else
        {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Signn.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }
}
