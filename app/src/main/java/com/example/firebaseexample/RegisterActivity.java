package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button registerBtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.register_activity_email);
        password = findViewById(R.id.register_activity_password);
        registerBtn = findViewById(R.id.register_activity_register_button);

        auth = FirebaseAuth.getInstance();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String emailEntered = email.getText().toString();
                String passwordEntered = password.getText().toString();
                
                if(TextUtils.isEmpty(emailEntered) || TextUtils.isEmpty(passwordEntered)) {
                    Toast.makeText(RegisterActivity.this, "Please don't leave the fields empty", Toast.LENGTH_SHORT).show();
                } else if(passwordEntered.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should consist at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(emailEntered, passwordEntered);
                }
            }
        });
    }

    private void registerUser(String emailEntered, String passwordEntered) {
        auth.createUserWithEmailAndPassword(emailEntered, passwordEntered)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}