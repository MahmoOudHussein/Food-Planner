package com.example.foodplanner.search.Names.view;

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
import com.example.foodplanner.model.Meal;
import com.example.foodplanner.model.MealsResponse;
import com.example.foodplanner.model.RepositoryImpl;
import com.example.foodplanner.network.RemoteDataSourceImpl;
import com.example.foodplanner.search.Names.pressenter.INamesPresenter;
import com.example.foodplanner.search.Names.pressenter.NamesPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class SeerchByNamesFragment extends Fragment implements INamesView, OnMealClickListener {
    View view;
    private NamesAdapter adapter;
    private INamesPresenter presenter;
    private LottieAnimationView errorImage;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    EditText search_et;
    private CompositeDisposable disposable = new CompositeDisposable();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seerch_by_names, container, false);

        recyclerView = view.findViewById(R.id.names_recyclerView);
        errorImage = view.findViewById(R.id.lottie_search);
        search_et = view.findViewById(R.id.search_editText);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NamesAdapter(getContext(), new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        errorImage.setRepeatCount(LottieDrawable.INFINITE);


        presenter = new NamesPresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));


        Observable<String> searchTextObservable = Observable.create(emitter ->
                search_et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                }));

        disposable.add(searchTextObservable
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::performSearch));

        return view;
    }

    private void performSearch(String query) {
        presenter.searchMealByName(query);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }

    @Override
    public void onGetMealsSuccess(MealsResponse mealsResponse) {
        List<Meal> meals = mealsResponse.getMeals();
        if (meals == null || meals.isEmpty()) {
            adapter.setList(meals);
            errorImage.setVisibility(View.GONE);
        } else {
            adapter.setList(meals);
            errorImage.setVisibility(meals.isEmpty() ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onGetMealsFail(String errorMsg) {
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMealClicked(Meal meal) {
    }
}