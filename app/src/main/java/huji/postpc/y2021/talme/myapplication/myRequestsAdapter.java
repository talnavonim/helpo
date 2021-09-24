package huji.postpc.y2021.talme.myapplication;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
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
        if (model.status != Request.RequestStatus.WAITING)
        {
            loadHelpOffer(holder, model.help_offer_id);
        }
        else
        {
            loadSearching(holder, model);
        }
//        ((myRequestGroceriesViewHolder) holder).setHelper(curr_request.h);

    }

    private void loadHelpOffer(myRequestViewHolder holder, String help_offer_id) {
        DocumentReference docRef = app.helpOffersRef.document(help_offer_id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    HelpOffer offer = document.toObject(HelpOffer.class);
                    assert offer != null;
                    holder.setHelper(offer.helper_full_name);

                    holder.helper.setOnClickListener(v->{ //todo set onclick for card not textview
                        Intent chatIntent = new Intent(holder.view.getContext(), ChatActivity.class);
                        chatIntent.putExtra("offer", (Serializable) offer);
                        holder.view.getContext().startActivity(chatIntent);
                    });
                } else {
                    //todo set status to waiting, help_offer_id to null and update cloud
                    loadSearching(holder, null);
                }
            } else {
                Log.d("Failed to load help off", "get failed with ", task.getException());
            }
        });
    }

    private void loadSearching(myRequestViewHolder holder, Request request)
    {
        holder.setHelper("Waiting for help!");
        if (request == null)
        {
            return;
        }
        app.helpOffersRef.whereEqualTo("req_id", request.req_id).whereEqualTo("status", HelpOffer.OfferStatus.PENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0)
                            {
                                holder.setHelper("someone offered help!");

                                holder.helper.setOnClickListener(v->{ //todo set onclick for card not textview
                                    Intent chatIntent = new Intent(holder.view.getContext(), chooseHelpOffer.class);
                                    chatIntent.putExtra("request_id", request.req_id);
                                    holder.view.getContext().startActivity(chatIntent);
                                });
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
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
