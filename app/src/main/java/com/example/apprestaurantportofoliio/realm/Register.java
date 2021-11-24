package com.example.apprestaurantportofoliio.realm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apprestaurantportofoliio.HomeActivity;
import com.example.apprestaurantportofoliio.Login;
import com.example.apprestaurantportofoliio.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText txtusername, txtemail, txtpassword;
    Button btnsignup;
    TextView txtsignin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtusername= findViewById(R.id.txtusername);
        txtemail = findViewById(R.id.txtemail);
        txtpassword = findViewById(R.id.txtpassword);
        btnsignup = findViewById(R.id.btnsignup);
        txtsignin = findViewById(R.id.txtsignin);

        fAuth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //untuk mengconvert edit text ke string
                String email = txtemail.getText().toString().trim();
                String password = txtpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    txtpassword.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    txtpassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6 ){
                    txtpassword.setError("Password must Be more than 6 characters");
                    return;
                }
                //membuat user dan email di fire base,email dan password di masukin ke firebase sebagai account
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }else {

                            Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }
}