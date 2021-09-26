package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.geofire.GeoFireUtils;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class CreateRequestActivity extends AppCompatActivity {
    HelpoApp app;
//    myRequestList holder;

    MaterialButtonToggleGroup selectHelpoType;
    ImageButton sendRequestButton;
    Request.RequestType type;


    EditText requestDescription;
    EditText textAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        app = HelpoApp.getInstance();
        type = Request.RequestType.Groceries;


        textAddress = findViewById(R.id.edittext_address);

        sendRequestButton = findViewById(R.id.send_request);
        requestDescription = findViewById(R.id.request_description);


        selectHelpoType = findViewById(R.id.select_helpo_type);
        selectHelpoType.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    switch (checkedId){
                        case R.id.groceries_button:
                            type = Request.RequestType.Groceries;
                            break;
                        case R.id.mail_button:
                            type = Request.RequestType.Mail;
                            break;
                        case R.id.other_button:
                            type = Request.RequestType.Other;
                            break;

                    }
                }
            }
        });
        sendRequestButton.setOnClickListener(v -> {

            String address = textAddress.getText().toString();
            LatLng loc = getLocationFromAddress(app, address);
            if (loc == null)
            {
                Toast.makeText(app, "Wrong address!", Toast.LENGTH_SHORT).show();
                return;
            }
            //public Request(String user_id, RequestType type, String address, String description, String full_name, double lat, double lng) {
            Request request = new Request(app.user_id, type, address, requestDescription.getText().toString(), app.full_name, loc.latitude, loc.longitude);
            DocumentReference orderRef = app.requestsRef.document(request.req_id);
            orderRef.set(request)
                    .addOnSuccessListener(result -> {
                        Intent intent = new Intent(app, RequestsActivity.class);
                        startActivity(intent);
                    }).addOnFailureListener(u -> {
                Toast toast = Toast.makeText(app, "Failed to send request!", Toast.LENGTH_SHORT);
                toast.show();
            });
//            holder.addRequest(new_request);
            Intent intent = new Intent(app, RequestsActivity.class);
            startActivity(intent);
        });

        //add to RequestsActivity to the recycleView

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

}