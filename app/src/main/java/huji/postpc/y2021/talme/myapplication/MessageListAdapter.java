package huji.postpc.y2021.talme.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MessageListAdapter extends FirestoreRecyclerAdapter<Message, MessageListAdapter.messageHolder> {


    public MessageListAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }


    class messageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;



        public messageHolder(@NonNull View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message);
            timeText = (TextView) itemView.findViewById(R.id.text_timestamp);

        }

    }

    @NonNull
    @Override
    public messageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sender = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new messageHolder(sender);
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isSent(HelpoApp.getInstance().user_id))
        {
            return R.layout.outgoing_message_layout_bubble;
        }
        else
        {
            return R.layout.incoming_message_layout_bubble;
        }
    }


    @Override
    protected void onBindViewHolder(@NonNull messageHolder holder, int position, @NonNull Message model) {

        holder.timeText.setText(model.timeToString());
        holder.messageText.setText(model.getMessage());
    }

}