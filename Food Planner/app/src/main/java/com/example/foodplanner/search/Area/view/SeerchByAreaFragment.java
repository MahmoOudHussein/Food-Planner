package com.example.foodplanner.search.Area.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodplanner.R;

public class SeerchByAreaFragment extends Fragment {
    View view;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_seerch_by_area, container, false);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("key")) {
            String key = bundle.getString("area");

        }
        return view;
    }
}