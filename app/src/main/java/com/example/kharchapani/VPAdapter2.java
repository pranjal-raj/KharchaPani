package com.example.kharchapani;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class VPAdapter2 extends FragmentStateAdapter {


    public VPAdapter2(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new onboardingfeaturesfragment();
        Bundle args = new Bundle();
//        if(position==0){
//
//            args.putInt("img", R.drawable.onbd0 );
//
//            args.putString("text", "Lörem ipsum ärade mas ur i mikron och ogusk. Dönyse ultratt växtbaserat kött bioling sopspanare. Sakäktig rånde. Käkårat syr jäkåra inklusive pen dygon. Vilödade pyrade trerat samt hyposa inte prebelt.");
//            fragment.setArguments(args);
//        }
//        else
//        {


            args.putInt("img",1);
            args.putString("text", "Lörem ipsum ärade mas ur i mikron och ogusk. Dönyse ultratt växtbaserat kött bioling sopspanare. Sakäktig rånde. Käkårat syr jäkåra inklusive pen dygon. Vilödade pyrade trerat samt hyposa inte prebelt.");
            fragment.setArguments(args);
       // }


        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
