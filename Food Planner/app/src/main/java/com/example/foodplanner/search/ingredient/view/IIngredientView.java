package com.example.foodplanner.search.ingredient.view;


import com.example.foodplanner.model.IngredientResponse;

public interface IIngredientView {

    void onGetIngredientsSuccess(IngredientResponse ingredientResponse);

    void onGetIngredientsFail(String errorMsg);
}
