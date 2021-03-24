package com.tugas.signupandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    EditText emailid, pass, repeat_pass;
    Button daftar;
    TextView login;
    FirebaseAuth mFirebaseAuth;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        repeat_pass = findViewById(R.id.re_enter_password);
        daftar = findViewById(R.id.button);
        login = findViewById(R.id.login);

        back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, WelcomeActivity.class);
                startActivity(i);
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String pwd = pass.getText().toString();
                String re_pwd = repeat_pass.getText().toString();

                if (email.isEmpty()) {
                    emailid.setError("Please input your email address");
                    emailid.requestFocus();
                } else if (!isEmailValid(email)) {
                    emailid.setError("Please input a valid email");
                    emailid.requestFocus();
                } else if (pwd.isEmpty()) {
                    pass.setError("Please input your password");
                    pass.requestFocus();
                } else if (re_pwd.isEmpty()){
                    repeat_pass.setError("Please re-input your password");
                    repeat_pass.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill the empty space !", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    if (pwd.equals(re_pwd)) {
                        mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Sign up error, please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        repeat_pass.setError("The password are not the same!");
                        repeat_pass.requestFocus();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "An error has occurred, please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(RegisterActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}