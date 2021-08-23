package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.UUID;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateRequestActivity extends AppCompatActivity {
    HelpoApp app;

    MaterialButtonToggleGroup selectHelpoType;
    ConstraintLayout groceriesConteiner;
    ConstraintLayout mailConteiner;
    ImageButton sendRequestButton;

    Request new_request;

    FloatingActionButton addBread;
    FloatingActionButton removeBread;
    TextView textBread;
    FloatingActionButton addShuger;
    FloatingActionButton removeShuger;
    TextView textShuger;
    FloatingActionButton addEggs;
    FloatingActionButton removeEggs;
    TextView textEggs;
    FloatingActionButton addMilk;
    FloatingActionButton removeMilk;
    TextView textMilk;
    FloatingActionButton addOil;
    FloatingActionButton removeOil;
    TextView textOil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        app = HelpoApp.getInstance();

        groceriesConteiner = findViewById(R.id.groceries_selected);
        mailConteiner = findViewById(R.id.mail_selected);
        sendRequestButton = findViewById(R.id.send_request);

        new_request = new Request(UUID.randomUUID().toString(), app.user.getName());

        addBread = findViewById(R.id.add_bred);
        removeBread = findViewById(R.id.remove_bred);
        textBread = findViewById(R.id.text_bread);
        addShuger = findViewById(R.id.add_shuger);
        removeShuger = findViewById(R.id.remove_shuger);
        textShuger = findViewById(R.id.text_shuger);
        addEggs = findViewById(R.id.add_eggs);
        removeEggs = findViewById(R.id.remove_eggs);
        textEggs = findViewById(R.id.text_eggs);
        addMilk = findViewById(R.id.add_milk);
        removeMilk = findViewById(R.id.remove_milk);
        textMilk = findViewById(R.id.text_milk);
        addOil = findViewById(R.id.add_oil);
        removeOil = findViewById(R.id.remove_oil);
        textOil = findViewById(R.id.text_oil);

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
                            groceryFunc();
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

    private void groceryFunc(){
        addBread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bread = new_request.addBread();
                textBread.setText(""+bread+" breads");
            }
        });
        removeBread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bread = new_request.removeBread();
                textBread.setText(""+bread+" breads");
            }
        });
        addShuger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shuger = new_request.addShuger();
                textShuger.setText(""+shuger+" begs of shuger");
            }
        });
        removeShuger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int shuger = new_request.removeShuger();
                textShuger.setText(""+shuger+" begs of shuger");
            }
        });
        addEggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int egg = new_request.addEggs();
                textEggs.setText(""+egg+" eggs");
            }
        });
        removeEggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int egg = new_request.removeEggs();
                textEggs.setText(""+egg+" eggs");
            }
        });
        addMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int milk = new_request.addMilk();
                textMilk.setText(""+milk+ "milk cans");
            }
        });
        removeMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int milk = new_request.removeMilk();
                textMilk.setText(""+milk+ "milk cans");
            }
        });
        addOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oil = new_request.addOil();
                textOil.setText(""+oil+" oil cans");
            }
        });
        removeOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int oil = new_request.removeOil();
                textOil.setText(""+oil+" oil cans");
            }
        });
    }
}