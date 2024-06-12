package evangelista.labs.lab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    Button saveButton;
    Button cancelButton;
    EditText usernameInputR;
    EditText passwordInputR;
    EditText confirmInput;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        usernameInputR = findViewById(R.id.usernameInputR);
        passwordInputR = findViewById(R.id.passwordInputR);
        confirmInput = findViewById(R.id.confirmInput);

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelRegister();
            }
        });

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }


    public void registerUser(){
        String username = usernameInputR.getText().toString();
        String password = passwordInputR.getText().toString();
        String confirm = confirmInput.getText().toString();

        if (username.isBlank()) {
            Toast toast = Toast.makeText(this, "Name must not be blank", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            if (password.isBlank()) {
                Toast toast = Toast.makeText(this, "Password must not be blank", Toast.LENGTH_LONG);
                toast.show();
            }
            else {
                if (password.equals(confirm)) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putString("username", username);
                    edit.putString("password", password);
                    edit.apply();

                    Toast toast = Toast.makeText(this, "Saved", Toast.LENGTH_LONG);
                    toast.show();

                    finish();
                }
                else {
                    Toast toast = Toast.makeText(this, "Confirm password does not match", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }

    public void cancelRegister(){
        finish();
    }

}