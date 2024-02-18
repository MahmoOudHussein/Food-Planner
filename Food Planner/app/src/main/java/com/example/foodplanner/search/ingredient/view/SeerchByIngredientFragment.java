package com.example.foodplanner.search.ingredient.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.example.foodplanner.R;
import com.example.foodplanner.db.LocalDataSourceImpl;
import com.example.foodplanner.model.Ingredient;
import com.example.foodplanner.model.IngredientResponse;
import com.example.foodplanner.model.RepositoryImpl;
import com.example.foodplanner.network.RemoteDataSourceImpl;
import com.example.foodplanner.search.ingredient.pressenter.IIngredientPresenter;
import com.example.foodplanner.search.ingredient.pressenter.IngredientPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SeerchByIngredientFragment extends Fragment implements IIngredientView, OnIngredientClickListener {
    private View view;
    private IngredientsAdapter adapter;
    private RecyclerView recyclerView;
    private List<Ingredient> ingredients;
    private EditText search_et;
    private CompositeDisposable disposable = new CompositeDisposable();
    private LottieAnimationView errorImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seerch_by_ingredient, container, false);

        recyclerView = view.findViewById(R.id.ingredient_recyclerView);
        errorImage = view.findViewById(R.id.lottie_search);
        search_et = view.findViewById(R.id.ingredient_searchEditText);

        ingredients = new ArrayList<>();
        adapter = new IngredientsAdapter(getContext(), ingredients, this);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        errorImage.setRepeatCount(LottieDrawable.INFINITE);


        IIngredientPresenter presenter = new IngredientPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getIngredients();

        disposable.add(Observable.create((ObservableOnSubscribe<String>) emitter ->
                        search_et.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                emitter.onNext(s.toString());
                            }
                            @Override
                            public void afterTextChanged(Editable s) {}
                        }))
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::filterIngredients));

        return view;
    }


    @Override
    public void onGetIngredientsSuccess(IngredientResponse ingredientResponse) {
        ingredients = ingredientResponse.getIngredients();
        adapter.setList(ingredients);
        errorImage.setVisibility(View.GONE);
    }

    @Override
    public void onGetIngredientsFail(String errorMsg) {
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onIngredientClicked(Ingredient ingredient) {
    }

    private void filterIngredients(String query) {
        if (query == null || query.isEmpty()) {
            adapter.setList(ingredients);
            errorImage.setVisibility(View.GONE);
        } else {
            List<Ingredient> filteredList = new ArrayList<>();
            for (Ingredient ingredient : ingredients) {
                if (ingredient.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(ingredient);
                }
            }
            adapter.setList(filteredList);

            errorImage.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }
}