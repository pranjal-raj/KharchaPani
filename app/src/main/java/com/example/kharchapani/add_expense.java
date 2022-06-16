package com.example.kharchapani;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.TimeUnit;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.provider.CalendarContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;


public class add_expense extends Fragment {

    ChipGroup accnchip;
    ChipGroup categchip,datechip;
    EditText amount_editText,categedittext;
    Chip chip;
    int amount;
    AppCompatButton add_button;
    Button dateselect;
    Calendar calender_select;
    String id;
    String dateid=" ",timeid;
    int openingbal;
     int x = 0;
    String category;
    String account;
    int accn_chip_id;
    int categ_chip_id;

    public add_expense() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dataref = firebaseDatabase.getReference("pranjal@gmail");


        //This the Selection of categories using Chips
        accnchip = view.findViewById(R.id.accnchip);
        categchip = view.findViewById(R.id.categchip);
        datechip = view.findViewById(R.id.datechip);
        dateselect = view.findViewById(R.id.dateselect);
        add_button = view.findViewById(R.id.add_buttn);
        amount_editText = view.findViewById(R.id.amount);
        categedittext = view.findViewById(R.id.categedittext);
        calender_select = Calendar.getInstance();



        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                openingbal = snapshot.child("cashbal").getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Code To Fix Chip Selection : Category & Account
        try {

            categchip.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                    Chip chip = view.findViewById(checkedIds.get(0));


                    if (checkedIds.get(0) != x) {
                        try {
                            Chip chip2 = view.findViewById(x);
                            chip2.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.background_color_chip_state_list)));
                            chip2.setTextColor(Color.parseColor("#000000"));
                        } catch (Exception e) {
                        }

                    }

                    x = checkedIds.get(0);

                    chip.setChipBackgroundColor(chip.getChipStrokeColor());
                    chip.setTextColor(Color.parseColor("#ffffff"));
                    category = chip.getText().toString();
                    if(category.equals("Other🚬😳")){
                        categedittext.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        categedittext.setVisibility(View.GONE);
                    }
                }

            });

            accnchip.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                    Chip chip = view.findViewById(checkedIds.get(0));


                    if (checkedIds.get(0) != x) {
                        try {
                            Chip chip2 = view.findViewById(x);
                            chip2.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.background_color_chip_state_list)));
                            chip2.setTextColor(Color.parseColor("#000000"));
                        } catch (Exception e) {
                        }

                    }

                    x = checkedIds.get(0);

                    chip.setChipBackgroundColor(chip.getChipStrokeColor());
                    chip.setTextColor(Color.parseColor("#ffffff"));
                    account = chip.getText().toString();
                }

            });


        } catch (Exception e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        datechip.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if(datechip.getCheckedChipId()!=View.NO_ID) {
                    String di = ((Chip) view.findViewById(datechip.getCheckedChipId())).getText().toString();
                    if(di.equals("Today"))
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        dateid = "" + sdf.format(calender_select.getTime());
                    }
                    if(di.equals("Yesterday"))
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                        calender_select.add(Calendar.DAY_OF_YEAR, -1);
                        dateid = "" + sdf.format(calender_select.getTime());
                    }
                }
            }
        });


        dateselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (datechip.getCheckedChipId() == View.NO_ID) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
                    datePickerDialog.show();
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            calender_select.set(i, i1, i2);
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                            dateid = "" + sdf.format(calender_select.getTime());
                        }
                    });
                }
                else
                {
                    datechip.clearCheck();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity());
                    datePickerDialog.show();
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            calender_select.set(i, i1, i2);
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                            dateid = "" + sdf.format(calender_select.getTime());
                        }
                    });

                }
            }
        });










        //This Updation of Data in the Internet


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(category.equals("Other🚬😳")){
                    if(!(TextUtils.isEmpty(categedittext.getText()))){
                        category = categedittext.getText().toString();
                    }

                }


                if(dateid.equals(" ")||((TextUtils.isEmpty(amount_editText.getText()))||account.equals(null)||(category.equals(null)&&(categedittext.getVisibility()!=View.GONE))))
                {
                    Toast.makeText(getActivity(), "Please fill/select all the fields"+category, Toast.LENGTH_LONG).show();
                }

                else{
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM dd,yyyy");
                    SimpleDateFormat stf = new SimpleDateFormat("HHmmss");

                    if(datechip.getCheckedChipId()==View.NO_ID) {
                        dateid = "" + sdf.format(calender_select.getTime());
                        timeid = "" + stf.format(calender_select.getTime());
                    }
                        try {
                            amount = Integer.parseInt(amount_editText.getText().toString());
                            id = dataref.push().getKey();

                            Thread.sleep(2000);
                            Toast.makeText(getActivity(), ""+openingbal, Toast.LENGTH_SHORT).show();
                            Record record = new Record(sdf2.format(calender_select.getTime()), account, amount, openingbal, (openingbal-amount), category,id);
                            Toast.makeText(getActivity(), ""+record.getOpeningbal(), Toast.LENGTH_SHORT).show();
                            dataref.child("Exepenses").child(dateid.substring(4,8)).child(dateid.substring(2,4)).child(dateid).child(id).setValue(record);

                                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        MainActivity act = ((MainActivity) getActivity());
                                        if(account.equals("Cash💵"))
                                        {
                                            int newcashbal =  snapshot.child("cashbal").getValue(Integer.class)-amount;
                                            dataref.child("cashbal").setValue(newcashbal);
                                            act.cashbal.setText(""+newcashbal);

                                        }
                                        if(account.equals("Bank🏦"))
                                        {
                                            int newcashbal =  snapshot.child("cashbal").getValue(Integer.class)-amount;
                                            dataref.child("bankbal").setValue(newcashbal);
                                            act.bankbal.setText(""+newcashbal);

                                        }



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }

                                });


                            Toast.makeText(getActivity(), "Record Added Successfully", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        dateid="";
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                        getActivity().getSupportFragmentManager().popBackStack();





                }

            }
        });
    }
}