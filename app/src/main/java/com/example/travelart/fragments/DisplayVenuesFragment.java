package com.example.travelart.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.travelart.MapsActivity;
import com.example.travelart.MapviewsActivity;
import com.example.travelart.R;
import com.example.travelart.utils.ParseData;
import com.example.travelart.utils.Venue;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DisplayVenuesFragment extends Fragment {

    ArrayList<Venue> venueArrayList;
    private RecyclerView venues;
    private ImageView mapIcon;
    private String city, venueType;
    CustomAdapterForEachPlace adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_venues, container, false);

        TextView tvVenueType = view.findViewById(R.id.venueType);
        venues = view.findViewById(R.id.rvVenueList);
        mapIcon = view.findViewById(R.id.mapIcon);

        Bundle bundle = getArguments();
        if (bundle !=null){
            venueType = bundle.getString("VENUE_TYPE");
            city = bundle.getString("CITY_NAME");
            tvVenueType.setText(city.toUpperCase() + " " + venueType.toUpperCase());
        }

        setVenues();



        mapIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            Intent intent = new Intent(getContext(), MapsActivity.class);
                            intent.putExtra("VENUE_LIST", venueArrayList);

                            startActivity(intent);
                            TastyToast.makeText(getContext(), "Goto MAP", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                        }

        });


        return view;
    }

    String getUrl() {

        String domain = "https://api.foursquare.com/v3/places/search?&near=";
        String url = domain + city
                + "&query=" + venueType
                + "&limit=50";
        Log.d("url", url);

        return url;
    }

    private void setVenues(){

        //////////////// Progress Bar ////////////////
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading Venues...");
        progressDialog.show();
        //////////////// Progress Bar started/////////////////

        String url = getUrl();

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        /* stop Progress Bar */
                        Log.d("myurl", response.toString());
                        progressDialog.hide();

                        ParseData parseData = new ParseData(response);
                        venueArrayList = parseData.getVenueList();

                        adapter = new CustomAdapterForEachPlace(venueArrayList);
                        venues.setLayoutManager(new LinearLayoutManager(getContext()));
                        venues.setAdapter(adapter);
//                        adapter.setOnItemClickListener(
//                                (position, view) -> {
//                                    Intent intent = new Intent(getContext(), MapsActivity.class);
//                                    intent.putExtra("VENUE_LIST", venueArrayList);
//
//                                    startActivity(intent);
//                                    TastyToast.makeText(getContext(), "Goto MAP", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
//
//                                }
//                        );
//
//

                    }
                }, error -> {
                    /* stop Progress Bar */
                    progressDialog.hide();
                    ////////// Show Alert Dialog //////////
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Alert")
                            .setTitle("Unexpected error occurred !!!")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ///////// close this activity //////////
                                }
                            })
                            .show();
                })

        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", getResources().getString(R.string.AUTH_ID));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

}

class CustomAdapterForEachPlace extends RecyclerView.Adapter<CustomAdapterForEachPlace.ViewHolder>  {

    private ArrayList<Venue> venueList;
    private static CustomAdapterForEachPlace.ClickListener clickListener;




    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textViewName, textViewAddress;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textViewName = (TextView) view.findViewById(R.id.VenueTypeName);
            textViewAddress = (TextView) view.findViewById(R.id.address);

            view.setOnClickListener(this);

        }

        public TextView getTextViewName() {
            return textViewName;
        }
        public TextView getTextViewAddress(){
            return textViewAddress;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);

        }


    }

    public interface ClickListener{
        void onItemClick(int position, View view);
    }

    public void setOnItemClickListener(CustomAdapterForEachPlace.ClickListener clickListener){
        CustomAdapterForEachPlace.clickListener = clickListener;
    }


    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public CustomAdapterForEachPlace(ArrayList<Venue> dataSet) {
        venueList = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.venue_type_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextViewName().setText(venueList.get(position).getName());
        viewHolder.getTextViewAddress().setText(venueList.get(position).getAddress());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return venueList.size();
    }
}
