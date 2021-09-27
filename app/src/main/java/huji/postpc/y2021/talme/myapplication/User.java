package huji.postpc.y2021.talme.myapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    String email;
    String name;
    String user_id;
    float rating;
    int raters;

    public User(String email, String name, int rating, int raters){
        this.email = email;
        this.name = name;
        this.rating = rating;
        this.raters = raters;
    }

    public User(){}

    static void rateHelper(String userId, float rating, DocumentReference requestRef)
    {
        DocumentReference docRef = HelpoApp.getInstance().usersRef.document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        WriteBatch batch = HelpoApp.getInstance().firestore.batch();

                        User user = (User) document.toObject(User.class);
                        assert user != null;
                        float newRating = user.getRating() * user.raters;
                        newRating += rating;
                        user.raters++;
                        user.rating = newRating / user.raters;
                        Map<String, Object> updateUser = new HashMap<String, Object>() {{
                            put("rating", user.rating);
                            put("raters", user.raters);
                        }};
                        batch.update(docRef, updateUser);

                        Map<String, Object> updateRequest = new HashMap<String, Object>() {{
                            put("status", Request.RequestStatus.Rated);
                        }};
                        batch.update(requestRef, updateRequest);
                        batch.commit();
                    } else {
//                        Log.d(TAG, "No such document");
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    static void rateRequester(String userId, float rating, DocumentReference requestRef)
    {
        DocumentReference docRef = HelpoApp.getInstance().usersRef.document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        WriteBatch batch = HelpoApp.getInstance().firestore.batch();

                        User user = (User) document.toObject(User.class);
                        assert user != null;
                        float newRating = user.getRating() * user.raters;
                        newRating += rating;
                        user.raters++;
                        user.rating = newRating / user.raters;
                        Map<String, Object> updateUser = new HashMap<String, Object>() {{
                            put("rating", user.rating);
                            put("raters", user.raters);
                        }};
                        batch.update(docRef, updateUser);

                        Map<String, Object> updateRequest = new HashMap<String, Object>() {{
                            put("status", HelpOffer.OfferStatus.Rated);
                        }};
                        batch.update(requestRef, updateRequest);
                        batch.commit();
                    } else {
//                        Log.d(TAG, "No such document");
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    public String getUser_id() {
        return user_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRaters(int raters) {
        this.raters = raters;
    }

    public String getEmail() {
        return email;
    }

    public float getRating() {
        return rating;
    }

    public int getRaters() {
        return raters;
    }

    public String getName() {
        return name;
    }
}
