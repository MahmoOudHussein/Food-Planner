package com.example.foodplanner.search.categories.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.foodplanner.model.Category;
import com.example.foodplanner.model.CategoryResponse;
import com.example.foodplanner.model.RepositoryImpl;
import com.example.foodplanner.network.RemoteDataSourceImpl;
import com.example.foodplanner.search.categories.pressenter.CategoryPressenter;
import com.example.foodplanner.search.categories.pressenter.ICategoryPressenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchByCategoriesFragment extends Fragment implements ICategoryView, OnCategoryClickListener {
    private View view;
    private CategoriesAdapter adapter;
    private RecyclerView recyclerView;
    private List<Category> categories;
    private EditText search_et;
    private CompositeDisposable disposable = new CompositeDisposable();
    private LottieAnimationView errorImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_seerch_by_categories, container, false);

        recyclerView = view.findViewById(R.id.search_categories_recyclerView);
        search_et = view.findViewById(R.id.categories_searchEditText);
        errorImage = view.findViewById(R.id.lottie_search);


        categories = new ArrayList<>();
        adapter = new CategoriesAdapter(getContext(), categories, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        errorImage.setRepeatCount(LottieDrawable.INFINITE);


        ICategoryPressenter presenter = new CategoryPressenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
        presenter.getCategories();

        Observable.create((ObservableOnSubscribe<String>) emitter -> search_et.addTextChangedListener(new TextWatcher() {
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
                .subscribe(this::filterCategory);

        return view;
    }

    @Override
    public void onGetCategoriesSuccess(CategoryResponse categoryResponse) {
        categories.clear();
        categories.addAll(categoryResponse.getCategories());
        adapter.notifyDataSetChanged();
        errorImage.setVisibility(View.GONE);

    }

    @Override
    public void onGetCategoriesFail(String errorMsg) {
        errorImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCategoryClicked(Category category) {
        // Handle category click event here
    }

    private void filterCategory(String query) {
        List<Category> filteredList;
        if (query.isEmpty()) {
            filteredList = categories;
            errorImage.setVisibility(View.GONE);

        } else {
            filteredList = categories.stream()
                    .filter(category -> category.getName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        adapter.setList(filteredList);
        errorImage.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.dispose();
    }
}
