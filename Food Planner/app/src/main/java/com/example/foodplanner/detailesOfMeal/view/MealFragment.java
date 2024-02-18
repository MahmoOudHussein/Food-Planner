package com.example.foodplanner.detailesOfMeal.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.db.LocalDataSourceImpl;
import com.example.foodplanner.detailesOfMeal.pressenter.MealPresenter;
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.RepositoryImpl;
import com.example.foodplanner.network.RemoteDataSourceImpl;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class MealFragment extends Fragment implements IMealView, OnIngredientClickListener {
    private View view;
    private TextView ingredients_tv, howToPrepare_tv, mealName_tv;
    private Button addToPlan_btn, addToFavorite_btn;
    private YouTubePlayerView youTube;
    private ImageView ingredients_img, meal_img;

    private MealPresenter presenter;
    private IngredientsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MealPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_meal, container, false);
        initializeViews();

        adapter = new IngredientsAdapter(requireContext(), new ArrayList<>(), new ArrayList<>(), this);
        recyclerView = view.findViewById(R.id.ingredients_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("mealId")) {
            String mealId = bundle.getString("mealId");
            presenter.getMealById(mealId);
        } else {
            Toast.makeText(getContext(), "Meal ID not provided", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private void initializeViews() {
        ingredients_tv = view.findViewById(R.id.ingredients_tv);
        ingredients_img = view.findViewById(R.id.ingredient_img);
        youTube = view.findViewById(R.id.media_youtube);
        howToPrepare_tv = view.findViewById(R.id.des_how_to_prepare_tv);
        mealName_tv = view.findViewById(R.id.meal_name_tv);
        addToPlan_btn = view.findViewById(R.id.add_to_plan);
        addToFavorite_btn = view.findViewById(R.id.add_to_favorite);
        meal_img = view.findViewById(R.id.meal_img);
    }

    private void addToPlan() {

    }

    @Override
    public void onGetMealSuccess(Meal meal) {
        mealName_tv.setText(meal.getName());
        howToPrepare_tv.setText(meal.getInstructions());

        Glide.with(requireContext())
                .load(meal.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading))
                .into(meal_img);

        youTube.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = GetIdFromYoutubeUrlToShow.getId(meal.getYoutubeUrl());
                youTubePlayer.loadVideo(videoId, 0);
            }
        });

        addToFavorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addMealToFav(meal);
                Toast.makeText(getContext(), meal.getName()+" added successfully", Toast.LENGTH_SHORT).show();

            }
        });

        ArrayList<String> ingredients = new ArrayList<>();
        ArrayList<String> measures = new ArrayList<>();

        try {
            for (int i = 1; i <= 20; i++) {
                String ingredient = (String) meal.getClass().getMethod("getIngredient" + i).invoke(meal);
                if (!ingredient.isEmpty()) ingredients.add(ingredient);

                String measure = (String) meal.getClass().getMethod("getMeasure" + i).invoke(meal);
                if (!measure.isEmpty()) measures.add(measure);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter.setList(ingredients, measures);
    }

    @Override
    public void onGetMealFail(String errorMsg) {
        Toast.makeText(getContext(), "Failed to fetch meal details: " + errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientClicked(String ingredientName) {
    }
}

class GetIdFromYoutubeUrlToShow {
    public static String getId(String link) {
        if (link != null && link.split("\\?v=").length > 1)
            return link.split("\\?v=")[1];
        else return "";
    }
}
