package com.example.android.health;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    DatabaseReference database_reference;
    Button login;
    TextView error_message;
    AlertDialog.Builder alert;

    String user="Sundeep0412";
    ArrayList<String> userdetails=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        error_message=(TextView) findViewById(R.id.errormessage);



        final TextView signup=(TextView) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup_screen=new Intent(Login.this,Signup.class);
                startActivity(signup_screen);

            }
        });



        try
        {


            database_reference= FirebaseDatabase.getInstance().getReference("USERNAME_PASSWORD");
            database_reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot child:dataSnapshot.getChildren())

                    {
                        String x=child.getValue(String.class);
                        userdetails.add(x);
                    }




                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch (Exception e)
        {
            Toast.makeText(Login.this,"error",Toast.LENGTH_SHORT).show();
        }


        //----------------------------------------------------------------------------------------


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(am_i_connected())
                {

                    String a = username.getText().toString().trim();
                    String b = password.getText().toString().trim();
                    String ab = a + b;
                    if(TextUtils.isEmpty(a))
                    {
                        error_message.setVisibility(TextView.VISIBLE);
                        error_message.setText("Enter Username and Password ");
                    }


                    else if (userdetails.contains(ab)) {
                        error_message.setVisibility(TextView.VISIBLE);
                        Toast.makeText(Login.this, "success", Toast.LENGTH_SHORT).show();
                        error_message.setText("Successfully Logined");
                        Intent h= new Intent(Login.this,AccountActivity.class);
                        startActivity(h);


                    } else {
                        error_message.setVisibility(TextView.VISIBLE);
                        Toast.makeText(Login.this, "dosent match", Toast.LENGTH_SHORT).show();
                        error_message.setText("Username and Password doesnt match");
                    }

                }
                else{

                    alert=new AlertDialog.Builder(Login.this);
                    alert.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                            .setTitle("No Internet Connection")
                            .create();
                    alert.show();

                }
            }
        });


    }

    public boolean am_i_connected()
    {
        ConnectivityManager con=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=con.getActiveNetworkInfo();

        return info!=null&&info.isConnected();
    }

}
