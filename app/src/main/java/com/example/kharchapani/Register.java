package com.example.kharchapani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText pswd,mobno;
    Button regbtn;
    FirebaseDatabase fb;
    DatabaseReference dr;
    Spinner spinner;
    ArrayList<String> currencies = new ArrayList();
    ArrayAdapter adapter;
    String currency;
    EditText cashbal, bankbal;
    TextInputLayout cashedit, bankedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        pswd = findViewById(R.id.editTextPassword);
        mobno = findViewById(R.id.editTextMobile);
        regbtn = findViewById(R.id.cirRegisterButton);
        fb = FirebaseDatabase.getInstance();
        dr = fb.getReference();
        SharedPreferences sharedPreferences = getSharedPreferences("one_time_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        currencies.add("INR ₹");currencies.add("USD $"); currencies.add("YEN ¥");

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails userdata = new UserDetails(name.getText().toString(), email.getText().toString(), pswd.getText().toString());
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (email.getText().toString().indexOf(".") != -1) {
                            if (snapshot.child(email.getText().toString().substring(0, (email.getText().toString().indexOf(".")))).exists()) {
                                Toast.makeText(getApplicationContext(), "Email Already Exists", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    dr.child(email.getText().toString().substring(0, (email.getText().toString().indexOf(".")))).setValue(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "User Successfully Added", Toast.LENGTH_SHORT).show();

                                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                                            ViewGroup viewGroup = findViewById(R.id.content);
                                            View view = View.inflate(Register.this,R.layout.dialog,viewGroup);
                                            builder.setView(view);
                                            AlertDialog alertDialog = builder.create();
                                            alertDialog.show();
                                            spinner = alertDialog.findViewById(R.id.spinner);
                                            cashbal = alertDialog.findViewById(R.id.cashbal);
                                            bankbal = alertDialog.findViewById(R.id.bankbal);
                                            cashedit = alertDialog.findViewById(R.id.editcashbal);
                                            bankedit = alertDialog.findViewById(R.id.editbankbal);
                                            adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner,currencies);
                                            spinner.setAdapter(adapter);

                                           spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                               @Override
                                               public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                   currency = spinner.getItemAtPosition(i).toString();
                                                   editor.putString("currency",currency.substring(4,5));

                                                   cashedit.setPrefixText(currency.substring(4,5));
                                                   bankedit.setPrefixText(currency.substring(4,5));
                                               }

                                               @Override
                                               public void onNothingSelected(AdapterView<?> adapterView) {

                                               }
                                           });

                                            AppCompatButton btn = alertDialog.findViewById(R.id.okbtn);

                                            btn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                   dr.child(email.getText().toString().substring(0, (email.getText().toString().indexOf(".")))).child("bankbal").setValue(Integer.parseInt(bankbal.getText().toString()));
                                                    dr.child(email.getText().toString().substring(0, (email.getText().toString().indexOf(".")))).child("cashbal").setValue(Integer.parseInt(cashbal.getText().toString()));
                                                    Intent i = new Intent(Register.this, MainActivity.class);
                                                    Toast.makeText(getApplicationContext(), ""+email.getText().toString(), Toast.LENGTH_SHORT).show();
                                                   String name = email.getText().toString().substring(0, (email.getText().toString().lastIndexOf(".")));
                                                   int cashbalance = Integer.parseInt(cashbal.getText().toString());
                                                   int bankbalance = Integer.parseInt(bankbal.getText().toString());
                                                   FirebaseDatabase.getInstance().getReference().child(name).child("cashbal").setValue(cashbalance);
                                                    FirebaseDatabase.getInstance().getReference().child(name).child("bankbal").setValue(bankbalance);
                                                    editor.putString("username",name);
                                                    editor.putString("current_user",name);
                                                    editor.putBoolean("Logged?",true);
                                                    editor.commit();
                                                   startActivity(i);
                                                }
                                            });

                                        }
                                    });
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error is" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}