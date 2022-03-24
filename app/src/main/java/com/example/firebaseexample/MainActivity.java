package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText userName;
    private EditText email;
    private Button addDetails;
    private Button logoutBtn;

    private int userCount = 0;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.add_name_editText);
        email = findViewById(R.id.add_email_editText);
        addDetails = findViewById(R.id.add_name_btn);
        
        addDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userNameEntered = userName.getText().toString();
                String emailEntered = email.getText().toString();
                if(TextUtils.isEmpty(userNameEntered) || TextUtils.isEmpty(emailEntered)) {
                    Toast.makeText(MainActivity.this, "Don't leave fields blank!", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> userDetails = new HashMap<>();
                    userDetails.put("Name", userNameEntered);
                    userDetails.put("Email", emailEntered);
                    FirebaseDatabase.getInstance().getReference()
                            .child("Information").child("User_" + ++userCount).updateChildren(userDetails);
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

        listView = findViewById(R.id.details_list_view);

        final ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference().child("Information");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot currDetails: snapshot.getChildren()) {
                    Information currUserInfo = currDetails.getValue(Information.class);
                    String text = currUserInfo.getName() + " : " + currUserInfo.getEmail();
                    list.add(text);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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