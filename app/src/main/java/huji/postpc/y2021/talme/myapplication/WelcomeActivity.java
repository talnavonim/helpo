package huji.postpc.y2021.talme.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "Error";
    GoogleSignInClient mGoogleSignInClient;
    ImageButton gmailButton;
    HelpoApp app;
    String client_id = "780182899043-fha6hk2b0bgl12hq96oojuh6n61rhnb6.apps.googleusercontent.com";
    int RC_SIGN_IN = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        app = HelpoApp.getInstance();
        app.firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(client_id)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        gmailButton = findViewById(R.id.gmail);
        gmailButton.setOnClickListener(v->
        {
            switch (v.getId()) {
                case R.id.gmail:
                    signIn();
                    break;
            }

        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = app.firebaseAuth.getCurrentUser();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(currentUser!= null) {//todo - receive account information and display it on screen
//            app.account = currentUser;
            Intent intent = new Intent(this, RequestsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        app.firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = app.firebaseAuth.getCurrentUser();
                            if(user != null)
                            {
                            app.firestore.collection("users").document(user.getUid()).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                   if (task.getResult().exists()) {
                                                                       app.full_name = user.getDisplayName();
                                                                       app.email = user.getEmail();
                                                                       app.user_id = user.getUid();
                                                                       Intent intent = new Intent(app, RequestsActivity.class);
                                                                       startActivity(intent);
                                                                   }

                                                               }
                                                           });
                            }
                            Intent signInIntent = new Intent(WelcomeActivity.this, GetAccountInfoActivity.class);
                            startActivity(signInIntent);
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            app.account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
//            updateUI(account);
            Intent signInIntent = new Intent(WelcomeActivity.this, GetAccountInfoActivity.class);
            startActivity(signInIntent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
//            updateUI(null);
        }
    }
}