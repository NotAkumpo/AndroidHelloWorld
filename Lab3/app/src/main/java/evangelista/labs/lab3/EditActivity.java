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

public class EditActivity extends AppCompatActivity {


    Button saveButtonE;
    Button cancelButtonE;
    EditText usernameInputE;
    EditText passwordInputE;
    EditText confirmInputE;
    SharedPreferences prefs;
    Realm realm;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        usernameInputE = findViewById(R.id.usernameInputE);
        passwordInputE = findViewById(R.id.passwordInputE);
        confirmInputE = findViewById(R.id.confirmInputE);

        saveButtonE = findViewById(R.id.saveButtonE);
        saveButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        cancelButtonE = findViewById(R.id.cancelButtonE);
        cancelButtonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelEdit();
            }
        });

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        String uuid = prefs.getString("uuidE", null);

        realm = Realm.getDefaultInstance();

        currentUser = realm.where(User.class)
                .equalTo("uuid", uuid)
                .findFirst();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void saveUser(){
        String username = usernameInputE.getText().toString();
        String password = passwordInputE.getText().toString();
        String confirm = confirmInputE.getText().toString();

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
                    if(exists != null && !exists.getName().equals(currentUser.getName())) {
                        Toast toast = Toast.makeText(this, "User already exists", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else {

                        realm.beginTransaction();

                        currentUser.setName(username);
                        currentUser.setPassword(password);

                        realm.copyToRealmOrUpdate(currentUser);
                        realm.commitTransaction();

                        Toast toast = Toast.makeText(this, "Changes saved", Toast.LENGTH_LONG);
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

    public void cancelEdit(){
        finish();
    }


}