package com.example.foodplanner.favorite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.db.LocalDataSourceImpl;
import com.example.foodplanner.favorite.pressenter.FavoritePresenter;
import com.example.foodplanner.favorite.pressenter.IFavoritePresenter;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryImpl;
import com.example.foodplanner.network.RemoteDataSourceImpl;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements OnMealClickListener {
    private View view;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    LottieAnimationView animationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);
        animationView = view.findViewById(R.id.lottie_favorite);


        recyclerView = view.findViewById(R.id.favorite_recyclerview);

        IFavoritePresenter presenter = new FavoritePresenter(
                RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(),
                        LocalDataSourceImpl.getInstance(requireContext()))
        );

        presenter.observeFavMeals().subscribe(
                meals -> {
                    ArrayList<Meal> arrayList = new ArrayList<>();
                    for (Meal meal : meals) {
                        if (meal.getFavourite() == null || meal.getFavourite()) {
                            arrayList.add(meal);
                        }
                    }
                    if (meals.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                        animationView.setVisibility(View.VISIBLE);
                        animationView.playAnimation();
                        moveAnimationView();

                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        animationView.setVisibility(View.GONE);
                        favoriteAdapter.setList(meals);
                    }
                },
                Throwable::printStackTrace
        );

        favoriteAdapter = new FavoriteAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(favoriteAdapter);

        return view;
    }

    @Override
    public void onMealClicked(Meal meal) {
    }
    private void moveAnimationView() {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, -50.0f, 50.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        animationView.startAnimation(animation);
    }

    @Override
    public void onRemoveClicked(Meal meal) {
        IFavoritePresenter presenter = new FavoritePresenter(
                RepositoryImpl.getInstance(
                        RemoteDataSourceImpl.getInstance(),
                        LocalDataSourceImpl.getInstance(requireContext())
                )
        );
        presenter.onRemoveClicked(meal);
        Toast.makeText(getContext(), meal.getName()+" removed ", Toast.LENGTH_SHORT).show();

    }

}
