package huji.postpc.y2021.talme.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class HelpoApp extends Application {
    private static HelpoApp instance = null;
    private SharedPreferences sp = null;
    public FirebaseFirestore firestore = null; //exposed for tests
    public final String REQUESTS = "requests";
    GoogleSignInAccount account;

    public User user;


    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseApp.initializeApp(this);;
        firestore = FirebaseFirestore.getInstance();
        instance = this;

        user = new User("talme1091@gmail.com", "tal", 10, 1);
    }

    public static HelpoApp getInstance()
    {
        return instance;
    }
}
