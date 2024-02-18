package com.example.foodplanner.detailesOfMeal.pressenter;


import com.example.foodplanner.detailesOfMeal.view.IMealView;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealsResponse;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallback;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class MealPresenter implements IMealPresenter, NetworkCallback {

    private final IMealView view;
    private final Repository repo;


    public MealPresenter(IMealView view, Repository repo) {
        this.view = view;
        this.repo = repo;

    }

    @Override
    public void getMealById(String mealId) {
        repo.getMealById(mealId, this);
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        return repo.getFavMeals();
    }

    @Override
    public void addMealToFav(Meal meal) {
        repo.addMealToFav(meal, this);
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        repo.removeMealFromFav(meal);
    }

    @Override
    public void addToPlan(Meal meal) {
        repo.addMealToFav(meal, this);
    }



    @Override
    public void onSuccessResult(Object object) {
        if (object instanceof MealsResponse) {
            view.onGetMealSuccess(((MealsResponse) object).getMeals().get(0));
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealFail(errorMsg);
    }
}
