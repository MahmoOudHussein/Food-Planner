package com.example.foodplanner.search.Names.pressenter;


import com.example.foodplanner.model.MealsResponse;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.search.Names.view.INamesView;

public class NamesPresenter implements INamesPresenter, NetworkCallback {

    private final INamesView view;
    private final Repository repo;

    public NamesPresenter(INamesView view, Repository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void searchMealByName(String mealName) {
        repo.searchMeal(mealName, this);
    }

    @Override
    public void onSuccessResult(Object object) {
        view.onGetMealsSuccess((MealsResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onGetMealsFail(errorMsg);
    }
}
