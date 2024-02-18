package com.example.foodplanner.detailesOfMeal.view;


import com.example.foodplanner.model.Meal;

public interface IMealView {
    void onGetMealSuccess(Meal meal);
    void onGetMealFail(String errorMsg);
}
