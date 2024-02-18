package com.example.foodplanner.search.categories.pressenter;


import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.Repository;
import com.example.foodplanner.network.NetworkCallback;
import com.example.foodplanner.search.categories.view.ICategoryView;

public class CategoryPressenter implements ICategoryPressenter, NetworkCallback {

    private final ICategoryView listener;
    private final Repository repo;

    public CategoryPressenter(ICategoryView listener, Repository repo) {
        this.listener = listener;
        this.repo = repo;
    }

    @Override
    public void getCategories() {
        repo.getCategories(this);
    }

    @Override
    public void onSuccessResult(Object object) {
        listener.onGetCategoriesSuccess((CategoryResponse) object);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        listener.onGetCategoriesFail(errorMsg);
    }
}
