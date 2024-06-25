package evangelista.labs.lab2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.realm.Realm;
import io.realm.RealmResults;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeTitle;
    SharedPreferences prefs;
    Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        welcomeTitle = findViewById(R.id.welcomeTitle);
        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        realm = Realm.getDefaultInstance();
        RealmResults<User> users = realm.where(User.class).findAll();

        boolean remembered = prefs.getBoolean("remembered", false);
        String uuid = prefs.getString("uuid", null);

        User rememberedUser = realm.where(User.class)
                .equalTo("uuid", uuid)
                .findFirst();
        String rememberedName = rememberedUser.getName();

        if (remembered){

            welcomeTitle.setText("Welcome " + rememberedName + "!!! " + "You will be remembered!");
        }
        else {
            welcomeTitle.setText("Welcome " + rememberedName + "!!!");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}