package huji.postpc.y2021.talme.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myRequestGroceriesViewHolder extends RecyclerView.ViewHolder{
    ImageView picture;
    TextView message;

    public myRequestGroceriesViewHolder(@NonNull View itemView) {
        super(itemView);
        picture = itemView.findViewById(R.id.imageView);
        message = itemView.findViewById(R.id.itemTextView);
    }

}
