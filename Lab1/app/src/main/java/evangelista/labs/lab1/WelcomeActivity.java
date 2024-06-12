package evangelista.labs.lab1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeTitle;
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        welcomeTitle = findViewById(R.id.welcomeTitle);
        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        boolean remembered = prefs.getBoolean("remembered", false);
        String username = prefs.getString("username", null);

        if (remembered){
            welcomeTitle.setText("Welcome " + username + "!!! " + "You will be remembered!");
        }
        else {
            welcomeTitle.setText("Welcome " + username + "!!!");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}