package com.example.foodplanner.search.ingredient.pressenter;


import com.example.foodplanner.model.IngredientResponse;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.search.ingredient.view.IIngredientView;

public class IngredientPresenter implements IIngredientPresenter, NetworkCallback {

    private final IIngredientView listener;
    private final Repository repo;

    public IngredientPresenter(IIngredientView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getIngredients() {
        repo.getIngredients(this);
    }

    @Override
    public void onSuccessResult(Object object) {
        listener.onGetIngredientsSuccess((IngredientResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        listener.onGetIngredientsFail(errorMsg);
    }
}
