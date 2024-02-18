package com.example.foodplanner.favorite.view;


import com.example.foodplanner.model.Meal;

public interface OnMealClickListener {

    void onMealClicked(Meal meal);
    void onRemoveClicked(Meal meal);
}
