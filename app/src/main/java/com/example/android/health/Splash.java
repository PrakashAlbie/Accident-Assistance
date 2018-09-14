package com.example.android.health;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity
{
    ImageView imgv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgv = (ImageView) findViewById(R.id.img);

        Animation an = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        imgv.startAnimation(an);

        final Intent i = new Intent(this,MainActivity.class);

        Thread th = new Thread(){
            public void run()
            {
                try
                {
                    sleep(6800);
                }
                catch(Exception e)
                {
                    System.out.println("Exp" + e.getStackTrace());
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };

        th.start();
    }

}


