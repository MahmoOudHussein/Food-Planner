package com.example.foodplanner.search.categories.view;


import com.example.foodplanner.model.CategoryResponse;

public interface ICategoryView {

    void onGetCategoriesSuccess(CategoryResponse categoryResponse);

    void onGetCategoriesFail(String errorMsg);
}
