package com.example.user.ecosnap;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;

    private Boolean isMapsFabOpen = false,isImageFabOpen=false;

    private FloatingActionButton maps_fab,image_fab,feed_fab,recent_map,migration_map,geography_map,alien_map,camera,gallary;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maps_fab= (FloatingActionButton) findViewById(R.id.maps);
        image_fab= (FloatingActionButton) findViewById(R.id.image);
        feed_fab= (FloatingActionButton) findViewById(R.id.feed);
        recent_map= (FloatingActionButton) findViewById(R.id.recent);
        migration_map= (FloatingActionButton) findViewById(R.id.migration);
        alien_map= (FloatingActionButton) findViewById(R.id.alien);
        geography_map= (FloatingActionButton) findViewById(R.id.geography);
        camera= (FloatingActionButton) findViewById(R.id.camera);
        gallary= (FloatingActionButton) findViewById(R.id.gallary);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);


        maps_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(isMapsFabOpen){
                    maps_fab.startAnimation(rotate_backward);
                    recent_map.startAnimation(fab_close);
                    geography_map.startAnimation(fab_close);
                    alien_map.startAnimation(fab_close);
                    migration_map.startAnimation(fab_close);

                    recent_map.setClickable(false);
                    geography_map.setClickable(false);
                    alien_map.setClickable(false);
                    geography_map.setClickable(false);
                    isMapsFabOpen = false;


                } else {

                    maps_fab.startAnimation(rotate_forward);
                    recent_map.startAnimation(fab_open);
                    geography_map.startAnimation(fab_open);
                    alien_map.startAnimation(fab_open);
                    migration_map.startAnimation(fab_open);

                    recent_map.setClickable(true);
                    geography_map.setClickable(true);
                    alien_map.setClickable(true);
                    migration_map.setClickable(true);
                    isMapsFabOpen = true;
                }



            }
        });

        image_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isImageFabOpen){

                    image_fab.startAnimation(rotate_backward);
                    camera.startAnimation(fab_close);
                    gallary.startAnimation(fab_close);
                    camera.setClickable(false);
                    gallary.setClickable(false);
                    isImageFabOpen = false;


                } else {

                    image_fab.startAnimation(rotate_forward);
                    camera.startAnimation(fab_open);
                    gallary.startAnimation(fab_open);
                    camera.setClickable(true);
                    gallary.setClickable(true);
                    isImageFabOpen = true;
                }

            }
        });

        feed_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("species");
                myRef.child("Name").setValue("Wishnu").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Sent data",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, RESULT_LOAD_IMAGE);

                }

            }
        });
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        recent_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maps_intent=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(maps_intent);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            //ImageView imageView = (ImageView) findViewById(R.id.imageView4);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));


        }


    }



}
