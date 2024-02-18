package com.example.foodplanner.home.pressenter;


import com.example.foodplanner.home.view.IHomeView;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.MealsResponse;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallback;

public class  HomePresenter implements IHomePresenter, NetworkCallback {

    private final IHomeView view;
    private final Repository repo;


    public HomePresenter(IHomeView view, Repository repo) {
        this.view = view;
        this.repo = repo;

    }

    @Override
    public void getMeals() {
        repo.getCategories(this);
    }

    @Override
    public void getMealOfTheDay() {
        repo.getMealOfTheDay(this);
    }

    public void getCategories() {
        repo.getCategories(this);
    }



    @Override
    public void onSuccessResult(Object object) {
        if (object instanceof CategoryResponse) {
            view.onGetCategoriesSuccess((CategoryResponse) object);
        } else if (object instanceof MealsResponse) {
            view.onGetMealOfTheDaySuccess((MealsResponse) object);
        }
    }

    @Override
    public void onFailureResult(String errorMsg) {
        view.onNetworkFail(errorMsg);
    }
}
