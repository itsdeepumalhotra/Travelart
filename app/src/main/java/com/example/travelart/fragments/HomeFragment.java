package com.example.travelart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.travelart.HomeActivity;
import com.example.travelart.R;
import com.example.travelart.utils.Resources;
import com.sdsmdg.tastytoast.TastyToast;


public class HomeFragment extends Fragment {

AutoCompleteTextView inputCityName;
Button startBtn;

String[] cities = new Resources().getCities();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        inputCityName = view.findViewById(R.id.inputCity);
        startBtn = view.findViewById(R.id.startBtn);


        inputCityName.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.select_dialog_item,cities));
        inputCityName.setThreshold(1);

        startBtn.setOnClickListener(view1 -> {
            String cityName = String.valueOf(inputCityName.getText());

            if (cityName.isEmpty()){
                TastyToast.makeText(getActivity().getApplicationContext(),"Please Enter a city",TastyToast.LENGTH_LONG,TastyToast.INFO);
            }
            else {
                Bundle bundle = new Bundle();
                bundle.putString("CITY_NAME",cityName);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,ChooseVenueFragment.class,bundle).addToBackStack(null).commit();
//                HomeActivity homeActivity = new HomeActivity();
//                homeActivity.replaceFragment(new ChooseVenueFragment());

                ChooseVenueFragment fragobj = new ChooseVenueFragment();
                fragobj.setArguments(bundle);
            }

        });


        return view;
    }
}