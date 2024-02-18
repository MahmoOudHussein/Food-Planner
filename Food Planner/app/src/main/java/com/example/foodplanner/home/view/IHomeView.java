package com.example.foodplanner.home.view;


import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.MealsResponse;

public interface IHomeView {

    void onGetCategoriesSuccess(CategoryResponse categoryResponse);

    void onGetMealOfTheDaySuccess(MealsResponse mealsResponse);

    void onNetworkFail(String errorMsg);
}
