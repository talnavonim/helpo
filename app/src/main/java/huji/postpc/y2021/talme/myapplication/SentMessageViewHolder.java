package huji.postpc.y2021.talme.myapplication;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SentMessageViewHolder extends RecyclerView.ViewHolder {
    TextView message;
    TextView hour;


    public SentMessageViewHolder(@NonNull  View itemView) {
        super(itemView);
//        message = itemView.findViewById(R.id.text_gchat_message_me);
//        hour = itemView.findViewById(R.id.text_gchat_timestamp_me);
    }

}