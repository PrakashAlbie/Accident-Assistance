package com.example.android.health;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Signup_1 extends AppCompatActivity {

    Spinner sp;
    AlertDialog.Builder connectivity_alert;
    TextView txt;
    EditText ed1,ed2,ed3,ed4;
    ImageView img;
    Button bt;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private ProgressDialog mprogress;
    StorageReference storage_reference;
    String e1,e2,e3,e4,e5;
    String product_picture_url="https://firebasestorage.googleapis.com/v0/b/eatsoon-101c5.appspot.com/o/PHOTO%2Fno_image.png?alt=media&token=6ac1a6cc-643d-425a-9fc5-d7416d7e4bec";


    ArrayList<String> available_user=new ArrayList<>();
    String ab;
    String count;
    Long number_of_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_1);

        ab=getIntent().getStringExtra("USER");

//
        databaseReference=FirebaseDatabase.getInstance().getReference("USER DETAILS");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child:dataSnapshot.getChildren() )
                {
                    String usrs=child.getValue(String.class);
                    available_user.add(usrs);
                    //Toast.makeText(Signup_1.this,""+usrs,Toast.LENGTH_LONG).show();
//                    number_of_users=dataSnapshot.getChildrenCount();
//                    count=Long.toString(number_of_users);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Signup_1.this,"Database Storing Failed",Toast.LENGTH_LONG).show();

            }
        });

        sp = (Spinner) findViewById(R.id.spinnerStates);
        img = (ImageView) findViewById(R.id.img1);
        txt = (TextView) findViewById(R.id.textView);
        bt = (Button) findViewById(R.id.nextPage);
        ed1=(EditText)findViewById(R.id.editTextDoorno);
        ed2=(EditText)findViewById(R.id.editTextStreet);
        ed3=(EditText)findViewById(R.id.editTextDistrict);
        ed4=(EditText)findViewById(R.id.editTextPincode);

        storage_reference= FirebaseStorage.getInstance().getReference();
        mprogress=new ProgressDialog(this);



        String[] states = {"Tamilnadu","Andhra Pradesh","Kerala","Karnataka","Mumbai","Delhi","Kolkatta","Uttar Pradesh","Jharkhand","Madhya Pradesh","Orissa"};
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,states);
        sp.setAdapter(ad);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(am_i_connected()) {


                    e1 = ed1.getText().toString().trim();
                    e2 = ed2.getText().toString().trim();
                    e3 = ed3.getText().toString().trim();
                    e4 = ed4.getText().toString().trim();
                    e5 = sp.getSelectedItem().toString();

                    if (TextUtils.isEmpty(e1)) {
                        Toast.makeText(Signup_1.this, "Enter Door No", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(e2)) {
                        Toast.makeText(Signup_1.this, "Enter Street", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(e3)) {
                        Toast.makeText(Signup_1.this, "Enter District", Toast.LENGTH_LONG).show();
                    } else if (TextUtils.isEmpty(e4)) {
                        Toast.makeText(Signup_1.this, "Enter Pincode", Toast.LENGTH_LONG).show();
                    } else {
                        try {
                            databaseReference = FirebaseDatabase.getInstance().getReference("USER DATA").child(ab);
                            databaseReference.child("Door No").setValue(e1);
                            databaseReference.child("Street").setValue(e2);
                            databaseReference.child("District").setValue(e3);
                            databaseReference.child("Pincode").setValue(e4);
                            databaseReference.child("State").setValue(e5);
                            databaseReference.child("photo url").setValue(product_picture_url);

                            Intent i = new Intent(Signup_1.this, Signup_2.class);
                            i.putExtra("USER",ab);
                            startActivity(i);
                        } catch (Exception e) {

                            Toast.makeText(Signup_1.this, "Error", Toast.LENGTH_LONG).show();

                        }
                    }
                }

                else
                {
                    Toast.makeText(Signup_1.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                }

            }
        });
        }

    public void photo()
    {
        mprogress=new ProgressDialog(this);

        storage_reference= FirebaseStorage.getInstance().getReference();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectivity_alert=new AlertDialog.Builder(Signup_1.this);
                connectivity_alert.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 1);
                    }
                }).setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                    }
                }).setTitle("IMAGE PICKER").create();
                connectivity_alert.show();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:


                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    img.setImageURI(selectedImage);
                    img.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    mprogress.setMessage("Uploading...");

                    mprogress.show();
                    StorageReference file_path=storage_reference.child("PHOTO").child(selectedImage.getLastPathSegment());
                    file_path.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mprogress.dismiss();
                            Uri download_url=taskSnapshot.getDownloadUrl();
                            product_picture_url=download_url.toString();
                            Picasso.with(Signup_1.this).load(download_url).fit().centerCrop().into(img);
                        }
                    });
                }
                break;
        }
    }

    public boolean am_i_connected()
    {
        ConnectivityManager con=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info=con.getActiveNetworkInfo();

        return info!=null&&info.isConnected();
    }
}
