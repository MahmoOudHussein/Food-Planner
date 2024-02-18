package com.example.foodplanner.model;



import com.example.foodplanner.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface Repository {

    void getCategories(NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getMealOfTheDay(NetworkCallback networkCallback);

    Flowable<List<Meal>> getFavMeals();
    void addMealToFav(Meal meal, NetworkCallback networkCallback);
    void removeMealFromFav(Meal meal);
    void removeMealFromPlan(Meal meal, NetworkCallback networkCallback);


    void getIngredients(NetworkCallback networkCallback);
    void getMealsByArea(String area, NetworkCallback networkCallback);
    void getMealsByCategory(String category, NetworkCallback networkCallback);
    void getMealsByIngredient(String ingredient, NetworkCallback networkCallback);
    void getMealById(String mealId, NetworkCallback networkCallback);


}
