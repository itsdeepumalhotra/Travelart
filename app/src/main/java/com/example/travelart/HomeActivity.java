package com.example.travelart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.DrawableWrapper;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.travelart.fragments.ContactUsFragment;
import com.example.travelart.fragments.HomeFragment;
import com.example.travelart.fragments.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import com.sdsmdg.tastytoast.TastyToast;
//import com.sdsmdg.tastytoast.TastyToast;

public class HomeActivity extends AppCompatActivity {

    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    View fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentContainer = findViewById(R.id.fragment_container);
        navigationView = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawerLayout);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Fragment fragment = new HomeFragment();
        replaceFragment(fragment);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.profile :
                        replaceFragment(new ProfileFragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.contactus :
                        replaceFragment(new ContactUsFragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.logout :
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setMessage("LEAVING SO SOON..??");
                        builder.setTitle("Just Checking");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logoutProcess();
                            }
                        });
                        builder.setNegativeButton("Nooo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                TastyToast.makeText(getApplicationContext(), "Enjoy planning your trips.!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog =builder.create();
                        alertDialog.show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });
//        
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .commit();
    }

    private void gotoMapActivity() {
        startActivity(new Intent(this, MapviewsActivity.class));
    }


    private void logoutProcess() {
        SharedPreferences sharedPreferences = getSharedPreferences("credentials",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
        TastyToast.makeText(getApplicationContext(),"Logged Out..!",TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }


}