package com.example.foodplanner.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;


import com.example.foodplanner.model.Meal;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LocalDataSourceImpl implements LocalDataSource {

    public static final String TAG = "Local";
    private final MealDao dao;
    private static LocalDataSourceImpl instance = null;
    private Flowable<List<Meal>> favMeals;


    private LocalDataSourceImpl(@NonNull Context context) {
        AppDatabase db = AppDatabase.getInstance(context.getApplicationContext());
        dao = db.getDao();
        favMeals = dao.observeFavMeal();
    }

    public static LocalDataSource getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new LocalDataSourceImpl(context);
        }
        return instance;
    }

    @Override
    public Flowable<List<Meal>> getFavMeals() {
        favMeals = dao.observeFavMeal();
        return favMeals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void removeMealFromFav(Meal meal) {
        dao.removeMealFromFav(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "removeMealFromFavorite"),
                        error -> {
                            Log.i(TAG, "removeMealFromFavorite error: " + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }
    @Override
    public void addMealToFav(Meal  meal) {
        dao.addMealToFav(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.i(TAG, "addMealToFavorite: " + meal.getName()),
                        error -> {
                            Log.i(TAG, "addMealToFavorite error: " + error.getMessage());
                            error.printStackTrace();
                        }
                );
    }


}
