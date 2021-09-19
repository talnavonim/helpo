package huji.postpc.y2021.talme.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.util.List;

public class myRequestsAdapter extends FirestoreRecyclerAdapter<Request, myRequestViewHolder> {

    HelpoApp app = HelpoApp.getInstance();

    public myRequestsAdapter(@NonNull FirestoreRecyclerOptions<Request> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull myRequestViewHolder holder, int position, @NonNull Request model) {
//            holder.setHelper();
//        holder.setPicture();
        holder.setReq_type(model.type);
        holder.setStatus(model.status);
        if (model.help_offer_id != null)
        {
            loadHelpOffer(holder, model.help_offer_id);
        }
        else
        {
            loadSearching(holder);
        }
//        ((myRequestGroceriesViewHolder) holder).setHelper(curr_request.h);

    }

    private void loadHelpOffer(myRequestViewHolder holder, String help_offer_id) {
        DocumentReference docRef = app.firestore.collection(app.HELP_OFFERS).document(help_offer_id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    HelpOffer offer = document.toObject(HelpOffer.class);
                    assert offer != null;
                    holder.setHelper(offer.helper_full_name);
                } else {
                    //todo set status to waiting, help_offer_id to null and update cloud
                    loadSearching(holder);
                }
            } else {
                Log.d("Failed to load help off", "get failed with ", task.getException());
            }
        });
    }

    private void loadSearching(myRequestViewHolder holder) {
        holder.setHelper("Waiting for help!");
        //todo set picture to megaphone
    }


    @NonNull
    @Override
    public myRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_item, parent, false);
        return new myRequestViewHolder(sender);

    }



//    @Override
//    public int getItemCount() {
//        return myRequestList.getMyRequest().size();
//    }

//    public void setMyRequestHolder(myRequestList myRequestList){
//        this.myRequestList = myRequestList;
//    }
}
