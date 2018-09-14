package com.example.android.health;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Signup_2 extends AppCompatActivity {


    Button bt;
    EditText ed1, ed2, ed3, ed4, ed5, ed6;
    String e1, edn2, edn3, edc1, edc2, edc3;
    DatabaseReference databaseReference;
    ArrayList<String> available_user = new ArrayList<>();
    String ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_2);

        ab=getIntent().getStringExtra("USER");


        databaseReference = FirebaseDatabase.getInstance().getReference("USER DETAILS");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String usrs = child.getValue(String.class);
                    available_user.add(usrs);
                    Toast.makeText(Signup_2.this, "" + usrs, Toast.LENGTH_LONG).show();
//                    number_of_users=dataSnapshot.getChildrenCount();
//                    count=Long.toString(number_of_users);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Signup_2.this, "Database Storing Failed", Toast.LENGTH_LONG).show();

            }
        });

        bt = (Button) findViewById(R.id.buttonNext);
        ed1 = (EditText) findViewById(R.id.editTextName1);
        ed2 = (EditText) findViewById(R.id.editTextName2);
        ed3 = (EditText) findViewById(R.id.editTextName3);
        ed4 = (EditText) findViewById(R.id.editcontact1);
        ed5 = (EditText) findViewById(R.id.editcontact2);
        ed6 = (EditText) findViewById(R.id.editcontact3);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (am_i_connected()) {

                    e1 = ed1.getText().toString().trim();
                    edn2 = ed2.getText().toString().trim();
                    edn3 = ed3.getText().toString().trim();
                    edc1 = ed4.getText().toString().trim();
                    edc2 = ed5.getText().toString().trim();
                    edc3 = ed6.getText().toString().trim();

                    if (TextUtils.isEmpty(e1)) {
                        Toast.makeText(Signup_2.this, "Enter Name 1", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edc1)) {
                        Toast.makeText(Signup_2.this, "Enter Contact 1", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edn2)) {
                        Toast.makeText(Signup_2.this, "Enter Name 2", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edc2)) {
                        Toast.makeText(Signup_2.this, "Enter  Contact 2", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edn3)) {
                        Toast.makeText(Signup_2.this, "Enter Name 3", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(edc3)) {
                        Toast.makeText(Signup_2.this, "Enter  Contact 3", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            databaseReference = FirebaseDatabase.getInstance().getReference("USER DATA").child(ab);
                            databaseReference.child("Name1").setValue(e1);
                            databaseReference.child("Contact1").setValue(edc1);
                            databaseReference.child("Name2").setValue(edn2);
                            databaseReference.child("Contact2").setValue(edc2);
                            databaseReference.child("Name3").setValue(edn3);
                            databaseReference.child("Contact3").setValue(edc3);

//                    Intent i = new Intent(Signup_2.this, Signup_2.class);
//                    startActivity(i);
                            Intent id = new Intent(Signup_2.this, Qr_code.class);
                            id.putExtra("QR_DETAILS", ab);
                            startActivity(id);

                        } catch (Exception e) {

                            Toast.makeText(Signup_2.this, "Error", Toast.LENGTH_LONG).show();

                        }
                    }
                } else {
                    Toast.makeText(Signup_2.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }
            }


        });
    }


    public boolean am_i_connected() {
        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = con.getActiveNetworkInfo();

        return info != null && info.isConnected();
    }
}
