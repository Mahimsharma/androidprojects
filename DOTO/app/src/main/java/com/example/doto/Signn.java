package com.example.doto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doto.Utils.LogInHandler;

public class Signn extends AppCompatActivity {
    private ProgressBar progressBar;
    private String name;
    private String number;
    private EditText editText_username;
    private EditText editText_number;
    private Button button;
    private CheckBox checkBox;
    private boolean flag;
    private LogInHandler logInHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signn);
        logInHandler =LogInHandler.getInstance(this);
        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);
        editText_username = findViewById(R.id.username);
        editText_number =  findViewById(R.id.number);
        checkBox = findViewById(R.id.checkBox);
        flag=true;

    }
    public void submit() {
        if (flag) {
            flag = false;
            progressBar.setVisibility(View.VISIBLE);
            if (checkBox.isChecked()) {
                logInHandler.setSharedPreferences(name, number, true);
                Toast.makeText(this, "Credentials saved!", Toast.LENGTH_LONG).show();

            }

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Signn.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                    , 1000);
        }
    }

    public void validate(View v)
    {

        name = editText_username.getText().toString();
        String nam = editText_username.getText().toString().replaceAll(" ","");
        number = editText_number.getText().toString().trim();

        String numb;
        if(number.length()>0) {
            if (number.charAt(0) == '+') numb = number.substring(1);
            else  numb=number;
        }
        else numb="";

        if (nam.length() == 0) {
            Toast.makeText(Signn.this, "Please enter the name.", Toast.LENGTH_SHORT).show();
        }
        else if (!nam.replaceAll("\\s", "").matches("^[a-zA-Z]*$")) {
            Toast.makeText(Signn.this, "Only Alphabets allowed in name....", Toast.LENGTH_SHORT).show();
        }
        else if (numb.length() == 0) {
            Toast.makeText(Signn.this, "Please enter your number.", Toast.LENGTH_SHORT).show();
        }
        else if ((numb.length() < 8 )) {
            Toast.makeText(Signn.this, "Invalid Number", Toast.LENGTH_SHORT).show();
        }

        else submit();

    }
}

