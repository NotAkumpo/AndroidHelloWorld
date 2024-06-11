package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    Button button;
    EditText nameInput;

    SharedPreferences prefs;

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

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        nameInput = findViewById(R.id.nameInput);

        String s = prefs.getString("name", null);

        if (s==null) {
            nameInput.setText("boo");
        }
        else {
            nameInput.setText(s);
        }

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNext();
            }
        });


    }

    public void goToNext(){
        String text = nameInput.getText().toString();
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", text);
        edit.apply();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("name","Jennie");
        intent.putExtra("name2","Lisa");
        intent.putExtra("name3","Jisoo");
        intent.putExtra("name4","Rose");

        intent.putExtra("age", 27);
        startActivity(intent);
    }

}