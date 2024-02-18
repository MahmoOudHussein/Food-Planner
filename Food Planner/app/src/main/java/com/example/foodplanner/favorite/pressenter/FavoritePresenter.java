package com.example.foodplanner.favorite.pressenter;



import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.Repository;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public class FavoritePresenter implements IFavoritePresenter {

    private final Repository repo;


    public FavoritePresenter(Repository repo) {
        this.repo = repo;

    }

    @Override
    public Flowable<List<Meal>> observeFavMeals() {
        return repo.getFavMeals();
    }

    @Override
    public void onRemoveClicked(Meal meal) {
        repo.removeMealFromFav(meal);

    }


}
