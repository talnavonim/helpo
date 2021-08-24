package huji.postpc.y2021.talme.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myRequestsAdapter extends RecyclerView.Adapter{
    private static final int VIEW_TYPE_GROCRIES = 1;
    private static final int VIEW_TYPE_MAIL = 2;

    myRequestHolder myRequestHolder = null;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==VIEW_TYPE_GROCRIES){
            View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_grocries_item, parent, false);
            return new SentMessageViewHolder(sender);
        }
        if(viewType == VIEW_TYPE_MAIL){
            View receiver = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_mail_item, parent, false);
            return new ReceivedMessageViewHolder(receiver);
        }
        View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_requests_mail_item, parent, false);
        return new SentMessageViewHolder(sender);

    }

    @Override
    public int getItemViewType(int position) {
        Request curr_request = myRequestHolder.getMyRequest().get(position);
        if(curr_request.getType()== Request.RequestType.GROCERIES){
            return VIEW_TYPE_GROCRIES;
        }
        else{
            return VIEW_TYPE_MAIL;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Request curr_request = myRequestHolder.getMyRequest().get(position);
        if(curr_request.getType()== Request.RequestType.GROCERIES){

        }
    }

    @Override
    public int getItemCount() {
        return myRequestHolder.getMyRequest().size();
    }

    public void setMyRequestHolder(myRequestHolder myRequestHolder){
        this.myRequestHolder = myRequestHolder;
    }
}
