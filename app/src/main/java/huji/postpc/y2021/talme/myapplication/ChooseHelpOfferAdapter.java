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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ChooseHelpOfferAdapter extends FirestoreRecyclerAdapter<HelpOffer, ChooseHelpOfferAdapter.UserHolder> {

    public ChooseHelpOfferAdapter(@NonNull FirestoreRecyclerOptions<HelpOffer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserHolder holder, int position, @NonNull HelpOffer model) {
        HelpoApp app = HelpoApp.getInstance();
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
                } else {
                    //todo set status to waiting, help_offer_id to null and update cloud
//                    loadSearching(holder, null);
                }
            } else {
                Log.d("Failed to load user", "get failed with ", task.getException());
            }
        });
    }

    private void acceptOffer(HelpOffer model, UserHolder holder) {

        DocumentReference requestRef = HelpoApp.getInstance().requestsRef.document(model.getReq_id());
        Map<String, Object> myMap = new HashMap<String, Object>() {{
            put("help_offer_id", model.help_id);
            put("status", Request.RequestStatus.READY);
        }};
        requestRef.update(myMap).addOnSuccessListener(aVoid -> {
                    Intent chatIntent = new Intent(holder.view.getContext(), ChatActivity.class);
                    chatIntent.putExtra("offer", (Serializable) model);
                    holder.view.getContext().startActivity(chatIntent);
                });
        DocumentReference offerRef = HelpoApp.getInstance().helpOffersRef.document(model.help_id);
        offerRef.update("status", HelpOffer.OfferStatus.APPROVED);
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
