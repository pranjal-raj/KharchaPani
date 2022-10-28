package com.example.kharchapani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.YuvImage;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import jahirfiquitiva.libs.fabsmenu.FABsMenu;
import jahirfiquitiva.libs.fabsmenu.FABsMenuListener;
import jahirfiquitiva.libs.fabsmenu.TitleFAB;


public class Dashboard extends Fragment {


    public static String arg_object = "order";
    String title;
    TextView tv;
    Bundle bundle;
    TitleFAB add;
    AppCompatButton Fab_menu;
    PieChart piechart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref;
    Calendar calendar = Calendar.getInstance();
    AppCompatButton prev, next;
    TextView dateview;
    String centretext;
    ArrayList<LegendEntry> legendEntries =new ArrayList<>();
    LegendEntry legendEntry = new LegendEntry();
    TextView cashbal, bankbal;
    float arr[];
    String name;


    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = getArguments();
        String type = bundle.getString("order");
        piechart = view.findViewById(R.id.chart);
        prev = view.findViewById(R.id.prev_btn);
        next = view.findViewById(R.id.next_btn);
        dateview = view.findViewById(R.id.dateview);
        TextView stats = view.findViewById(R.id.stats);

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Analytics.class ));
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("one_time_details", Context.MODE_PRIVATE);
        name = sharedPreferences.getString("current_user", "");
        firebaseDatabase = FirebaseDatabase.getInstance();
        myref = firebaseDatabase.getReference().child(name).child(type);
        // dateview.setText(getCalculatedDate(calendar,"MMMM dd, yyyy",0));
        TabLayout tabLayout = view.findViewById(R.id.linearLayout);
        tabLayout.selectTab(tabLayout.getTabAt(0));
        dateview.setText(getCalculatedDate(calendar, "MMMM dd, yyyy", Calendar.DAY_OF_YEAR, 0, myref));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getText().toString())
                {
                    case "Day":
                       // Calendar cal = Calendar.getInstance();
                        dateview.setText(getCalculatedDate(calendar, "MMMM dd, yyyy", Calendar.DAY_OF_YEAR, 0, myref));
                        break;
                    case "Month":
                        //Calendar calm = Calendar.getInstance();
                        dateview.setText(getCalculatedDate(calendar, "MMMM", Calendar.MONTH, 0, myref));
                        break;
                    case "Year":
                       // Calendar caly = Calendar.getInstance();
                        dateview.setText(getCalculatedDate(calendar, "yyyy", Calendar.YEAR, 0, myref));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString()) {
                    case "Day":
                        dateview.setText(getCalculatedDate(calendar, "MMMM dd, yyyy", Calendar.DAY_OF_YEAR, -1, myref));
                        break;
                    case "Month":
                        dateview.setText(getCalculatedDate(calendar, "MMMM", Calendar.MONTH, -1, myref));
                        break;
                    case "Year":
                        dateview.setText(getCalculatedDate(calendar, "yyyy", Calendar.YEAR, -1, myref));
                        break;
                }


            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getText().toString()) {
                    case "Day":
                        dateview.setText(getCalculatedDate(calendar, "MMMM dd, yyyy", Calendar.DAY_OF_YEAR, 1, myref));
                        break;
                    case "Month":
                        dateview.setText(getCalculatedDate(calendar, "MMMM", Calendar.MONTH, 1, myref));
                        break;
                    case "Year":
                        dateview.setText(getCalculatedDate(calendar, "yyyy", Calendar.YEAR, 1, myref));
                        break;
                }
            }
        });

        final FABsMenu menu = view.findViewById(R.id.fabs_menu);
        menu.setMenuUpdateListener(new FABsMenuListener() {
            // You don't need to override all methods. Just the ones you want.

            @Override
            public void onMenuClicked(FABsMenu fabsMenu) {
                super.onMenuClicked(fabsMenu); // Default implementation opens the menu on click
                //   Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
                add = view.findViewById(R.id.green_fab);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (type.equals("Exepenses")) {

                            getParentFragmentManager().beginTransaction().replace(R.id.fcont, new add_expense()).addToBackStack(null).commit();
                        } else if (type.equals("Income")) {
                            getParentFragmentManager().beginTransaction().replace(R.id.fcont, new add_income()).addToBackStack(null).commit();
                        } else {
                        }
                    }
                });
            }

            @Override
            public void onMenuCollapsed(FABsMenu fabsMenu) {
                super.onMenuCollapsed(fabsMenu);

            }

            @Override
            public void onMenuExpanded(FABsMenu fabsMenu) {
                super.onMenuExpanded(fabsMenu);

            }
        }); }


    public void setupPieChart(String centretext) {
        piechart.setDrawHoleEnabled(true);
        piechart.setUsePercentValues(true);
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setEntryLabelTextSize(8f);
        piechart.setCenterText(centretext);
        piechart.setCenterTextSize(16f);
        piechart.setCenterTextColor(Color.parseColor("#5D6541"));
        Legend legend = piechart.getLegend();

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        //legend.setCustom(legendEntries);


        piechart.getDescription().setEnabled(false);
    }

    public void loadPieChart(float ff, float sf, float tf, float of) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        legendEntries.clear();
        String food = "", studies = "", transport = "", other = "";
        if(getArguments().getString("order").equals("Exepenses")) {
            if (ff != 0) {
                food = "Food";
            }
            if (sf != 0) {
                studies = "Studies";
            }
            if (tf != 0) {
                transport = "Transport";
            }
            if (of != 0) {
                other = "Other";
            }
            entries.add(new PieEntry(ff, food));
            entries.add(new PieEntry(sf, studies));
            entries.add(new PieEntry(tf, transport));
            entries.add(new PieEntry(of, other));
            entries.add(new PieEntry(1 - (ff + sf + tf + of)));


            LegendEntry legendEntry1 = new LegendEntry();
            legendEntry1.label="Food" + " " + Math.ceil(ff*100)+"%";
            legendEntry1.formColor = Color.parseColor("#E99F28");
            legendEntries.add(legendEntry1);
            LegendEntry legendEntry2 = new LegendEntry();
            legendEntry2.label="Studies" + " " + Math.ceil(sf*100)+"%";
            legendEntry2.formColor = Color.parseColor("#316ACC");
            legendEntries.add(legendEntry2);
            LegendEntry legendEntry3 = new LegendEntry();
            legendEntry3.label="Transpotsport" + " " + Math.ceil(tf*100)+"%";
            legendEntry3.formColor = Color.parseColor("#9270F3");
            legendEntries.add(legendEntry3);
            LegendEntry legendEntry4 = new LegendEntry();
            legendEntry4.label="Other" + " " + Math.ceil(of*100)+"%";
            legendEntry4.formColor = Color.parseColor("#80F499");
            legendEntries.add(legendEntry4);

            piechart.getLegend().setCustom(legendEntries);
            legendEntries.clear();
        }
        else {
            if (ff != 0) {
                food = "Gift";
            }
            if (sf != 0) {
                studies = "Salary";
            }
            if (tf != 0) {
                transport = "Interest";
            }
            if (of != 0) {
                other = "Other";
            }
            entries.add(new PieEntry(ff, food));
            entries.add(new PieEntry(sf, studies));
            entries.add(new PieEntry(tf, transport));
            entries.add(new PieEntry(of, other));
            entries.add(new PieEntry(1 - (ff + sf + tf + of)));

            LegendEntry legendEntry1 = new LegendEntry();
            legendEntry1.label="Gift" + " " + Math.ceil(ff*100)+"%";
            legendEntry1.formColor = Color.parseColor("#E99F28");
            legendEntries.add(legendEntry1);
            LegendEntry legendEntry2 = new LegendEntry();
            legendEntry2.label="Salary" + " " + Math.ceil(sf*100)+"%";
            legendEntry2.formColor = Color.parseColor("#316ACC");
            legendEntries.add(legendEntry2);
            LegendEntry legendEntry3 = new LegendEntry();
            legendEntry3.label="Interest" + " " + Math.ceil(tf*100)+"%";
            legendEntry3.formColor = Color.parseColor("#9270F3");
            legendEntries.add(legendEntry3);
            LegendEntry legendEntry4 = new LegendEntry();
            legendEntry4.label="Other" + " " + Math.ceil(of*100)+"%";
            legendEntry4.formColor = Color.parseColor("#80F499");
            legendEntries.add(legendEntry4);
            piechart.getLegend().setCustom(legendEntries);
            legendEntries.clear();


        }






        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#E99F28"));
        colors.add(Color.parseColor("#316ACC"));
        colors.add(Color.parseColor("#9270F3"));
        colors.add(Color.parseColor("#80F499"));
        colors.add(Color.parseColor("#5C324525"));
        PieDataSet piedataset = new PieDataSet(entries, "Expenses");
        piedataset.setColors(colors);

        PieData data = new PieData(piedataset);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(piechart));
        data.setValueTextSize(4f);
        data.setValueTextColors(colors);

        piechart.setData(data);
        piechart.invalidate();

        piechart.animateY(600);
    }

    public String getCalculatedDate(Calendar calendar, String dateFormat, int operation, int days, DatabaseReference myre) {
        Calendar cal = calendar;
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(operation, days);
        SimpleDateFormat s2 = new SimpleDateFormat("ddMMyyyy");
        String dateid = s2.format(new Date(cal.getTimeInMillis()));
        switch (operation) {
            case Calendar.DAY_OF_YEAR:
                getdata_loadpiechart(myre, dateid);
                break;
            case Calendar.MONTH:
                getdata_loadpiechartmonth(myre, dateid);
                break;
            case Calendar.YEAR:
                getdata_loadpiechartyear(myre, dateid);
                break;
        }


        return s.format(new Date(cal.getTimeInMillis()));

    }

    public void getdata_loadpiechart(DatabaseReference dr, String dateid) {
        myref.child(dateid.substring(4, 8)).child(dateid.substring(2, 4)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(dateid)) {
                    ArrayList<Record> rcrds = new ArrayList<>();


                    try {
                        for (DataSnapshot j : snapshot.child(dateid).getChildren()) {
                            try {
                                rcrds.add(snapshot.child(dateid).child(j.getKey()).getValue(Record.class));
                            } catch (Exception e) {
                            }
                            //Toast.makeText(getActivity(), ""+j.getKey(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                    float sum = 0;
                    float fsum = 0, ssum = 0, tsum = 0, osum = 0;
                    float ff = 0, sf = 0, tf = 0, of = 0;
                    for (Record k : rcrds) {

                        sum = sum + k.getAmmount();
                        String categ = k.getCategory();
                        switch (categ) {
                            case "Gift🎁":
                                fsum = fsum + k.getAmmount();
                                break;
                            case "Salary🤑" :
                                ssum = ssum + k.getAmmount();
                                break;
                            case "Interest📈":
                                tsum = tsum + k.getAmmount();
                                break;
                            case "Other🤔" :
                                osum = osum + k.getAmmount();
                                break;

                            case "Food🍔":
                                fsum = fsum + k.getAmmount();
                                break;
                            case "Studies📚":
                                ssum = ssum + k.getAmmount();
                                break;
                            case "Transport🛺":
                                tsum = tsum + k.getAmmount();
                                break;
                            case "Other🚬😳":
                                osum = osum + k.getAmmount();
                                break;
                            case "":
                                break;
                            default:
                                osum = osum + k.getAmmount();
                                break;

                        }


                    }
                    ff = (fsum / sum);
                    sf = ssum / sum;
                    tf = tsum / sum;
                    of = osum / sum;

                    centretext = "$" + sum;
                    setupPieChart(centretext);
                    loadPieChart(ff, sf, tf, of);
                } else {
                    if(getArguments().getString("order").equals("Exepenses")) {
                    centretext = "NO\nExpenses";}else{centretext = "NO\nINCOME";}
                    setupPieChart(centretext);
                    loadPieChart(0, 0, 0, 0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getdata_loadpiechartmonth(DatabaseReference dr, String dateid) {
        myref.child(dateid.substring(4, 8)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(dateid.substring(2, 4))) {


                    try {
                        float sum = 0;
                        float fsum = 0, ssum = 0, tsum = 0, osum = 0;
                        float ff = 0, sf = 0, tf = 0, of = 0;
                        for (DataSnapshot j : snapshot.child(dateid.substring(2, 4)).getChildren()) {
                            //  Toast.makeText(getActivity(), ""+j.getKey(), Toast.LENGTH_SHORT).show();
                            ArrayList<Record> rcrds = new ArrayList<>();
                            for (DataSnapshot k : snapshot.child(dateid.substring(2, 4)).child(j.getKey()).getChildren()) {
                                try {

                                    rcrds.add(snapshot.child(dateid.substring(2, 4)).child(j.getKey()).child(k.getKey()).getValue(Record.class));
                                  //  Toast.makeText(getActivity(), ""+k.getKey(), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                }
                            }

                            //Toast.makeText(getActivity(), "" + rcrds.size(), Toast.LENGTH_SHORT).show();
                            for (Record p : rcrds) {

                                sum = sum + p.getAmmount();

                                String categ = p.getCategory();
                                switch (categ) {
                                    case "Gift🎁":
                                        fsum = fsum + p.getAmmount();
                                        break;
                                    case "Salary🤑" :
                                        ssum = ssum + p.getAmmount();
                                        break;
                                    case "Interest📈":
                                        tsum = tsum + p.getAmmount();
                                        break;
                                    case "Other🤔" :
                                        osum = osum + p.getAmmount();
                                        break;
                                    case "":
                                        break;
                                    case "Food🍔":
                                        fsum = fsum + p.getAmmount();
                                        break;
                                    case "Studies📚":
                                        ssum = ssum + p.getAmmount();
                                        break;
                                    case "Transport🛺":
                                        tsum = tsum + p.getAmmount();
                                        break;
                                    case "Other🚬😳":
                                        osum = osum + p.getAmmount();
                                        break;
                                    default:
                                        osum = osum + p.getAmmount();
                                        break;

                                }

                            }


                        }

                        ff = fsum / sum;
                        sf = ssum / sum;
                        tf = tsum / sum;
                        of = osum / sum;
                        centretext = "$" + sum;
                        setupPieChart(centretext);
                        loadPieChart(ff, sf, tf, of);


                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } else {
                    if(getArguments().getString("order").equals("Exepenses")) {
                        centretext = "NO\nExpenses";}else{centretext = "NO\nINCOME";}
                    setupPieChart(centretext);
                    loadPieChart(0, 0, 0, 0);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getdata_loadpiechartyear(DatabaseReference dr, String dateid) {
        dr.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                dr.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(dateid.substring(4,8)))
                        {
                            ArrayList<Record> rcrds = new ArrayList<>();
                            float ysum=0, fsum=0, ssum=0, tsum =0, osum=0,fy=0,sy=0,ty=0,oy=0;

                            for (DataSnapshot i : snapshot.child(dateid.substring(4,8)).getChildren())
                            {

                                for( DataSnapshot j : i.getChildren())
                                {
                                    for (DataSnapshot k: j.getChildren())
                                    {
                                        try {
                                                rcrds.add(j.child(k.getKey()).getValue(Record.class));
                                            } catch (Exception e) {}
                                    }
                                }

                            }
                            for (Record p : rcrds) {

                                ysum = ysum + p.getAmmount();

                                String categ = p.getCategory();
                                switch (categ) {
                                    case "Gift🎁":
                                        fsum = fsum + p.getAmmount();
                                        break;
                                    case "Salary🤑" :
                                        ssum = ssum + p.getAmmount();
                                        break;
                                    case "Interest📈":
                                        tsum = tsum + p.getAmmount();
                                        break;
                                    case "Other🤔" :
                                        osum = osum + p.getAmmount();
                                        break;
                                    case "Food🍔":
                                        fsum = fsum + p.getAmmount();
                                        break;
                                    case "Studies📚":
                                        ssum = ssum + p.getAmmount();
                                        break;
                                    case "Transport🛺":
                                        tsum = tsum + p.getAmmount();
                                        break;
                                    case "Other🚬😳":
                                        osum = osum + p.getAmmount();
                                        break;
                                    case "":
                                        break;
                                    default:
                                        osum = osum + p.getAmmount();
                                        break;

                                }

                            }

                            fy = fsum / ysum;
                            sy = ssum / ysum;
                            ty = tsum / ysum;
                            oy = osum / ysum;
                            centretext = "$" + ysum;
                            setupPieChart(centretext);
                            loadPieChart(fy, sy, ty, oy);

                        }
                        else {
                            if(getArguments().getString("order").equals("Exepenses")) {
                                centretext = "NO\nExpenses";}else{centretext = "NO\nINCOME";}
                            setupPieChart(centretext);
                            loadPieChart(0, 0, 0, 0);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }



}
