package com.example.kharchapani;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class OnBoardingFeatures extends Fragment {

    int pos;

    public OnBoardingFeatures() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_features, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager2 viewPager2 = view.findViewById(R.id.viewpager2);
        VPAdapter2 vp2 = new VPAdapter2(getParentFragmentManager(),getLifecycle());
        viewPager2.setAdapter(vp2);
        viewPager2.setCurrentItem(0,false);
        pos = 1;
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {

                pos = (pos==0)?1:0;
                Toast.makeText(getActivity(), pos+" "+position , Toast.LENGTH_SHORT).show();
                viewPager2.setCurrentItem(position,true);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

            }

        });



    }
}