package com.example.kharchapani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText name;
    EditText email_log;
    EditText pswd;
    Button logbtn;
    FirebaseDatabase fb;
    DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_log = findViewById(R.id.editTextEmail);
        pswd = findViewById(R.id.editTextPassword);
        logbtn = findViewById(R.id.cirLoginButton);
        fb = FirebaseDatabase.getInstance();
        dr = fb.getReference();


        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try{

                    dr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(email_log.getText().toString().indexOf(".")!=-1) {
                                if (snapshot.child(email_log.getText().toString().substring(0, (email_log.getText().toString().indexOf(".")))).exists()) {
                                    if (pswd.getText().toString().equals(snapshot.child(email_log.getText().toString().substring(0, (email_log.getText().toString().indexOf(".")))).child("pswd").getValue().toString())) {

                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        String name = email_log.getText().toString().substring(0, (email_log.getText().toString().indexOf(".")));
                                        i.putExtra("name", name);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Account Not Found Create A New One", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onLoginClick(View View) {
        startActivity(new Intent(this, Register.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);

    }
}