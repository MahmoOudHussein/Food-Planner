package com.example.foodplanner.favorite.pressenter;



import com.example.foodplanner.model.Meal;

import java.util.List;


import io.reactivex.rxjava3.core.Flowable;

public interface IFavoritePresenter {

    Flowable<List<Meal>> observeFavMeals();
    void onRemoveClicked(Meal meal);



}
