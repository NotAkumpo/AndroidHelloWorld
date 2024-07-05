package com.example.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;


public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        checkPermissions();

    }

    RecyclerView recyclerView;
    ImageButton snapshot;

    Realm realm;
    public static int REQUEST_CODE_IMAGE_SCREEN = 0;


    public void snapshot()
    {
        Intent i = new Intent(this, ImageActivity.class);
        startActivityForResult(i, REQUEST_CODE_IMAGE_SCREEN);
    }


    // SINCE WE USE startForResult(), code will trigger this once the next screen calls finish()
    public void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);

        if (requestCode==REQUEST_CODE_IMAGE_SCREEN)
        {
            if (responseCode==ImageActivity.RESULT_CODE_IMAGE_TAKEN)
            {
                // receieve the raw JPEG data from ImageActivity
                // this can be saved to a file or save elsewhere like Realm or online
                byte[] jpeg = data.getByteArrayExtra("rawJpeg");

                try {

                    Pic c = new Pic();
                    c.setPath(System.currentTimeMillis()+".jpeg");

//                    c.setPath(c.getUuid()+".jpeg");



                    // save rawImage to file
                    File savedImage = saveFile(jpeg, c.getPath());

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(c);
                    realm.commitTransaction();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    }




    private File saveFile(byte[] jpeg, String name) throws IOException
    {
        // this is the root directory for the images
        File getImageDir = getExternalCacheDir();

        // just a sample, normally you have a diff image name each time
        File savedImage = new File(getImageDir, name);

        FileOutputStream fos = new FileOutputStream(savedImage);
        fos.write(jpeg);
        fos.close();
        return savedImage;
    }




    public void checkPermissions()
    {

        // REQUEST PERMISSIONS for Android 6+
        // THESE PERMISSIONS SHOULD MATCH THE ONES IN THE MANIFEST
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA

                )

                .withListener(new BaseMultiplePermissionsListener()
                {
                    public void onPermissionsChecked(MultiplePermissionsReport report)
                    {
                        if (report.areAllPermissionsGranted())
                        {
                            // all permissions accepted proceed
                            init();
                        }
                        else
                        {
                            // notify about permissions
                            toastRequirePermissions();
                        }
                    }
                })
                .check();

    }


    public void toastRequirePermissions()
    {
        Toast.makeText(this, "You must provide permissions for app to run", Toast.LENGTH_LONG).show();
        finish();
    }



    public void init()
    {
        recyclerView = findViewById(R.id.recyclerView);

        snapshot = findViewById(R.id.snapshot);
        snapshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snapshot();
            }
        });


        // initialize RecyclerView
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);


        // initialize Realm
        realm = Realm.getDefaultInstance();

        // query the things to display
        RealmResults<Pic> list = realm.where(Pic.class).findAll();

        // initialize Adapter
        PicAdapter adapter = new PicAdapter(this, list, true);
        recyclerView.setAdapter(adapter);
    }



}