package com.example.firebaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginBtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_activity_email);
        password = findViewById(R.id.login_activity_password);
        loginBtn = findViewById(R.id.login_activity_login_button);

        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();

                if(TextUtils.isEmpty(emailEntered) || TextUtils.isEmpty(passwordEntered)) {
                    Toast.makeText(LoginActivity.this, "Please don't leave the fields empty", Toast.LENGTH_SHORT).show();
                } else if(passwordEntered.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password should consist at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailEntered, passwordEntered);
                }
            }
        });
    }

    private void loginUser(String emailEntered, String passwordEntered) {
        auth.signInWithEmailAndPassword(emailEntered, passwordEntered)
                .addOnSuccessListener(LoginActivity.this, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }
}