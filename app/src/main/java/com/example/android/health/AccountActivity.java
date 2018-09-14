package com.example.android.health;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;




public class AccountActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    AlertDialog.Builder connectivity_alert;
    FloatingActionButton floatingActionButton;

    MediaPlayer mediaplayer;
    Vibrator vibrator;

    private ZXingScannerView scan_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_menu);

        floatingActionButton=(FloatingActionButton) findViewById(R.id.fab);

        mediaplayer= MediaPlayer.create(this,R.raw.beep);
        vibrator=(Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                AccountActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(AccountActivity.this);




    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_help:

                break;
            case R.id.nav_about:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_logout:
                Intent in=new Intent(AccountActivity.this,MainActivity.class);
                startActivity(in);
                break;
            case R.id.nav_invite:
                Intent i = new Intent(AccountActivity.this, Invite.class);
                startActivity(i);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public  void  scan(View view)
    {

        scan_view=new ZXingScannerView(this);
        scan_view.setResultHandler(new ZXingScannerResultHandler());

        setContentView(scan_view);
        scan_view.startCamera();
    }
    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler
    {
        @Override
        public void handleResult(Result result) {

            //Toast.makeText(Add_Item.this,""+result.getText(),Toast.LENGTH_SHORT).show();

            mediaplayer.start();
            vibrator.vibrate(90);
            scan_view.stopCamera();
            Toast.makeText(AccountActivity.this,""+result.getText(),Toast.LENGTH_LONG).show();
            Intent intent=new Intent(AccountActivity.this,Emergency_Details.class);
            intent.putExtra("ID",result.getText());

            startActivity(intent);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //Toast.makeText(Update_Product_Details.this,"Back Prohibitted",Toast.LENGTH_SHORT).show();
            Intent nxt=new Intent(Intent.ACTION_MAIN);
            nxt.addCategory(Intent.CATEGORY_HOME);
            nxt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(nxt);
        }
        return super.onKeyDown(keyCode, event);
    }
}
