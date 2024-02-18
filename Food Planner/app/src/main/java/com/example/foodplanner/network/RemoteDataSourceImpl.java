package com.example.foodplanner.network;

import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.IngredientResponse;
import com.example.foodplanner.model.MealsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDataSourceImpl implements RemoteDataSource {

    public static final String URL = "https://www.themealdb.com/api/json/v1/1/";

    private static RemoteDataSourceImpl client = null;
    public static ApiService apiService;



    private RemoteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    public static RemoteDataSource getInstance() {
        if (client == null) {
            client = new RemoteDataSourceImpl();
        }
        return client;
    }

    @Override
    public void getMealOfTheDay(NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealOfTheDay();
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getCategories(NetworkCallback networkCallback) {
        Flowable<CategoryResponse> flowable = apiService.getCategories();
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryResponse -> {
                            networkCallback.onSuccessResult(categoryResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void searchMeal(String name, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.searchMealsByName(name);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }



    @Override
    public void getIngredients(NetworkCallback networkCallback) {
        Flowable<IngredientResponse> flowable = apiService.getIngredients();
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredientResponse -> {
                            networkCallback.onSuccessResult(ingredientResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealsByArea(String area, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealsByArea(area);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealsByCategory(String category, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealsByCategory(category);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealsByIngredient(String ingredient, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealsByIngredient(ingredient);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }

    @Override
    public void getMealById(String mealId, NetworkCallback networkCallback) {
        Flowable<MealsResponse> flowable = apiService.getMealById(mealId);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsResponse -> {
                            networkCallback.onSuccessResult(mealsResponse);
                        },
                        error -> {
                            networkCallback.onFailureResult(error.getMessage());
                            error.printStackTrace();
                        }
                );
    }
}
