package com.google.firebase.udacity.friendlychat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.udacity.Model_class.LastMessage;
import com.google.firebase.udacity.Model_class.people;
import com.google.firebase.udacity.Model_class.user;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private ListView list;
    private FirebaseDatabase firebaseDatabase, md;
    private DatabaseReference databaseReference, mref;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList,namelist;
    private String name,iid, data, mm,mx,both;
    private FirebaseListAdapter<user> mlist;
    private FirebaseListAdapter<LastMessage>llist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run your one time code here
            databaseSetup();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        list = (ListView) findViewById(R.id.rview);
        namelist = new ArrayList<String>();
        arrayList = new ArrayList<String>();
        //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        //list.setAdapter(adapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference().child("user").child(userId);


        mlist = new FirebaseListAdapter<user>(this, user.class, R.layout.mm, databaseReference) {
            @Override
            protected void populateView(View v, user model, int position) {
                ImageView img = (ImageView) v.findViewById(R.id.img);
                TextView name = (TextView) v.findViewById(R.id.secondLine);
                TextView id = (TextView) v.findViewById(R.id.firstLine);
                name.setText(model.getName());
                img.setImageResource(R.drawable.dp);
                namelist.add(model.getName());
                 both=model.getName()+model.getId();
                arrayList.add(both);

            }
        };
        list.setAdapter(mlist);
        mlist.notifyDataSetChanged();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent ii = new Intent(view.getContext(), Main3Activity.class);
                ii.putExtra("message", arrayList.get(position));
                ii.putExtra("name",namelist.get(position));
                startActivity(ii);
            }
        });




    }





    public void insert(View view){
        Intent i=new Intent(this,Insert.class);
        i.putStringArrayListExtra("already",arrayList);

        startActivity(i);


    }


    public void enter(View view){
        Intent i=new Intent(this,Enter_Group.class);
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log:
                //sign out
                AuthUI.getInstance().signOut(this);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void databaseSetup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Enter UserName");
        final EditText input_username = new EditText(this);
        alert.setView(input_username);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String username = input_username.getText().toString();

                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("userName",username);
                editor.commit();
                dialogInterface.dismiss();
            }
        });
        alert.setCancelable(false);
        alert.show();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}