package evangelista.labs.lab2;

import android.app.Application;

import io.realm.Realm;

public class MyApp extends Application {

    public void onCreate()
    {
        super.onCreate();

        Realm.init(this);
    }
}
