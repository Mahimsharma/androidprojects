package com.example.form;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonSignOut;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE);

        buttonSignOut = findViewById(R.id.buttonSignOut);
        buttonSignOut.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        sharedPreferences.edit().clear().apply();
        LogInActivity.signOut();
        super.onBackPressed();
        finish();
    }
}