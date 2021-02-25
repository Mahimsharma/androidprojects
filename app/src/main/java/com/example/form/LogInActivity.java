package com.example.form;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private static GoogleSignInClient googleSignInClient;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button buttonGoogleSignIn;
    private Button buttonContinueAsGuest;
    private int RC_SIGN_IN = 1;

    private SharedPreferences sharedPreferences;
    @Override
    public void onStart() {
        updateUI();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("NAME", Context.MODE_PRIVATE);

        buttonGoogleSignIn = findViewById(R.id.buttonGoogleSignIn);
        buttonContinueAsGuest = findViewById(R.id.buttonContinueAsGuest);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);

        buttonGoogleSignIn.setOnClickListener(this);
        buttonContinueAsGuest.setOnClickListener(this);

    }

    private void updateUI() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        String name="";
        if(account!= null || sharedPreferences.getString("name",null) != null){
            if(account!=null){
            name = account.getDisplayName();
            }
            if(sharedPreferences.getString("name",null) != null) {
                name = sharedPreferences.getString("name",null);
            }
            Intent intent = new Intent(this,HomeScreen.class);
            startActivity(intent);
            Toast.makeText(LogInActivity.this,"Welcome "+name+"!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.buttonGoogleSignIn:
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,RC_SIGN_IN);
                break;
            case R.id.buttonContinueAsGuest:
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try{
                GoogleSignInAccount acc = task.getResult(ApiException.class);
                FirebaseGoogleAuth(acc);

            }catch (ApiException e){
                Toast.makeText(this,"Couldn't complete your request. PLease try again later!",Toast.LENGTH_SHORT).show();
                FirebaseGoogleAuth(null);
                Log.e("Exception", "onActivityResult: ",e);
            }
        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acc) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acc.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() || task.isComplete()){
                    Toast.makeText(LogInActivity.this,"Signed in successfully!",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    Intent intent = new Intent(LogInActivity.this, RegistrationActivity.class);
                    intent.putExtra("name", account.getDisplayName());
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LogInActivity.this,"Couldn't complete your request. PLease try again later!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static void signOut(){
        mAuth.signOut();
        googleSignInClient.signOut();
    }

}

