package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.firebase.geofire.GeoFireUtils;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;

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
    FloatingActionButton addSugar;
    FloatingActionButton removeSugar;
    TextView textSugar;
    FloatingActionButton addEggs;
    FloatingActionButton removeEggs;
    TextView textEggs;
    FloatingActionButton addMilk;
    FloatingActionButton removeMilk;
    TextView textMilk;
    FloatingActionButton addOil;
    FloatingActionButton removeOil;
    TextView textOil;
    EditText comment;
//    Button groceries;
//    Button mail;
    EditText mailAddress;
    EditText mailType;
    EditText textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        app = HelpoApp.getInstance();

        groceriesConteiner = findViewById(R.id.groceries_selected);
        mailConteiner = findViewById(R.id.mail_selected);
//        groceries = findViewById(R.id.groceries_button);
//        mail = findViewById(R.id.mail_button);

        new_request = new Request(UUID.randomUUID().toString(), app.user.getName());

        addBread = findViewById(R.id.add_bred);
        removeBread = findViewById(R.id.remove_bred);
        textBread = findViewById(R.id.text_bread);
        addSugar = findViewById(R.id.add_sugar);
        removeSugar = findViewById(R.id.remove_sugar);
        textSugar = findViewById(R.id.text_sugar);
        addEggs = findViewById(R.id.add_eggs);
        removeEggs = findViewById(R.id.remove_eggs);
        textEggs = findViewById(R.id.text_eggs);
        addMilk = findViewById(R.id.add_milk);
        removeMilk = findViewById(R.id.remove_milk);
        textMilk = findViewById(R.id.text_milk);
        addOil = findViewById(R.id.add_oil);
        removeOil = findViewById(R.id.remove_oil);
        textOil = findViewById(R.id.text_oil);
        textAddress = findViewById(R.id.edittext_address);

        sendRequestButton = findViewById(R.id.send_request);
        comment = findViewById(R.id.edittext_comment);

        mailAddress = findViewById(R.id.edittext_commend_mail);
        mailType = findViewById(R.id.edittext_type_mail);

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
        sendRequestButton.setOnClickListener(v -> {
            if(groceriesConteiner.getVisibility() == View.VISIBLE){
                new_request.setType(Request.RequestType.GROCERIES);
                new_request.setComment(comment.getText().toString());
            }
            else if(mailConteiner.getVisibility() == View.VISIBLE){
                new_request.setType(Request.RequestType.MAIL);
                new_request.setComment(comment.getText().toString());
                new_request.setMailType(mailType.getText().toString());
                new_request.setMailLocation(mailAddress.getText().toString());
            }
            else{
                Toast toast = Toast.makeText(app, "You need to select type of helpo first!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            String address = textAddress.getText().toString();
            new_request.setAddress(address);
            LatLng loc = getLocationFromAddress(app, address);
            if (loc == null)
            {
                Toast.makeText(app, "Wrong address!", Toast.LENGTH_SHORT).show();
                return;
            }
            new_request.setLat(loc.latitude);
            new_request.setLng(loc.longitude);
            String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(loc.latitude, loc.longitude));
            new_request.setGeohash(hash);
//            new_request.setRequest_email(app.account.getEmail());
//            new_request.setFull_name(app.account.getDisplayName()); todo add display name and email
            DocumentReference orderRef = app.firestore.collection(app.REQUESTS).document(new_request.req_id);
            orderRef.set(new_request)
                    .addOnSuccessListener(result -> {
                        Intent intent = new Intent(app, RequestsActivity.class);
                        startActivity(intent);
                    }).addOnFailureListener(u -> {
                Toast toast = Toast.makeText(app, "Failed to send request!", Toast.LENGTH_SHORT);
                toast.show();
            });
        });
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
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
        addSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sugar = new_request.addSugar();
                textSugar.setText(""+sugar+" begs of sugar");
            }
        });
        removeSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sugar = new_request.removeSugar();
                textSugar.setText(""+sugar+" begs of sugar");
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