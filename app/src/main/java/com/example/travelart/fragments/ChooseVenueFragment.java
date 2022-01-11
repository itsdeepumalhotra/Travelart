package com.example.travelart.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelart.R;
import com.example.travelart.adapters.VenueAdapter;

import java.util.ArrayList;

public class ChooseVenueFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_venue, container, false);

        String cityName = getArguments().getString("CITY_NAME");
        Toast.makeText(getContext(), cityName, Toast.LENGTH_SHORT).show();

        RecyclerView rvVenueCategory = view.findViewById(R.id.rvVenueCategory);
        VenueAdapter adapter = new VenueAdapter(getCategoryData());

        rvVenueCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVenueCategory.setAdapter(adapter);


        //////////////// on Clicking Type of Venue (eg. Market)////////////////////
        adapter.setOnItemClickListener(new VenueAdapter.ClickListener(){
            @Override
            public void onItemClick(int position, View view) {
                String text = ((TextView) view.findViewById(R.id.venueCategory)).getText().toString();
                Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("VENUE_TYPE",text);
                bundle.putString("CITY_NAME",cityName);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container,DisplayVenuesFragment.class,bundle).addToBackStack(null).commit();

            }
        });


        return view;
    }

    ArrayList<Pair<String, Integer>> getCategoryData(){
        ArrayList<Pair<String, Integer>> list = new ArrayList<>();

        list.add(new Pair<>("Market", R.drawable.market));
        list.add(new Pair<>("Hospital", R.drawable.doctor));
        list.add(new Pair<>("Historic Site", R.drawable.historic_site));
        list.add(new Pair<>("Cinema", R.drawable.movie_theater));
        list.add(new Pair<>("Museum", R.drawable.museum));
        list.add(new Pair<>("Stadium", R.drawable.stadium));
        list.add(new Pair<>("Water Park", R.drawable.water_park));
        list.add(new Pair<>("Zoo", R.drawable.zoo));
        list.add(new Pair<>("College", R.drawable.college));
        list.add(new Pair<>("Food", R.drawable.food));
        list.add(new Pair<>("Cafe", R.drawable.cafe));
        list.add(new Pair<>("Night Life Spot", R.drawable.night_life_spot));
        list.add(new Pair<>("Public Park", R.drawable.park));
        list.add(new Pair<>("Rivers", R.drawable.river));
        list.add(new Pair<>("Police", R.drawable.police));
        list.add(new Pair<>("Government Building", R.drawable.government_building));
        list.add(new Pair<>("Temple", R.drawable.spiritual_site));
        list.add(new Pair<>("Shopping Mall", R.drawable.shopping_mall));
        list.add(new Pair<>("Transport", R.drawable.travel_and_transport));

        return list;
    }

}

