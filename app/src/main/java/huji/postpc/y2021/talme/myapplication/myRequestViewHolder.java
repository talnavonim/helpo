package huji.postpc.y2021.talme.myapplication;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class myRequestViewHolder extends RecyclerView.ViewHolder{
    public View view;
    TextView helper;
    TextView status;
    TextView req_type;
    CardView card;
    ImageButton btn_dismiss, btn_approve_rating;
    RatingBar ratingBar;
    LinearLayout layoutRating;

    public myRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        helper = itemView.findViewById(R.id.txt_req_name);
        status = itemView.findViewById(R.id.txt_req_status);
        req_type = itemView.findViewById(R.id.txt_req_type);
        card = itemView.findViewById(R.id.card_request_row);
        btn_approve_rating = itemView.findViewById(R.id.imageButton_approve_rating);
        btn_dismiss = itemView.findViewById(R.id.imageButton_dismiss);
        ratingBar = itemView.findViewById(R.id.ratingBar_request);
        layoutRating = itemView.findViewById(R.id.layout_rate);
    }

    public void setReq_type(Request.RequestType type) {
        this.req_type.setText(type.toString());
    }


    public void setHelper(String m){
        this.helper.setText(m);
    }


    public void setStatus(Request.RequestStatus status) {
        this.status.setText(status.toString());
    }

}
