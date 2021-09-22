package huji.postpc.y2021.talme.myapplication;
//
//public class ReceivedMessageViewHolder {
//}

import android.view.View;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

public class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
    TextView message;
    TextView hour;
    TextView sender;

    public ReceivedMessageViewHolder(@NonNull  View itemView) {
        super(itemView);
//        message = itemView.findViewById(R.id.text_gchat_message_other);
//        hour = itemView.findViewById(R.id.text_gchat_timestamp_other);
//        sender = itemView.findViewById(R.id.text_full_name);
    }
}
