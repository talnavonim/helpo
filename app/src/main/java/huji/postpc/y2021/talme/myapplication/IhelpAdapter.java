package huji.postpc.y2021.talme.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

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
        setStatus(holder, model.requestStatus);
        holder.requester.setText(model.requester_full_name);

    }


//    @Override
//    public int getItemViewType(int position) {
////        Request curr_help = ihelpHolder.getIhelps().get(position);
////        if(curr_help.getType()== Request.RequestType.GROCERIES){
////            return VIEW_TYPE_GROCRIES;
////        }
////        else{
////            return VIEW_TYPE_MAIL;
////        }
//        return 0;
//    }

    private void setStatus(HelpOfferHolder holder, Request.RequestStatus requestStatus) {
        switch (requestStatus){
            case IN_PROGRESS:
                holder.status.setText("In progress");
                break;
            case WAITING:
                holder.status.setText("Waiting");
                break;
            case READY:
                holder.status.setText("Ready");
                break;
            case DONE:
                holder.status.setText("Done");
                break;
            default:
                holder.status.setText("Error");
        }
    }

//    @Override
//    public int getItemCount() {
////        return ihelpHolder.getIhelps().size();
//        return 0;
//    }

//    public void setIhelpHolder(HelpOfferList helpOfferList){
////        this.ihelpHolder = ihelpHolder;
//    }

    public class HelpOfferHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView requester;
        TextView status;
        TextView req_type;


        public HelpOfferHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.imageProfile);
            requester = itemView.findViewById(R.id.txt_req_name);
            status = itemView.findViewById(R.id.txt_req_status);
            req_type = itemView.findViewById(R.id.txt_req_type);
        }
    }
}
