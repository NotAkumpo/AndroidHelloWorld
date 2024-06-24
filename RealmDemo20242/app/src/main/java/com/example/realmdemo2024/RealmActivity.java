package com.example.realmdemo2024;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmActivity extends AppCompatActivity {

    EditText item;
    EditText price;
    EditText search;

    Button searchButton;

    Button addButton;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_realm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // VIEW INITIALIZATION


        item = findViewById(R.id.item);
        price = findViewById(R.id.price);
        search = findViewById(R.id.searchbox);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });


        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });



        // REALM INITIALIZATION

        realm = Realm.getDefaultInstance();

        RealmResults<Item> result = realm.where(Item.class).findAll();



        result = result.sort("price", Sort.DESCENDING);

        for (Item i : result)
        {
            Log.d("RealmDemo", i.toString());
        }
    }




    public void onDestroy() {
        super.onDestroy();

        if (!realm.isClosed()) {
            realm.close();
        }
    }






    public void add()
    {

        String name = item.getText().toString();
        double p = Double.parseDouble(price.getText().toString());

        Item newItem =  new Item();
        newItem.setName(name);
        newItem.setPrice(p);

        long count = 0;

        try {


            realm.beginTransaction();
            realm.copyToRealmOrUpdate(newItem);  // save
            realm.commitTransaction();


            count = realm.where(Item.class).count();


            Toast t = Toast.makeText(this, "Saved: "+count, Toast.LENGTH_LONG);
            t.show();

        }
        catch(Exception e)
        {
            Toast t = Toast.makeText(this, "Error saving", Toast.LENGTH_LONG);
            t.show();
        }
    }


    public void search()
    {
        RealmResults<Item> r = realm.where(Item.class)
                .findAll();



        // QUERY

        Item result = realm.where(Item.class)
                .equalTo("name", search.getText().toString())
                .findFirst();

        Log.d("RealmDemo", result.toString());

        if (result!=null)
        {
            Toast t = Toast.makeText(this, "Price = "+result.getPrice(), Toast.LENGTH_LONG);
            t.show();
        }
        else
        {
            Toast t = Toast.makeText(this, "No result found", Toast.LENGTH_LONG);
            t.show();
        }
    }
}