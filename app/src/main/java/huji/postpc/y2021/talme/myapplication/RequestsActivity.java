package huji.postpc.y2021.talme.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class RequestsActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
//    Button signOut;
    Button toMapButton;
    Button newRequestButton;
    GoogleSignInClient mGoogleSignInClient;

    MaterialButtonToggleGroup selectIhelpOrMyRequests;
    Button iHelpButton;
    Button myRequestButton;
    ConstraintLayout ihelpContainer;
    ConstraintLayout myRequestsContainer;

    RecyclerView recyclerView_myrequests;
    myRequestsAdapter myrequests_adapter;
    IhelpAdapter help_offer_adapter;
    RecyclerView recyclerHelpOffers;

    HelpoApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        app = HelpoApp.getInstance();
        setUpRequestRecylerView();
        setUpIhelpRecylerView();

//        signOut = findViewById(R.id.signOutButton);
        newRequestButton = findViewById(R.id.create_request_button);
        toMapButton = findViewById(R.id.toMapButton);
        iHelpButton = findViewById(R.id.i_help_button);
        myRequestButton = findViewById(R.id.my_requests_button);
        ihelpContainer = findViewById(R.id.ihelp_selected);
        myRequestsContainer = findViewById(R.id.myrequests_selected);

        recyclerHelpOffers = findViewById(R.id.recycler_ihelp);
//        myRequestButton.set
//        selectIhelpOrMyRequests.setSingleSelection();




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
                            break;
                        case R.id.my_requests_button:
                            if(ihelpContainer.getVisibility() == View.VISIBLE){
                                ihelpContainer.setVisibility(View.INVISIBLE);
                            }
                            myRequestsContainer.setVisibility(View.VISIBLE);
                            break;
                    }

                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        signOut.setOnClickListener(v ->
//        {
//            switch (v.getId()) {
//                case R.id.signOutButton:
//                    signOut();
//                    break;
//            }
//
//        });
        toMapButton.setOnClickListener(v->
        {
            Intent findHelpos = new Intent(RequestsActivity.this, MainActivity.class);
            startActivity(findHelpos);
        });

        newRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newRequest = new Intent(RequestsActivity.this, CreateRequestActivity.class);
                startActivity(newRequest);
            }
        });

    }



    private void setUpIhelpRecylerView() {
        Query query = app.helpOffersRef.whereEqualTo("helper_id", app.user_id).whereNotEqualTo("status", HelpOffer.OfferStatus.Hidden)
                .orderBy("status", Query.Direction.DESCENDING).orderBy("offer_timestamp", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<HelpOffer> options = new FirestoreRecyclerOptions.Builder<HelpOffer>()
                .setQuery(query, HelpOffer.class)
                .build();

        recyclerHelpOffers = findViewById(R.id.recycler_ihelp);
        help_offer_adapter = new IhelpAdapter(options, this);
        recyclerHelpOffers.setHasFixedSize(true);
        recyclerHelpOffers.setItemAnimator(null);
        recyclerHelpOffers.setLayoutManager(new LinearLayoutManager(this));
        recyclerHelpOffers.setAdapter(help_offer_adapter);
    }

    private void setUpRequestRecylerView()
    {
        Query query = app.requestsRef.whereEqualTo("user_id", app.user_id).whereNotEqualTo("status", Request.RequestStatus.Hidden)
                .orderBy("status", Query.Direction.DESCENDING).orderBy("request_timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Request> options = new FirestoreRecyclerOptions.Builder<Request>()
                .setQuery(query, Request.class)
                .build();

        recyclerView_myrequests = findViewById(R.id.recycler_myrequests);
        myrequests_adapter = new myRequestsAdapter(options, this);
        recyclerView_myrequests.setHasFixedSize(true);
        recyclerView_myrequests.setItemAnimator(null);
        recyclerView_myrequests.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_myrequests.setAdapter(myrequests_adapter);

    }

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.requests_menu);
        popup.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        myrequests_adapter.startListening();
        help_offer_adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myrequests_adapter.stopListening();
        help_offer_adapter.stopListening();
    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut();
        Toast.makeText(RequestsActivity.this, "Signed Out Successfully", Toast.LENGTH_LONG).show();
        Intent goToLogin = new Intent(RequestsActivity.this, WelcomeActivity.class);
        startActivity(goToLogin);

//        mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(RequestsActivity.this,"Signed Out Successfully",Toast.LENGTH_LONG).show();
//                        Intent goToLogin = new Intent(RequestsActivity.this, WelcomeActivity.class);
//                        startActivity(goToLogin);
////                        finish();
//                    }
//                });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch(item.getItemId()){
            case R.id.signOutMenuItem:
                signOut();
                return true;
            default:
                return false;
        }
    }
}
