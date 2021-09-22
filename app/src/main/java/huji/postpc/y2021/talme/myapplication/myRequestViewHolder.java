package huji.postpc.y2021.talme.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myRequestViewHolder extends RecyclerView.ViewHolder{
    public View view;
    ImageView picture;
    TextView helper;
    TextView status;
    TextView req_type;

    public myRequestViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        picture = itemView.findViewById(R.id.imageProfile);
        helper = itemView.findViewById(R.id.txt_req_name);
        status = itemView.findViewById(R.id.txt_req_status);
        req_type = itemView.findViewById(R.id.txt_req_type);
    }

    public void setReq_type(Request.RequestType type) {
        if (type == Request.RequestType.GROCERIES)
        {
            this.req_type.setText("Groceries");
        }
        else  // mail
        {
            this.req_type.setText("Mail");
        }
    }

    public void setPicture(ImageView picture) {
        this.picture = picture; // todo
    }


    public void setHelper(String m){
        this.helper.setText(m);
    }


    public void setStatus(Request.RequestStatus status) {
        switch (status){
            case IN_PROGRESS:
                this.status.setText("In progress");
                break;
            case WAITING:
                this.status.setText("Waiting");
                break;
            case READY:
                this.status.setText("Ready");
                break;
            case DONE:
                this.status.setText("Done");
                break;
            default:
                this.status.setText("Error");
        }
    }

}
