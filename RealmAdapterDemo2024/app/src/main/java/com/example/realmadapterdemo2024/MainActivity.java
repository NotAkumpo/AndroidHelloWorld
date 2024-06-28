package com.example.realmadapterdemo2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    EditText name;

    EditText breed;

    CheckBox deceased;


    Button addCat;


    Realm realm;
    public void init()
    {
        recyclerView = findViewById(R.id.recyclerView);
        name = findViewById(R.id.name);
        breed = findViewById(R.id.breed);
        deceased = findViewById((R.id.deceased));

        addCat = findViewById(R.id.addCat);
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCat();
            }
        });

        // initialize RecyclerView
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);


        // initialize Realm
        realm = Realm.getDefaultInstance();

        // query the things to display
        RealmResults<Cat> list = realm.where(Cat.class).findAll();

        // initialize Adapter
        CatAdapter adapter = new CatAdapter(this, list, true);
        recyclerView.setAdapter(adapter);
    }

    public void onDestroy()
    {
        super.onDestroy();
        if (!realm.isClosed())
        {
            realm.close();
        }
    }

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

        init();

    }


    public void addCat()
    {
        Cat c = new Cat();
        c.setUuid(UUID.randomUUID().toString());
        c.setName(name.getText().toString());
        c.setBreed(breed.getText().toString());
        c.setDeceased(deceased.isChecked());

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(c);
        realm.commitTransaction();
    }

    public void delete(Cat c)
    {
        // need to check if previously deleted
        if (c.isValid())
        {

            realm.beginTransaction();
            c.deleteFromRealm();
            realm.commitTransaction();
        }
    }



}