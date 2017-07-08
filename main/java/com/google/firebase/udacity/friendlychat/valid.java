package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class valid extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,mref;
    private String userId,grname,grid;
    private EditText name,id;
    private Button chk;
    private TextView n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid);
        Button bc=(Button)findViewById(R.id.button4);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        name=(EditText)findViewById(R.id.editText);
        id=(EditText)findViewById(R.id.editText3);
        chk=(Button)findViewById(R.id.button2);
        n=(TextView)findViewById(R.id.textView4);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                grname=name.getText().toString();
                grid=id.getText().toString();
                String chk=grname+grid;
                Query query = FirebaseDatabase.getInstance().getReference().child("Groups").orderByChild("qery").equalTo(chk);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            // 1 or more users exist which have the username property "usernameToCheckIfExists"
                            Toast.makeText(getApplicationContext(), "Groupname Already Exist", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Succssfull", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });
        mref=firebaseDatabase.getReference();
        Query lastQuery = mref.child("groupchat").child("Ibrahimlla").orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    String message = childSnapShot.child("chat").getValue().toString();
                    n.setText(message);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void test(View view){
        Intent i=new Intent(this,Floating_a.class);
        startActivity(i);
    }
}
