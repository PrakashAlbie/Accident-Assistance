package com.example.android.health;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Emergency_Details extends AppCompatActivity {

    String ID;
    TextView uniqueid,name,age,contact1,contact2,contact3,locality,liveLocation,blood;
    ImageView img;
    ArrayList<String> user_data=new ArrayList<>();
    DatabaseReference database_reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency__details);


        ID=getIntent().getStringExtra("ID");

        //uniqueid=(TextView)findViewById(R.id.textViewUniqueid);
        //locality=(TextView)findViewById(R.id.textViewLocality);
        liveLocation=(TextView)findViewById(R.id.textViewLiveLocation);
        name=(TextView)findViewById(R.id.textViewName);
        age=(TextView)findViewById(R.id.textViewAge);
        contact1=(TextView)findViewById(R.id.textViewContact1);
        contact2=(TextView)findViewById(R.id.textViewContact2);
        contact3=(TextView)findViewById(R.id.textViewContact3);
        img=(ImageView)findViewById(R.id.img);
        blood=(TextView)findViewById(R.id.textViewBlood);

                database_reference= FirebaseDatabase.getInstance().getReference("USER DATA").child(ID);
        database_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child:dataSnapshot.getChildren() )
                {
                    String usrs=child.getValue(String.class);
                    user_data.add(usrs);
                   // Toast.makeText(Emergency_Details.this,""+usrs,Toast.LENGTH_LONG).show();

                }
                age.setText(user_data.get(0));
                blood.setText(user_data.get(1));
                //uniqueid.setText(ID);
                name.setText(user_data.get(17));
                contact1.setText(user_data.get(2));
                contact2.setText(user_data.get(3));
                contact3.setText(user_data.get(4));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });












    }

}
