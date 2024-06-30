package evangelista.labs.lab3;

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

import io.realm.Realm;
import io.realm.RealmResults;

public class RegisterActivity extends AppCompatActivity {

    Button saveButton;
    Button cancelButton;
    EditText usernameInputR;
    EditText passwordInputR;
    EditText confirmInput;
    Realm realm;

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

        realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class).findAll();

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
        User exists = realm.where(User.class)
                .equalTo("name", username)
                .findFirst();

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
                    if(exists != null) {
                        Toast toast = Toast.makeText(this, "User already exists", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {
                        User u = new User();
                        u.setName(username);
                        u.setPassword(password);

                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(u);
                        realm.commitTransaction();

                        Toast toast = Toast.makeText(this, "New User saved. Total: " + realm.where(User.class).count(), Toast.LENGTH_LONG);
                        toast.show();

                        finish();
                    }
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

    public void onDestroy(){
        super.onDestroy();

        if(!realm.isClosed()){
            realm.close();
        }
    }

}