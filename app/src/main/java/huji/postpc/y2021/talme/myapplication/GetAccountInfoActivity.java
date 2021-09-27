package huji.postpc.y2021.talme.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class GetAccountInfoActivity extends AppCompatActivity {
    EditText name;
    TextView email;
    ImageView photo;
    FloatingActionButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_account_info);
        HelpoApp app = HelpoApp.getInstance();
        name = findViewById(R.id.infoName);
        email = findViewById(R.id.InfoEmail);
        photo = findViewById(R.id.InfoImage);
        confirmButton = findViewById(R.id.ConfirmButton);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
//            app.firestore.collection("users").document(user.getUid()).get()
//                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                            if(task.getResult().exists())
//                            {
//                                app.full_name = user.getDisplayName();
//                                app.email = user.getEmail();
//                                app.user_id = user.getUid();
//                                Intent intent = new Intent(app, RequestsActivity.class);
//                                startActivity(intent);
//                            }
//
//                        }
//                    });
            String personName = user.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
            String personEmail = user.getEmail();
//            String personId = acct.getId();
            Uri personPhoto = user.getPhotoUrl();
            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(photo);
            confirmButton.setOnClickListener(v->
            {
                User new_user = new User(email.getText().toString(),name.getText().toString(),3,0);
                new_user.user_id = user.getUid();
                app.full_name = name.getText().toString();
                app.email = email.getText().toString();
                app.user_id = new_user.getUser_id();
                DocumentReference newUserRef = app.usersRef.document(new_user.user_id);
                newUserRef.set(new_user)
                        .addOnSuccessListener(result -> {
                            Intent intent = new Intent(app, RequestsActivity.class);
                            startActivity(intent);
                            finish();
                        }).addOnFailureListener(u -> {
                    Toast toast = Toast.makeText(app, "Failed to connect with server", Toast.LENGTH_SHORT);
                    toast.show();
                    finish();
                });
            });
        }
        else
        {
            Toast.makeText(GetAccountInfoActivity.this, "Failed to receive information - please resign", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}