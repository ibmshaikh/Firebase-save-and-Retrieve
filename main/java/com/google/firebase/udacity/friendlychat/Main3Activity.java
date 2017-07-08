package com.google.firebase.udacity.friendlychat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.udacity.Model_class.LastMessage;
import com.google.firebase.udacity.Model_class.group;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    private String mm,nn;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ListView list;
    private Button msave;
    private EditText editText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseListAdapter<group> mlist;
    private String userid;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        userid= FirebaseAuth.getInstance().getCurrentUser().getUid();

        arrayList = new ArrayList<String>();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        list = (ListView) findViewById(R.id.listView);
        // arrayList = new ArrayList<String>();
        //adapter = new ArrayAdapter<String>(this,
        //     android.R.layout.simple_list_item_1, android.R.id.text1, arrayList);
        //list.setAdapter(adapter);
        Bundle data_from_list= getIntent().getExtras();

        mm = data_from_list.getString("message");
        String mkv = data_from_list.getString("name");
        editText = (EditText) findViewById(R.id.textToSend);
        getSupportActionBar().setTitle("    "+mkv+"  >");

        final Button aa = (Button)findViewById(R.id.textView3);
        aa.setEnabled(true);
        aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference();
                String ed = editText.getText().toString();
                if (ed.length() == 0) {

                } else {
                    editText.setText("");
                    group gp = new group();
                    gp.setChat(ed);
                    gp.setSender(userid);
                    LastMessage lm=new LastMessage();
                    lm.setLastm(ed);
                    databaseReference.child("LastMessage").child(mm).setValue(lm);
                    databaseReference.child("groupchat").child(mm).push().setValue(gp);


                }

            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference = firebaseDatabase.getReference().child("groupchat").child(mm);
        databaseReference.keepSynced(true);
        mlist=new FirebaseListAdapter<group>(this,group.class,R.layout.chatui,databaseReference) {
            @Override
            protected void populateView(View v, group model, int position) {

                String userName = sharedPref.getString("userName", "Not Available");
                TextView name=(TextView)v.findViewById(R.id.MessageListItemText);
                String uuid=model.getSender();
                String b=model.getUsname();
                if (uuid==userid)
                {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)name.getLayoutParams();
                    name.setText(model.getChat());
                    name.setBackground(getDrawable(R.drawable.bubble_right_green));
                    name.setGravity(Gravity.RIGHT);

                }
                else {


                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)name.getLayoutParams();
                    name.setText(model.getChat());
                    name.setBackground(getDrawable(R.drawable.bubble_right_green));

                    name.setGravity(Gravity.LEFT);



                }



            }
        };

        list.setAdapter(mlist);
        mlist.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.nn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.grp:
                //sign out

                String a=(mm+"mice");
                Intent ij=new Intent(this,GroupUser.class);
                ij.putExtra("message",a);
                startActivity(ij);
                return true;
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
