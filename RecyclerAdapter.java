package com.example.ibm.hermes.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibm.hermes.Main3Activity;
import com.example.ibm.hermes.Messaging_Services.Chat_Activity;
import com.example.ibm.hermes.Model_class.LastMessage;
import com.example.ibm.hermes.Model_class.user;
import com.example.ibm.hermes.R;
import com.example.ibm.hermes.contact;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends AppCompatActivity {


    private RecyclerView mUserlist;
    private DatabaseReference muserref,mlastmessage,mimage,mref;
    private FirebaseRecyclerAdapter madapter;
    private ArrayList<String> arrau;
    private String val,timea;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_adapter);
        arrau=new ArrayList<String>();
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        muserref= FirebaseDatabase.getInstance().getReference().child("user").child(uid);
        Button b=(Button)findViewById(R.id.button8);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                madapter.notifyItemMoved(1,0);
            }
        });

        mUserlist=(RecyclerView)findViewById(R.id.recyler);
        mUserlist.setHasFixedSize(true);
        LinearLayoutManager lm=new LinearLayoutManager(this);
        mUserlist.setLayoutManager(lm);

        madapter= new FirebaseRecyclerAdapter<user, UserViewHolder>(user.class,R.layout.activity_dialogs_list,UserViewHolder.class,muserref) {
            @Override
            protected void populateViewHolder(final UserViewHolder viewHolder, user model, final int position) {

                final String ais=model.getName();
                viewHolder.setName(model.getName());
                final  String b=model.getQuery();
                mref=FirebaseDatabase.getInstance().getReference().child("LastMessage").child(b);
                mref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                            HashMap<String, Object> hashmap = (HashMap) dataSnapshot.getValue();
                             val = (String) hashmap.get("lastm");
                             timea = (String) hashmap.get("time");

                             change(position);
                             viewHolder.setLastmessage(val);
                             viewHolder.setLastTime(timea);


                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                mimage=FirebaseDatabase.getInstance().getReference().child("Image").child(b);
                mimage.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {

                            HashMap<String, Object> hashmap = (HashMap) dataSnapshot.getValue();
                            final String vala = (String) hashmap.get("image");
                            viewHolder.setImage(vala);
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(RecyclerAdapter.this, Chat_Activity.class);
                        i.putExtra("message",b);
                        i.putExtra("name",ais);
                        startActivity(i);
                    }
                });
            }
        };
        mUserlist.setAdapter(madapter);
        madapter.notifyDataSetChanged();
    }

    private void change(int position) {
        madapter.notifyItemMoved(2,0);
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder{

        View mview;

        public UserViewHolder(View itemView) {
            super(itemView);
            mview=itemView;

        }
        public void setName(String name){
            TextView username=(TextView)mview.findViewById(R.id.dialogName);
            username.setText(name);
        }

        public void setLastmessage(String lastmessage) {
            TextView lastmessa=(TextView)mview.findViewById(R.id.dialogLastMessage);
            lastmessa.setText(lastmessage);


        }

        public void setLastTime(String lastTime) {
            TextView time=(TextView)mview.findViewById(R.id.dialogDate);
            time.setText(lastTime);
        }

        public void setImage(String image) {
            CircleImageView cm=(CircleImageView)mview.findViewById(R.id.dialogAvatar);
            Context cont=mview.getContext();
            Picasso.with(cont).load(image).into(cm);
        }

    }

}
