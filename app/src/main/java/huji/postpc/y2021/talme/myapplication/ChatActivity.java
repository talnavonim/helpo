package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.google.firestore.v1.Write;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    TextView txt_partnerName;
    RecyclerView recyclerView;
    MessageListAdapter adapter;
    HelpOffer offer;
    private CollectionReference chatRef;
    HelpoApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        app = HelpoApp.getInstance();

        Request request = (Request) getIntent().getSerializableExtra("request");
        if (request != null)
        {
            newHelpOffer(request);
        }
        else
        {
            offer = (HelpOffer) getIntent().getSerializableExtra("offer");
        }
        chatRef = app.helpOffersRef.document(offer.help_id).collection("chat");
        setUpRecyclerView();

        txt_partnerName = findViewById(R.id.txt_partner_name);
        if (offer.helper_id.equals(HelpoApp.getInstance().user_id))
        {
            txt_partnerName.setText(offer.requester_full_name);
        }
        else
        {
            txt_partnerName.setText(offer.helper_full_name);
        }
        EditText editText = findViewById(R.id.editTextInsertTask);
        FloatingActionButton sendButton = findViewById(R.id.button_gchat_send);

        sendButton.setOnClickListener(v->{
            String message = editText.getText().toString();
            if(message.length() != 0){
                chatRef.add(new Message(editText.getText().toString(),
                        HelpoApp.getInstance().user_id, Timestamp.now()));
                editText.setText("");
            }
        });

    }

    private void setUpRecyclerView() {
        chatRef = app.helpOffersRef.document(offer.help_id).collection("chat");

        Query query = chatRef.orderBy("message_timestamp", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .build();

        adapter = new MessageListAdapter(options);
        recyclerView = findViewById(R.id.recycler_gchat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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


    private void newHelpOffer(Request request) {
        offer = new HelpOffer(request.req_id, app.user_id,request.user_id, request.full_name, request.type);
        app.helpOffersRef.document(offer.help_id).set(offer);
        Message requestMessage = new Message(request.phraseRequest(), request.user_id, Timestamp.now());
        WriteBatch batch = app.firestore.batch();
        batch.set(chatRef.document(UUID.randomUUID().toString()), requestMessage);
        Message systemMessage = new Message("Help offer pending", "system", Timestamp.now());
        batch.set(chatRef.document(UUID.randomUUID().toString()), systemMessage);
        batch.commit();

    }

    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu);
        if ( !offer.helper_id.equals(app.user_id) || offer.status != HelpOffer.OfferStatus.Ongoing)
            popup.getMenu().removeItem(R.id.set_as_done);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cancel_offer:
                cancelOffer(app.firestore.batch());
                return true;
            case R.id.report_user:
                reportUser();
                return true;
            case R.id.set_as_done:
                setAsDone();
            default:
                return false;
        }
    }

    private void setAsDone() {

        WriteBatch batch = HelpoApp.getInstance().firestore.batch();
        Map<String, Object> done = new HashMap<String, Object>() {{
            put("status", Request.RequestStatus.Done);
        }};

        DocumentReference requestRef = HelpoApp.getInstance().requestsRef.document(offer.getReq_id());
        batch.update(requestRef, done);

        Map<String, Object> offerDone = new HashMap<String, Object>() {{
            put("status", HelpOffer.OfferStatus.Done);
        }};
        DocumentReference offerRef = HelpoApp.getInstance().helpOffersRef.document(offer.getHelp_id());
        batch.update(offerRef, offerDone);

        Message systemMessage = new Message("Help offer done!", "system", Timestamp.now());
        batch.set(chatRef.document(UUID.randomUUID().toString()), systemMessage);

        batch.commit();
    }

    private void reportUser() {

        WriteBatch batch = HelpoApp.getInstance().firestore.batch();
        Report report;
        if (app.user_id.equals(offer.helper_id))
        {
            report = new Report(offer.requester_id, offer.helper_id, offer.help_id, "bad guy!");
        }
        else
        {
            report = new Report(offer.helper_id, offer.requester_id, offer.help_id, "bad guy!");
        }
        DocumentReference reportRef = app.firestore.collection("reports").document(report.report_id);
        batch.set(reportRef, report);
        cancelOffer(batch);
    }

    private void cancelOffer(WriteBatch batch) {
        Map<String, Object> waiting = new HashMap<String, Object>() {{
            put("status", Request.RequestStatus.Waiting);
            put("help_offer_id", "");
        }};

        DocumentReference requestRef = HelpoApp.getInstance().requestsRef.document(offer.getReq_id());
        batch.update(requestRef, waiting);

        Map<String, Object> canceled = new HashMap<String, Object>() {{
            put("status", HelpOffer.OfferStatus.Canceled);
        }};
        DocumentReference offerRef = HelpoApp.getInstance().helpOffersRef.document(offer.getHelp_id());
        batch.update(offerRef, canceled);

        batch.commit().addOnSuccessListener(aVoid -> {
            finish();
        });
    }
}