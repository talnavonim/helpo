package huji.postpc.y2021.talme.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GetAccountInfoActivity extends AppCompatActivity {
    EditText name;
    TextView email;
    ImageView photo;
    FloatingActionButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_account_info);
        HelpoApp app = HelpoApp.getInstance();
        name = findViewById(R.id.infoName);
        email = findViewById(R.id.InfoEmail);
        photo = findViewById(R.id.InfoImage);
        confirmButton = findViewById(R.id.ConfirmButton);
        GoogleSignInAccount acct = app.account;
        if (acct != null) {
            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
//            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            name.setText(personName);
            email.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(photo);
            confirmButton.setOnClickListener(v->
            {
                app.full_name = name.getText().toString();
                app.email = email.getText().toString();
                Intent confirmedUser = new Intent(GetAccountInfoActivity.this, RequestsActivity.class);
                startActivity(confirmedUser);
            });
        }
        else
        {
            Toast.makeText(GetAccountInfoActivity.this, "Failed to receive information - please resign", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}