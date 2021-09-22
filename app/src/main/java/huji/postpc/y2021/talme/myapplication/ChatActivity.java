package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MessageListAdapter adapter;
    HelpOffer offer;
    private CollectionReference chatRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Request request = (Request) getIntent().getSerializableExtra("request");
        if (request != null)
        {
            newHelpOffer(request);
        }
        else
        {
            offer = (HelpOffer) getIntent().getSerializableExtra("offer");
        }

        setUpRecyclerView();


        EditText editText = findViewById(R.id.editTextInsertTask);
        Button sendButton = findViewById(R.id.button_gchat_send);

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
        chatRef = HelpoApp.getInstance().helpOffersRef.document(offer.help_id).collection("chat");

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
        offer = new HelpOffer(request.req_id, HelpoApp.getInstance().user_id, request.full_name, request.type);
        HelpoApp.getInstance().helpOffersRef.document(offer.help_id).set(offer); //todo doesn't check success
        chatRef = HelpoApp.getInstance().helpOffersRef.document(offer.help_id).collection("chat");
        Message requestMessage = new Message(request.phraseRequest(), request.user_id, Timestamp.now());
        chatRef.add(requestMessage);
//        String req_id, String helper_email, String requester_full_name, Request.RequestType requestType

    }

}