package huji.postpc.y2021.talme.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChooseHelpOfferAdapter extends FirestoreRecyclerAdapter<HelpOffer, ChooseHelpOfferAdapter.UserHolder> {
    HelpoApp app;

    public ChooseHelpOfferAdapter(@NonNull FirestoreRecyclerOptions<HelpOffer> options) {
        super(options);
        app = HelpoApp.getInstance();
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull HelpOffer model) {

        DocumentReference docRef = app.firestore.collection(app.USERS).document(model.helper_id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    assert user != null;
                    holder.txt_name.setText(user.name);
                    holder.txt_raters.setText(String.format("(%d)", user.raters));
                    holder.ratingBar.setRating(user.rating);
                    holder.btn_approve.setOnClickListener(v->{ //todo set onclick for card not textview

                        acceptOffer(model, holder);
                    });
                    holder.btn_decline.setOnClickListener(v -> {
                        declineOffer(model, holder);
                    });
                } else {
                    //todo set status to waiting, help_offer_id to null and update cloud
//                    loadSearching(holder, null);
                }
            } else {
                Log.d("Failed to load user", "get failed with ", task.getException());
            }
        });
    }

    private void declineOffer(HelpOffer model, UserHolder holder) {
        DocumentReference offerRef = app.helpOffersRef.document(model.getHelp_id());
        offerRef.update("status", HelpOffer.OfferStatus.Declined);
    }


    private void acceptOffer(HelpOffer model, UserHolder holder) {

        app.helpOffersRef .whereEqualTo("req_id", model.req_id) .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            updateDcouments(task, model, holder);
                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void updateDcouments(Task<QuerySnapshot> task, HelpOffer model, UserHolder holder) {
        WriteBatch batch = app.firestore.batch();
        Map<String, Object> approved = new HashMap<String, Object>() {{
            put("status", HelpOffer.OfferStatus.Ongoing);
        }};
        Map<String, Object> declined = new HashMap<String, Object>() {{
            put("status", HelpOffer.OfferStatus.Declined);
        }};
        for (QueryDocumentSnapshot document : task.getResult()) {
            DocumentReference docRef = app.helpOffersRef.document(document.getId());
            if (document.getId().equals(model.help_id))
            {
                batch.update(docRef, approved);
            }
            else
            {
                batch.update(docRef, declined);
            }
        }

        DocumentReference requestRef = app.requestsRef.document(model.getReq_id());
        Map<String, Object> updateRequest = new HashMap<String, Object>() {{
            put("help_offer_id", model.help_id);
            put("status", Request.RequestStatus.Ongoing);
        }};
        batch.update(requestRef, updateRequest);

        CollectionReference chatRef = app.helpOffersRef.document(model.help_id).collection("chat");

        Message systemMessage = new Message("Help offer pending", "system", Timestamp.now());
        batch.set(chatRef.document(UUID.randomUUID().toString()), systemMessage);

        batch.commit().addOnSuccessListener(aVoid -> {
            Intent chatIntent = new Intent(holder.view.getContext(), ChatActivity.class);
            chatIntent.putExtra("offer", (Serializable) model);
            holder.view.getContext().startActivity(chatIntent);
        });
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sender = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_help_offer_user, parent, false);
        return new UserHolder(sender);
    }

    class UserHolder extends RecyclerView.ViewHolder{
        TextView txt_name, txt_raters;
        ImageButton btn_approve, btn_decline;
        RatingBar ratingBar;
        View view;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txt_name = itemView.findViewById(R.id.text_helper_full_name);
            txt_raters = itemView.findViewById(R.id.text_raters);
            btn_approve = itemView.findViewById(R.id.imageButton_approve);
            btn_decline = itemView.findViewById(R.id.imageButton_decline);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
