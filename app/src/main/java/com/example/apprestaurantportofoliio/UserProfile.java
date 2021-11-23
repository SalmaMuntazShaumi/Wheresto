package com.example.apprestaurantportofoliio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {

    String username, phone, email, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile);

        TextView user_name = findViewById(R.id.user_name);
        EditText txt_username = findViewById(R.id.username_et);
        EditText txt_phone = findViewById(R.id.phone_et);
        EditText txt_email = findViewById(R.id.email_et);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            phone = bundle.getString("phone");
            username = bundle.getString("username");
            email = bundle.getString("email");
            name = bundle.getString("username");


            txt_username.setText(username);
            txt_phone.setText(phone);
            txt_email.setText(email);
            user_name.setText(username);
        }


        Button btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myReference = database.getReference().child("profile");

                username = txt_username.getText().toString();
                phone = txt_phone.getText().toString();
                email = txt_email.getText().toString();

                ProfileModel profileModel = new ProfileModel(username, phone, email);
                myReference.setValue(profileModel);
                Toast.makeText(getApplicationContext(), "Data has been edited", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }

        });
    }

}
