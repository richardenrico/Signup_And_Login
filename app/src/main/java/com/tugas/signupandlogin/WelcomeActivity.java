package com.tugas.signupandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }


    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }

    public void to_Login_Page(View view) {
        Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void to_Signup_Page(View view) {
        Intent i = new Intent(WelcomeActivity.this, RegisterActivity.class);
        startActivity(i);
        finish();
    }
}