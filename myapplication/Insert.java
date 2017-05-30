package com.example.siddique.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Insert extends AppCompatActivity {
    private EditText minsert;
    private Button msave;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        minsert=(EditText)findViewById(R.id.editText);
        msave=(Button)findViewById(R.id.button2);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              String name=minsert.getText().toString();
                user us=new user();
                us.setName(name);
                databaseReference.child("user").push().setValue(us);
                Toast.makeText(getApplicationContext(), "Data Store Successfully", Toast.LENGTH_SHORT).show();



            }
        });
    }
}
