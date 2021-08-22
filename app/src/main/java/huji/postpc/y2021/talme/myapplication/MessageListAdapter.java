package huji.postpc.y2021.talme.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

//    private Context mContext; TODO really needed?
    private List<Message> mMessageList;
    ChatMessageHolder chatHolder = null;

//    public MessageListAdapter(Context context, List<Message> messageList) {
//        mContext = context;
//        mMessageList = messageList;
//    } todo really needed??

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        Message bubbleChat = chatHolder.getChat().get(position);
        return bubbleChat.getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType ==VIEW_TYPE_MESSAGE_SENT){
            View sender = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_bg_outgoing_bubble, parent, false);
            return new SentMessageViewHolder(sender);
        }
        if(viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            View receiver = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_bg_incoming_bubble, parent, false);
            return new ReceivedMessageViewHolder(receiver);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message bubbleChat = chatHolder.getChat().get(position);
        if(bubbleChat.equals(VIEW_TYPE_MESSAGE_SENT)){
            TextView user_message_txv = ((SentMessageViewHolder) holder).message;
            TextView user_message_hour_txv = ((SentMessageViewHolder) holder).hour;
            user_message_txv.setText(bubbleChat.getMessage());
            int hour = bubbleChat.getTime().hour;
            int minutes = bubbleChat.getTime().minute;
            String time = hour + ":" + minutes;
            user_message_hour_txv.setText(time);
        }
        else{
            TextView user_message_txv = ((ReceivedMessageViewHolder) holder).message;
            TextView user_message_hour_txv = ((ReceivedMessageViewHolder) holder).hour;
            user_message_txv.setText(bubbleChat.getMessage());
            int hour = bubbleChat.getTime().hour;
            int minutes = bubbleChat.getTime().minute;
            String time = hour + ":" + minutes;
            user_message_hour_txv.setText(time);
        }

    }
}