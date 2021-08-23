package huji.postpc.y2021.talme.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class HelpoApp extends Application {
    private static HelpoApp instance = null;
    private SharedPreferences sp = null;
    public FirebaseFirestore firestore = null; //exposed for tests
    public final String REQUESTS = "requests";
    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseApp.initializeApp(this);;
        firestore = FirebaseFirestore.getInstance();
        instance = this;

    }

    public static HelpoApp getInstance()
    {
        return instance;
    }
}
