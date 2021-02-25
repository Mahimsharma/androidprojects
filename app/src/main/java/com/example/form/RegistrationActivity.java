package com.example.form;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editname;
    private ImageButton buttonBack;
    private Button buttonContinue;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE);
        editname = findViewById(R.id.editName);
        buttonBack = findViewById(R.id.buttonBack);
        buttonContinue = findViewById(R.id.buttonContinue);


        if(getIntent().getExtras() != null){
            editname.setText(getIntent().getExtras().getString("name"));
        }

        buttonBack.setOnClickListener(this);
        buttonContinue.setOnClickListener(this);
        showSoftKeyboard(editname);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.buttonBack:
                sharedPreferences.edit().clear().apply();
                LogInActivity.signOut();
                onBackPressed();
                finish();
                break;
            case  R.id.buttonContinue:
                sharedPreferences.edit().putString("name",editname.getText().toString()).commit();
                Intent intent = new Intent(this,HomeScreen.class);
                startActivity(intent);
                Toast.makeText(this,"Welcome "+editname.getText().toString()+"!",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public void showSoftKeyboard(View view){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
           }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}