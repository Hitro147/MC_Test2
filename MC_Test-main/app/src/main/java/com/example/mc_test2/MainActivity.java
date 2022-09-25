package com.example.mc_test2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {
    //Initialize Variable
    ImageView imageView;
    Button btOpen;
    Button navButton;
    private byte[] byteArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign Variable
        imageView = findViewById(R.id.image_view);
        btOpen = findViewById(R.id.bt_open);



        //Request for Camera Permission
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    }, 100);
        }

        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });

        navButton = findViewById(R.id.nav_button);

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open2ndPage();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            captureImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray = stream.toByteArray();

        }
    }

    public void open2ndPage() {
        Intent intent = new Intent(this, MC_Test2_2ndPage.class);
        intent.putExtra("BitmapImage", byteArray );
        startActivity(intent);
    }


}