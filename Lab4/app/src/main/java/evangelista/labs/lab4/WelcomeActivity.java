package evangelista.labs.lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;

public class WelcomeActivity extends AppCompatActivity {

    TextView welcomeTitle;
    ImageView imageViewW;
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

        imageViewW = findViewById(R.id.imageViewW);

        File getImageDir = getExternalCacheDir();
        File file = new File(getImageDir, rememberedUser.getPath());

        if (file.exists()) {
            Picasso.get()
                    .load(file)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(imageViewW);
        }
        else {
            imageViewW.setImageResource(R.mipmap.ic_launcher);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onDestroy(){
        super.onDestroy();

        if(!realm.isClosed()){
            realm.close();
        }
    }

}