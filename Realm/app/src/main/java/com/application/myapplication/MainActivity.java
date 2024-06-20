package com.application.myapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.application.myapplication.realm.Student;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        realm = Realm.getDefaultInstance();

        if (realm.where(Student.class).count()==0) {

            Student s = new Student();
            s.setName("Keaton Shih");
            s.setCourse("BS MIS");
            s.setAge(20);
            s.setScholar(false);
            s.setIdNumber("12345");

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(s);
            realm.commitTransaction();

            Toast.makeText(this, "SAVED" + s.toString(), Toast.LENGTH_LONG).show();
        }
        else {
            RealmResults results = realm.where(Student.class).findAll();
            Toast.makeText(this, "LOADED" + results.toString(), Toast.LENGTH_LONG).show();
        }



    }






    public void onDestroy(){
        super.onDestroy();

        if(!realm.isClosed()){
            realm.close();
        }
    }


}