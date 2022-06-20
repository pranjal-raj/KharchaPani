package com.example.kharchapani;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    public static TextView cashbal, bankbal;
    FirebaseDatabase firebaseDatabase;
    ImageButton print ;
    ArrayList<Record> rcrds = new ArrayList<>();

    VPAdapter adapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewpager2);
        cashbal = findViewById(R.id.cashbal);
        print = findViewById(R.id.printbtn);
        bankbal = findViewById(R.id.bankbal);
        updatebal();
        Intent i = getIntent();
        String name = i.getStringExtra("name");
       // Toast.makeText(getApplicationContext(), "" + name, Toast.LENGTH_SHORT).show();
        firebaseDatabase = FirebaseDatabase.getInstance();


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);

                createMyPDF(view);


            }
        });


        FragmentManager fmanager = getSupportFragmentManager();
        adapter = new VPAdapter(fmanager, getLifecycle());
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //  tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        AppCompatButton show_expenses = findViewById(R.id.show_expenses);
        show_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Records.class);
                i.putExtra("type", "Exepenses");
                startActivity(i);
            }
        });

        AppCompatButton show_income = findViewById(R.id.show_income);
        show_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, Records.class);
                i.putExtra("type", "Income");
                startActivity(i);
            }
        });


    }



    public void createMyPDF(View view) {

            PdfDocument myPdfDocument = new PdfDocument();
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
            PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
            ArrayList<Record> records = new ArrayList<>();
            records =getRecords();
            Paint myPaint = new Paint();
            myPaint.setTextSize(5f);
            String msg="";
            int x = 10, y = 25;
        for (Record r : records)
        {
            msg = msg + "\n\n" + " Date :" + r.getDate() + " Account :" +r.getAccount()+ " Category :" +r.getCategory() + " Amount : $" +r.getAccount()+" Opening Balance :" +r.getOpeningbal()+ " Closing Balance :" +r.getClosingbal();
        }
          String  myString = "Hello";
            for (String line : msg.split("\n")) {
                myPage.getCanvas().drawText(line, x, y, myPaint);
                y += myPaint.descent() - myPaint.ascent();
            }

            myPdfDocument.finishPage(myPage);

//            String myFilePath = Environment.getExternalStorageDirectory().getPath() + "/myPDFFile.pdf";
        String mPath;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
           // mPath= (MainActivity.this).getExternalFilesDir(Environment.DIRECTORY_DCIM) + "/" + "mypdf.pdf";
         File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, "geeksDat.pdf");
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getApplicationContext(), ""+file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

        }

//        }
//        else
//        {
//            mPath= Environment.getExternalStorageDirectory().toString() + "/" +"mypdf.pdf";
//        }
//            File myFile = new File(mPath);
//            try {
//                myPdfDocument.writeTo(new FileOutputStream(myFile));
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }

            myPdfDocument.close();
        }

        public void updatebal()
        {
            FirebaseDatabase.getInstance().getReference().child("pranjal@gmail").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild("cashbal")) {
                        cashbal.setText(snapshot.child("cashbal").getValue().toString());
                        bankbal.setText(snapshot.child("bankbal").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




    public ArrayList<Record> getRecords( )
    {

        FirebaseDatabase.getInstance().getReference().child("pranjal2@gmail").child("Exepenses").addListenerForSingleValueEvent(new ValueEventListener() {
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

                                //Toast.makeText(getApplicationContext(), ""+k.getKey(), Toast.LENGTH_SHORT).show();
                                for (DataSnapshot l: k.getChildren()) {

                                    try {
                                        //Toast.makeText(getApplicationContext(), ""+l.getKey(), Toast.LENGTH_SHORT).show();
                                        rcrds.add(snapshot.child(i.getKey()).child(j.getKey()).child(k.getKey()).child(l.getKey()).getValue(Record.class));
                                        String msg = snapshot.child(i.getKey()).child(j.getKey()).child(k.getKey()).child(l.getKey()).getValue(Record.class).getCategory();

                                    } catch (Exception e) {}
                                }

                            }

                        }



                    }catch(Exception e){ Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show(); }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return rcrds;
    }



















    }











