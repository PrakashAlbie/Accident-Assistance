package com.example.android.health;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Invite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
    }


    public void click(View v) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "YOUR ARE INVITED TO TRY OUR APPLICATION eATsOON WITH THIS LINK...www.eatsoon.org.in");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

