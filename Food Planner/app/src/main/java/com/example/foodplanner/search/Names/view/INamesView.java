package com.example.foodplanner.search.Names.view;


import com.example.foodplanner.model.MealsResponse;

public interface INamesView {

    void onGetMealsSuccess(MealsResponse mealsResponse);

    void onGetMealsFail(String errorMsg);
}
