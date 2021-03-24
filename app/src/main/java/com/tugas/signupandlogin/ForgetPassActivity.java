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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPassActivity extends AppCompatActivity {

    EditText emailid;
    Button btn_next;
    FirebaseAuth mFirebaseAuth;
    ImageView back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailid = findViewById(R.id.email);
        btn_next = findViewById(R.id.next_btn);

        back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgetPassActivity.this, WelcomeActivity.class);
                startActivity(i);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();

                if (!isEmailValid(email)){
                    emailid.setError("Please input a valid email address");
                    emailid.requestFocus();
                } else {
                    mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPassActivity.this, "A reset link has been sent to " + email, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ForgetPassActivity.this, LoginActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(ForgetPassActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currentUser){

        if(currentUser != null){
            Toast.makeText(this,"You have successfully signed in",Toast.LENGTH_LONG).show();
            startActivity(new Intent(ForgetPassActivity.this, HomeActivity.class));
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ForgetPassActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }
}