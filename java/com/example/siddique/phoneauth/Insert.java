package com.example.siddique.phoneauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Insert extends AppCompatActivity {
    private EditText minsert,minsert2;
    private Button msave;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        minsert=(EditText)findViewById(R.id.editText);
        minsert2=(EditText)findViewById(R.id.editText3);
        msave=(Button)findViewById(R.id.button2);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=minsert.getText().toString();
                String id=minsert2.getText().toString();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                user us=new user();
                us.setName(name);
                databaseReference.child("user").child(userId).push().setValue(us);
                Toast.makeText(getApplicationContext(), "Data Store Successfully", Toast.LENGTH_SHORT).show();



            }
        });
    }
}