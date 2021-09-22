package huji.postpc.y2021.talme.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.io.Serializable;

public class IhelpAdapter extends FirestoreRecyclerAdapter<HelpOffer, IhelpAdapter.HelpOfferHolder> {
    private static final int VIEW_TYPE_GROCRIES = 1;
    private static final int VIEW_TYPE_MAIL = 2;

    HelpOfferList helpOfferList = null;

    public IhelpAdapter(@NonNull FirestoreRecyclerOptions<HelpOffer> options) {
        super(options);
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

        if (model.requestType == Request.RequestType.GROCERIES)
        {
            holder.req_type.setText("Groceries");
        }
        else  // mail
        {
            holder.req_type.setText("Groceries");
        }
        setStatus(holder, model.status);
        holder.requester.setText(model.requester_full_name);
        holder.requester.setOnClickListener(v->{ //todo set onclick for card not textview
            Intent chatIntent = new Intent(holder.view.getContext(), ChatActivity.class);
            chatIntent.putExtra("offer", (Serializable) model);
            holder.view.getContext().startActivity(chatIntent);
        });
    }


    private void setStatus(HelpOfferHolder holder, HelpOffer.OfferStatus offerStatus) {
        switch (offerStatus){
            case PENDING:
                holder.status.setText("Pending");
                break;
            case APPROVED:
                holder.status.setText("Approved");
                break;
            default:
                holder.status.setText("Error");
        }
    }

    public class HelpOfferHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView requester;
        TextView status;
        TextView req_type;
        View view;


        public HelpOfferHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            picture = itemView.findViewById(R.id.imageProfile);
            requester = itemView.findViewById(R.id.txt_req_name);
            status = itemView.findViewById(R.id.txt_req_status);
            req_type = itemView.findViewById(R.id.txt_req_type);
        }
    }
}
