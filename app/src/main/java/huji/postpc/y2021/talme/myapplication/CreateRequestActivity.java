package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class CreateRequestActivity extends AppCompatActivity {
    MaterialButtonToggleGroup selectHelpoType;
    ConstraintLayout groceriesConteiner;
    ConstraintLayout mailConteiner;

    Request new_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        groceriesConteiner = findViewById(R.id.groceries_selected);
        mailConteiner = findViewById(R.id.mail_selected);


        selectHelpoType = findViewById(R.id.select_helpo_type);
        selectHelpoType.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    switch (checkedId){
                        case R.id.groceries_button:
                            if(mailConteiner.getVisibility() == View.VISIBLE){
                                mailConteiner.setVisibility(View.INVISIBLE);
                            }
                            groceriesConteiner.setVisibility(View.VISIBLE);

                            break;
                        case R.id.mail_button:
                            if(groceriesConteiner.getVisibility() == View.VISIBLE){
                                groceriesConteiner.setVisibility(View.INVISIBLE);
                            }
                            mailConteiner.setVisibility(View.VISIBLE);
                            break;

                    }

                }
            }
        });

    }
}