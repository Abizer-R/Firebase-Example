package com.example.firebaseexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private Button addNameBtn;
    private Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.add_name_editText);
        addNameBtn = findViewById(R.id.add_name_btn);
        
        addNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameEntered = userName.getText().toString();
                if(TextUtils.isEmpty(userNameEntered)) {
                    Toast.makeText(MainActivity.this, "No name entered!", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Rampurawala's").push().child("Name").setValue(userNameEntered);
                }
            }
        });
        
        logoutBtn = findViewById(R.id.main_activity_logout_button);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, StartActivity.class));
            }
        });

//        addSingleValue();
//
//        addMultipleValues();

    }

    private void addSingleValue() {
        FirebaseDatabase.getInstance().getReference()
                .child("Rampurawala's").child("Abizer").setValue("19");
    }

    private void addMultipleValues() {
        HashMap<String,  Object> map = new HashMap<>();
        map.put("Name", "Abizer");
        map.put("Age", "19");
        map.put("Email", "rabizeridris@gmail.com");

        FirebaseDatabase.getInstance().getReference()
                .child("Rampurawala's").child("More Details").updateChildren(map);
    }
}