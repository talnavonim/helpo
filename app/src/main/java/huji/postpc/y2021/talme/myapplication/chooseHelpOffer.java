package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class chooseHelpOffer extends AppCompatActivity {

    ChooseHelpOfferAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_help_offer);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backGround_teal)));
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.black));
        String request_id = getIntent().getStringExtra("request_id");
        getSupportActionBar().setTitle("Choose help offer");
        setUpRecyclerView(request_id);
    }

    private void setUpRecyclerView(String request_id) {
        Query query = HelpoApp.getInstance().helpOffersRef.whereEqualTo("req_id", request_id)
                .whereEqualTo("status", HelpOffer.OfferStatus.Pending);
        FirestoreRecyclerOptions<HelpOffer> options = new FirestoreRecyclerOptions.Builder<HelpOffer>()
                .setQuery(query, HelpOffer.class)
                .build();
        adapter = new ChooseHelpOfferAdapter(options, this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_choose_help_offer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}