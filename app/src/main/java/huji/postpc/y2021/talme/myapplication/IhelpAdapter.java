package huji.postpc.y2021.talme.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.Serializable;

public class IhelpAdapter extends FirestoreRecyclerAdapter<HelpOffer, IhelpAdapter.HelpOfferHolder> {
    Context con;


    public IhelpAdapter(@NonNull FirestoreRecyclerOptions<HelpOffer> options, Context con) {
        super(options);
        this.con = con;
    }


    @NonNull
    @Override
    public HelpOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.help_offer_row, parent, false);
        return new HelpOfferHolder(sender);
//        return null;
    }


    @Override
    protected void onBindViewHolder(@NonNull HelpOfferHolder holder, int position, @NonNull HelpOffer model) {

        HelpoApp app = HelpoApp.getInstance();
        holder.req_type.setText(model.requestType.toString());
        setStatus(holder, model.status);
        holder.requester.setText(model.requester_full_name);

        //enum OfferStatus {Pending, Ongoing, Done, Declined, Canceled, Rated, Hidden}
        switch (model.status)
        {
            case Pending:
            case Ongoing:

                holder.layoutRating.setVisibility(View.GONE);
                holder.btn_dismiss.setVisibility(View.GONE);
                holder.card.setOnClickListener(v->{
                    Intent chatIntent = new Intent(con, ChatActivity.class);
                    chatIntent.putExtra("offer", (Serializable) model);
                    con.startActivity(chatIntent);
                });
                return;
            case Done:

                holder.layoutRating.setVisibility(View.VISIBLE);
                holder.btn_dismiss.setVisibility(View.GONE);
                holder.btn_approve_rating.setOnClickListener(v -> {
                    User.rateRequester(model.requester_id, holder.ratingBar.getRating(), app.helpOffersRef.document(model.help_id));
                });
                return;
            case Declined:
            case Canceled:
            case Rated:
                holder.layoutRating.setVisibility(View.GONE);
                holder.btn_dismiss.setVisibility(View.VISIBLE);
                holder.btn_dismiss.setOnClickListener(v -> {
                    app.helpOffersRef.document(model.help_id).update("status", HelpOffer.OfferStatus.Hidden);
                });
            case Hidden:
        }

    }


    private void setStatus(HelpOfferHolder holder, HelpOffer.OfferStatus offerStatus) {
        holder.status.setText(offerStatus.toString());
    }

    public class HelpOfferHolder extends RecyclerView.ViewHolder {


        TextView requester;
        TextView status;
        TextView req_type;
        View view;
        CardView card;
        LinearLayout layoutRating;
        ImageButton btn_approve_rating;
        ImageButton btn_dismiss;
        RatingBar ratingBar;


        public HelpOfferHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            requester = itemView.findViewById(R.id.txt_req_name);
            status = itemView.findViewById(R.id.txt_req_status);
            req_type = itemView.findViewById(R.id.txt_req_type);
            card = itemView.findViewById(R.id.card_help_offer);

            btn_approve_rating = itemView.findViewById(R.id.imageButton_approve_rating);
            btn_dismiss = itemView.findViewById(R.id.imageButton_dismiss);
            ratingBar = itemView.findViewById(R.id.ratingBar_offer);
            layoutRating = itemView.findViewById(R.id.layout_rate);
        }
    }
}
