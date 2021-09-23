package huji.postpc.y2021.talme.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HelpoApp extends Application {
    private static HelpoApp instance = null;

    private SharedPreferences sp = null;
    public FirebaseFirestore firestore = null; //exposed for tests
    public final String REQUESTS = "requests";
    public final String HELP_OFFERS = "help_offers";
    public final String USERS = "users";
    public CollectionReference requestsRef;
    public CollectionReference helpOffersRef;
    GoogleSignInAccount account;
    public String email;
    public String full_name;
    public String user_id;

    public User user;

    public myRequestList requests_list;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseApp.initializeApp(this);;
        firestore = FirebaseFirestore.getInstance();
        requestsRef = firestore.collection(REQUESTS);
        helpOffersRef = firestore.collection(HELP_OFFERS);
        instance = this;

        email = "talme1091@gmail.com"; // todo remove place holder
        full_name = "Tal Navon"; // todo remove place holder
//        user_id = "QdyRh1684aFJtaVLQ2qm"; // todo remove place holder
        user_id = "Carmel";

        user = new User("talme1091@gmail.com", "tal", 10, 1);

        requests_list = new myRequestList(this);

    }

    public myRequestList getRequestsList(){
        return this.requests_list;
    }

    public static HelpoApp getInstance()
    {
        return instance;
    }


}

