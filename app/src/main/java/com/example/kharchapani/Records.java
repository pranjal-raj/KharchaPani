package com.example.kharchapani;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Records extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference myref2;
    RecordViewAdapter adapter;
    ArrayList<RecordsView> recordsViews = new ArrayList<>();
    String name;
    ArrayList<DateItem> dateItems= new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        FirebaseDatabase fb = FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences("one_time_details", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("current_user", "");
        myref2 = fb.getReference().child(name).child(getIntent().getStringExtra("type"));
        recyclerView = findViewById(R.id.recyclerView);
        getData(myref2);



    }


    public void getData(DatabaseReference reference2)
    {
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot i:snapshot.getChildren())
                {
                    //Toast.makeText(getApplicationContext(), ""+i.getKey(), Toast.LENGTH_SHORT).show();
                    try {

                        for (DataSnapshot j : i.getChildren()) {
                            //Toast.makeText(getApplicationContext(), ""+j.getKey(), Toast.LENGTH_SHORT).show();
                            for (DataSnapshot k :j.getChildren()) {
                                ArrayList<Record> rcrds = new ArrayList<>();
                                //Toast.makeText(getApplicationContext(), ""+k.getKey(), Toast.LENGTH_SHORT).show();
                                for (DataSnapshot l: k.getChildren()) {

                                    try {
                                        //Toast.makeText(getApplicationContext(), ""+l.getKey(), Toast.LENGTH_SHORT).show();
                                        rcrds.add(snapshot.child(i.getKey()).child(j.getKey()).child(k.getKey()).child(l.getKey()).getValue(Record.class));
                                        String msg = snapshot.child(i.getKey()).child(j.getKey()).child(k.getKey()).child(l.getKey()).getValue(Record.class).getCategory();

                                    } catch (Exception e) {}
                                }
                                recordsViews.add(new RecordsView(k.getKey(),rcrds));
                            }

                        }



                    }catch(Exception e){ Toast.makeText(Records.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show(); }
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(Records.this);
                recyclerView.setLayoutManager(layoutManager);
                Collections.reverse(recordsViews);
                adapter = new RecordViewAdapter(recordsViews,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }



}