package com.example.apprestaurantportofoliio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText txtusername, txtpassword;
    Button btnsignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        txtusername =(EditText)findViewById(R.id.txtusername);
        txtpassword =(EditText)findViewById(R.id.txtpassword);
        btnsignin =(Button) findViewById(R.id.btnsignin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtusername.getText().toString();
                String password = txtpassword.getText().toString();
                if (user.equalsIgnoreCase("admin")
                        && password.equalsIgnoreCase("12345")){
                    Toast.makeText(Login.this, "Login Sukses", Toast.LENGTH_SHORT).show();
                    Intent move = new Intent(Login.this, HomeActivity.class);
                    move.putExtra("user", user);
                    startActivity(move);
                }
                else{
                    Toast.makeText(Login.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}