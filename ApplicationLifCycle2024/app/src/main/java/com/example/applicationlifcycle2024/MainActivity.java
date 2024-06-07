package com.example.applicationlifcycle2024;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button click;
    EditText input;
    TextView myText;
    CheckBox checkBox;

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

        click = findViewById(R.id.click);
        input = findViewById(R.id.input);
        myText = findViewById(R.id.myText);

        checkBox = findViewById(R.id.checkBox);

        myText.setText("Click me, you know you want to");

        myText.setText("Input Something");

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferText();
            }
        });

    }

    public void transferText(){
        String text = input.getText().toString();
        myText.setText(text + " " + checkBox.isChecked());
    }

    // https://developer.android.com/studio/debug/am-logcat

    /*

    These correspond the the log level dropdown on the Logcat

    Log.d   Debug
    Log.i   Info
    Log.e   Error
    Log.w   Warn
    Log.v   Verbose

    the first parameter is a TAG, this helps you filter using the Search box
    (make sure Regex is deselected)
     */

    public void onStart()
    {
        super.onStart();
        Log.d("Lifecycle", "onStart()");
    }

    public void onStop()
    {
        super.onStop();
        Log.d("Lifecycle", "onStop()");

    }

    public void onRestart()
    {
        super.onRestart();
        Log.d("Lifecycle", "onRestart()");
    }

    public void onResume()
    {
        super.onResume();
        Log.d("Lifecycle", "onResume()");
    }

    public void onPause()
    {
        super.onPause();
        Log.d("Lifecycle", "onPause()");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Log.d("Lifecycle", "onDestroy()");
    }
}
