package huji.postpc.y2021.talme.myapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

public class HelpoApp extends Application {
    private static HelpoApp instance = null;

    private SharedPreferences sp = null;
    public FirebaseFirestore firestore = null; //exposed for tests
    private final String REQUESTS = "requests";
    private final String HELP_OFFERS = "help_offers";
    public final String USERS = "users";
    public CollectionReference requestsRef;
    public CollectionReference helpOffersRef;
    GoogleSignInAccount account;
    public String email;
    public String full_name;
    public String user_id;

//    public User user;

    public myRequestList requests_list;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        FirebaseApp.initializeApp(this);;
        firestore = FirebaseFirestore.getInstance();
        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("new_offer");
        requestsRef = firestore.collection(REQUESTS);
        helpOffersRef = firestore.collection(HELP_OFFERS);
        instance = this;

//        user_id = "QdyRh1684aFJtaVLQ2qm"; full_name= "Ophir Han"; email="ophirhan@gmail.com";  //todo remove place holder
        user_id = "s53mTabvuHzWKpA3cRhw"; full_name= "Eldar Lerner"; email="eldar.lerner@gmail.com";



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

