package huji.postpc.y2021.talme.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class RequestsActivity extends AppCompatActivity {
    Button signOut;
    ImageButton toMapButton;
    Button newRequastButton;
    GoogleSignInClient mGoogleSignInClient;

    MaterialButtonToggleGroup selectIhelpOrMyRequests;
    Button iHelpButton;
    Button myRequestButton;
    ConstraintLayout ihelpContainer;
    ConstraintLayout myRequestsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        signOut = findViewById(R.id.signOutButton);
        newRequastButton = findViewById(R.id.create_request_button);
        toMapButton = findViewById(R.id.toMapButton);
        iHelpButton = findViewById(R.id.i_help_button);
        myRequestButton = findViewById(R.id.my_requests_button);
        ihelpContainer = findViewById(R.id.ihelp_selected);
        myRequestsContainer = findViewById(R.id.myrequests_selected);


        selectIhelpOrMyRequests = findViewById(R.id.ihelp_and_requests_buttons);
        selectIhelpOrMyRequests.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    switch (checkedId){
                        case R.id.i_help_button:
                            if(myRequestsContainer.getVisibility() == View.VISIBLE){
                                myRequestsContainer.setVisibility(View.INVISIBLE);
                            }
                            ihelpContainer.setVisibility(View.VISIBLE);
//                            iHelpButtonFunc();
                            break;
                        case R.id.my_requests_button:
                            if(ihelpContainer.getVisibility() == View.VISIBLE){
                                ihelpContainer.setVisibility(View.INVISIBLE);
                            }
                            myRequestsContainer.setVisibility(View.VISIBLE);
//                            groceryFunc();
                            break;
                    }

                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signOut.setOnClickListener(v ->
        {
            switch (v.getId()) {
                case R.id.signOutButton:
                    signOut();
                    break;
            }

        });
        toMapButton.setOnClickListener(v->
        {
            Intent findHelpos = new Intent(RequestsActivity.this, MainActivity.class);
            startActivity(findHelpos);
        });

        newRequastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newRequest = new Intent(RequestsActivity.this, CreateRequestActivity.class);
                startActivity(newRequest);
            }
        });
    }

    private void iHelpButtonFunc(){
        //TODO
    }

    private void myRequestsFunc(){

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RequestsActivity.this,"Signed Out Successfully",Toast.LENGTH_LONG).show();
                        Intent goToLogin = new Intent(RequestsActivity.this, WelcomeActivity.class);
                        startActivity(goToLogin);
                    }
                });
    }
}
