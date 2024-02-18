package com.example.foodplanner.network;


import com.example.foodplanner.model.Meal;

public interface RemoteDataSource {
    void getMealOfTheDay(NetworkCallback networkCallback);
    void getCategories(NetworkCallback networkCallback);
    void getMealById(String mealId, NetworkCallback networkCallback);
    void searchMeal(String name, NetworkCallback networkCallback);
    void getIngredients(NetworkCallback networkCallback);
    void getMealsByArea(String area, NetworkCallback networkCallback);
    void getMealsByCategory(String category, NetworkCallback networkCallback);
    void getMealsByIngredient(String ingredient, NetworkCallback networkCallback);


}
