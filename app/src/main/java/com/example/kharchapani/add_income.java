package com.example.kharchapani;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;



public class add_income extends Fragment {

    ChipGroup accnchipi;
    ChipGroup categchipi;
    int x = 0;
    int xx = 1;

    public add_income() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_income, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        accnchipi = view.findViewById(R.id.accnchip);
        categchipi = view.findViewById(R.id.categchip);

        try {

            categchipi.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                    Chip chip = view.findViewById(checkedIds.get(0));


                    if(checkedIds.get(0)!=x)
                    {
                        try{Chip chip2 = view.findViewById(x);
                            chip2.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.background_color_chip_state_list)));
                            chip2.setTextColor(Color.parseColor("#000000"));
                        }catch (Exception e){}

                    }

                    x = checkedIds.get(0);

                    chip.setChipBackgroundColor(chip.getChipStrokeColor());
                    chip.setTextColor(Color.parseColor("#ffffff"));
                }

            });

            accnchipi.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
                @Override
                public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

                    Chip chip = view.findViewById(checkedIds.get(0));


                    if(checkedIds.get(0)!=x)
                    {
                        try{
                            Chip chip2 = view.findViewById(x);
                            chip2.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.background_color_chip_state_list)));
                            chip2.setTextColor(Color.parseColor("#000000"));
                        }
                        catch (Exception e){}

                    }

                    x = checkedIds.get(0);

                    chip.setChipBackgroundColor(chip.getChipStrokeColor());
                    chip.setTextColor(Color.parseColor("#ffffff"));
                }

            });





        }catch(Exception e)
        {
            Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}