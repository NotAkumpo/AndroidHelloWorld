package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {


    TextView label;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        label = findViewById(R.id.textView);
        back = findViewById(R.id.button2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        //pick up gift basket
        Intent intent = getIntent();
        String s = intent.getStringExtra("name");
        String s2 = intent.getStringExtra("name2");
        String s3 = intent.getStringExtra("name3");
        String s4 = intent.getStringExtra("name4");

        int i = intent.getIntExtra("age", 0);

        label.setText(s + " " + s2 + " " + s3 + " " + s4 + " has arrived!");

    }


    public void goBack(){
        finish();
    }

    @Override
    public void finish(){
        Toast toast = Toast.makeText(this, "CLOSING", Toast.LENGTH_LONG);
        toast.show();
        super.finish();
    }

}