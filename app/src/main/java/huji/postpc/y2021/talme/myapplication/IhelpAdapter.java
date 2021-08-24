package huji.postpc.y2021.talme.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IhelpAdapter extends RecyclerView.Adapter<IhelpAdapter.HelpOfferHolder>{
    private static final int VIEW_TYPE_GROCRIES = 1;
    private static final int VIEW_TYPE_MAIL = 2;

    HelpOfferList helpOfferList = null;


    @NonNull
    @Override
    public HelpOfferHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType ==VIEW_TYPE_GROCRIES){
            View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_grocries_item, parent, false);
            return new HelpOfferHolder(sender);
        }
        if(viewType == VIEW_TYPE_MAIL){
            View receiver = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_mail_item, parent, false);
            return new HelpOfferHolder(receiver);
        }
        View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_mail_item, parent, false);
        return new HelpOfferHolder(sender);
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HelpOfferHolder holder, int position) {
//        Request curr_help = holder.getIhelps().get(position);
//        if(curr_help.getType()== Request.RequestType.GROCERIES){
//
//        }

    }

    @Override
    public int getItemViewType(int position) {
//        Request curr_help = ihelpHolder.getIhelps().get(position);
//        if(curr_help.getType()== Request.RequestType.GROCERIES){
//            return VIEW_TYPE_GROCRIES;
//        }
//        else{
//            return VIEW_TYPE_MAIL;
//        }
        return 0;
    }



    @Override
    public int getItemCount() {
//        return ihelpHolder.getIhelps().size();
        return 0;
    }

    public void setIhelpHolder(HelpOfferList helpOfferList){
//        this.ihelpHolder = ihelpHolder;
    }

    public class HelpOfferHolder extends RecyclerView.ViewHolder {
        public HelpOfferHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
