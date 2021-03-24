package com.tugas.signupandlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText emailid, pass;
    Button signin;
    TextView tvdaftar, tvforgot;
    FirebaseAuth mFirebaseAuth;
    ImageView back_icon;
    CheckBox checkBox_remember;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_EMAIL = "Email";
    private static final String PREF_PASSWORD = "Password";
    private static final String PREF_REMEMBER = "Remember";

    private final String defaultEmailValue = "";
    private final String defaultPassValue = "";
    private final boolean defaultRememberValue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailid = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        signin = findViewById(R.id.button);
        tvdaftar = findViewById(R.id.daftar);

        checkBox_remember = findViewById(R.id.remember_checkbox);

        back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(i);
            }
        });

        tvforgot = findViewById(R.id.forgot_tv);
        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(i);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String password = pass.getText().toString();

                if (email.isEmpty()) {
                    emailid.setError("Please input your email address");
                    emailid.requestFocus();
                } else if (password.isEmpty()) {
                    pass.setError("Please input your password");
                    pass.requestFocus();
                } else if (!isEmailValid(email)) {
                    emailid.setError("Please input a valid email");
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill the empty space !", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (checkBox_remember.isChecked()){
                                    savedPreferences(email, password, true);
                                } else {
                                    savedPreferences(null, null, false);
                                }
                                Intent intoHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intoHome);
                            } else {
                                Toast.makeText(LoginActivity.this, "Login error, please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "An error has occurred, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvdaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoDftr = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intoDftr);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPreferences();
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void savedPreferences(String emailVal, String passVal, Boolean rememberVal) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString(PREF_EMAIL, emailVal);
        editor.putString(PREF_PASSWORD, passVal);
        editor.putBoolean(PREF_REMEMBER, rememberVal);

        editor.commit();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String emailVal = settings.getString(PREF_EMAIL, defaultEmailValue);
        String passVal = settings.getString(PREF_PASSWORD, defaultPassValue);
        Boolean rememberVal = settings.getBoolean(PREF_REMEMBER, defaultRememberValue);

        emailid.setText(emailVal);
        pass.setText(passVal);
        checkBox_remember.setChecked(rememberVal);
    }
}
