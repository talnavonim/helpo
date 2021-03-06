package huji.postpc.y2021.talme.myapplication;

import android.content.Context;
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
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class myRequestsAdapter extends FirestoreRecyclerAdapter<Request, myRequestViewHolder> {

    HelpoApp app = HelpoApp.getInstance();
    Context con;

    public myRequestsAdapter(@NonNull FirestoreRecyclerOptions<Request> options, Context con) {
        super(options);
        this.con = con;
    }


    @Override
    protected void onBindViewHolder(@NonNull myRequestViewHolder holder, int position, @NonNull Request model) {

        holder.setReq_type(model.type);
        holder.setStatus(model.status);
        if (model.status != Request.RequestStatus.Waiting)
        {
            loadHelpOffer(holder, model.help_offer_id, model);
        }
        else
        {
            loadSearching(holder, model);
        }
    }

    private void loadHelpOffer(myRequestViewHolder holder, String help_offer_id, Request request) {
        DocumentReference docRef = app.helpOffersRef.document(help_offer_id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    HelpOffer offer = document.toObject(HelpOffer.class);
                    assert offer != null;
                    holder.setHelper(offer.helper_full_name);
                    holder.status.setText(offer.status.toString());

                    if (request.status == Request.RequestStatus.Rated)
                    {
                        holder.btn_dismiss.setVisibility(View.VISIBLE);
                        holder.layoutRating.setVisibility(View.GONE);
                        holder.btn_dismiss.setOnClickListener(v-> {
                            app.requestsRef.document(request.req_id).update("status", Request.RequestStatus.Hidden);
                        });
                    }
                    else{
                        if(request.status == Request.RequestStatus.Done){
                            holder.layoutRating.setVisibility(View.VISIBLE);
                            holder.btn_dismiss.setVisibility(View.GONE);
                            holder.btn_approve_rating.setOnClickListener(v -> {
                                User.rateHelper(offer.helper_id, holder.ratingBar.getRating(), app.requestsRef.document(request.req_id));
                            });
                        }
                        else
                        {

                            holder.layoutRating.setVisibility(View.GONE);
                            holder.btn_dismiss.setVisibility(View.GONE);

                            holder.card.setOnClickListener(v->{
                                Intent chatIntent = new Intent(con, ChatActivity.class);
                                chatIntent.putExtra("offer", (Serializable) offer);
                                con.startActivity(chatIntent);
                            });
                        }
                    }

                } else {

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

        holder.layoutRating.setVisibility(View.GONE);
        holder.btn_dismiss.setVisibility(View.GONE);
        app.helpOffersRef.whereEqualTo("req_id", request.req_id).whereEqualTo("status", HelpOffer.OfferStatus.Pending)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0)
                            {
                                holder.setHelper("someone offered help!");

                                holder.card.setOnClickListener(v->{
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
