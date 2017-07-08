package com.google.firebase.udacity.friendlychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.udacity.Model_class.Group_user_Id;
import com.google.firebase.udacity.Model_class.Maa;
import com.google.firebase.udacity.Model_class.people;
import com.google.firebase.udacity.Model_class.user;

import java.util.ArrayList;

public class Enter_Group extends AppCompatActivity {


    private EditText minsert,minsert2;
    private Button msave;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String name,id,chk;
    ProgressDialog mProgressDialog;
    private Integer flag;
    private ArrayList stock_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter__group);
        stock_list = new ArrayList<String>();
        stock_list=getIntent().getStringArrayListExtra("already");
        minsert=(EditText)findViewById(R.id.editText);
        minsert2=(EditText)findViewById(R.id.editText3);
        msave=(Button)findViewById(R.id.button2);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = minsert.getText().toString();
                id = minsert2.getText().toString();
                chk=name+id;

                if (name.isEmpty() && id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "GroupName And Group Key Should not be Empty", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "GroupName Should not be Empty", Toast.LENGTH_SHORT).show();
                } else if (id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Group Key Should not be Empty", Toast.LENGTH_SHORT).show();
                }

                else {

                    chk=name+id;
                    Query query = FirebaseDatabase.getInstance().getReference().child("Groups").orderByChild("qery").equalTo(chk);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0){


                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                user us=new user();
                                us.setId(id);
                                us.setName(name);
                                Maa maa=new Maa();
                                maa.setId(id);
                                maa.setNnmm(name);
                                String que=name+id;
                                maa.setQery(que);
                                databaseReference.child("Groups").push().setValue(maa);
                                databaseReference.child("user").child(userId).push().setValue(us);
                                Toast.makeText(getApplicationContext(), "Successfully Enterd in Group", Toast.LENGTH_SHORT).show();
                                String a = name + "mice";
                                Group_user_Id uid=new Group_user_Id();
                                uid.setUser_id(userId);
                                uid.setAdmin("Not");
                                databaseReference.child("UserId").child(que+"mice").push().setValue(uid);
                                people p = new people();
                                p.setPop("Annoni");
                                databaseReference.child("grouppeople").child(a).push().setValue(p);
                                Intent i=new Intent(Enter_Group.this,Main3Activity.class);
                                i.putExtra("message", que);
                                i.putExtra("name",name);
                                startActivity(i);



                            }
                            else {




                                Toast.makeText(getApplicationContext(), "Groupname Doesn't Exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }
        });





    }

}
