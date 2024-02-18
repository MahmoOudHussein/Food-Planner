package com.example.foodplanner.search.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.R;
import com.example.foodplanner.detailesOfMeal.view.MealFragment;
import com.example.foodplanner.search.Area.view.SeerchByAreaFragment;
import com.example.foodplanner.search.Names.view.SeerchByNamesFragment;
import com.example.foodplanner.search.categories.view.SearchByCategoriesFragment;
import com.example.foodplanner.search.categories.view.SearchByCategoriesFragment;
import com.example.foodplanner.search.ingredient.view.SeerchByIngredientFragment;

public class SearchFragment extends Fragment {
    View view;
    Button searchByName_btn,searchByArea_btn,searchByCategory_btn,searchByIngredient_btn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_search, container, false);

        searchByArea_btn=view.findViewById(R.id.search_by_area);
        searchByName_btn=view.findViewById(R.id.search_by_name);
        searchByIngredient_btn=view.findViewById(R.id.search_by_ingredient);
        searchByCategory_btn=view.findViewById(R.id.search_by_category);
        searchByArea_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchType = "area";

                Bundle bundle = new Bundle();
                bundle.putString("SearchType", searchType);

                SeerchByAreaFragment fragment = new SeerchByAreaFragment();
                fragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        searchByName_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String searchType = "names";
                bundle.putString("SearchType", searchType);

                SeerchByNamesFragment fragment = new SeerchByNamesFragment();
                fragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();


            }
        });
        searchByIngredient_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String searchType = "ingredient";
                bundle.putString("SearchType", searchType);

                SeerchByIngredientFragment fragment = new SeerchByIngredientFragment();
                fragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();


            }
        });
        searchByCategory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String searchType = "category";
                bundle.putString("SearchType", searchType);

                SearchByCategoriesFragment fragment = new SearchByCategoriesFragment();
                fragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return view;
    }
}