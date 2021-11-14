package com.example.apprestaurantportofoliio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText txtusername, txtpassword;
    Button btnsignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        txtusername =(EditText)findViewById(R.id.txtusername);
        txtpassword =(EditText)findViewById(R.id.txtpassword);
        btnsignin =(Button) findViewById(R.id.btnsignin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtusername.getText().toString().trim();;
                String password = txtpassword.getText().toString().trim();;

                if(user.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(user, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getApplicationContext(), "Sign In Success", Toast.LENGTH_SHORT).show();
                                        //clearText();
                                        //finish();
                                        Intent intent =  new Intent(getApplicationContext(), HomeActivity.class);
                                        intent.putExtra("email", user);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_SHORT).show();
                                        clearText();
                                    }

                                }

                            });
                    }

            }
        });
    }
    private void clearText() {
        txtusername.setText("");
        txtpassword.setText("");
    }
}