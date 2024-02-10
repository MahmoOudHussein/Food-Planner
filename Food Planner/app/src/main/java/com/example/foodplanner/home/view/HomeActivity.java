package com.example.foodplanner.home.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.foodplanner.R;
import com.example.foodplanner.calender.view.CalenderFragment;
import com.example.foodplanner.favorite.view.FavoriteFragment;
import com.example.foodplanner.search.view.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        frameLayout=findViewById(R.id.frame_layout);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.home_item) {
                    replaceFragment(new HomeFragment(),false);
                } else if (itemId == R.id.favorite_item) {
                    replaceFragment(new FavoriteFragment(),false);
                } else if (itemId == R.id.search_item) {
                    replaceFragment(new SearchFragment(),false);
                }
                else if (itemId == R.id.clender_item) {
                    replaceFragment(new CalenderFragment(),false);
                }
                    return true;

            }
        });
        replaceFragment(new HomeFragment(),true);

    }
    private void replaceFragment(Fragment fragment,boolean isAppInitialized) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (isAppInitialized) {
            fragmentTransaction.add(R.id.frame_layout, fragment);
        } else {
            fragmentTransaction.replace(R.id.frame_layout, fragment);
        }
        fragmentTransaction.commit();
    }
}