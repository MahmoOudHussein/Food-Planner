    package com.example.foodplanner.home.view;



    import android.os.Bundle;

    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.bumptech.glide.request.RequestOptions;
    import com.example.foodplanner.R;
    import com.example.foodplanner.db.LocalDataSourceImpl;
    import com.example.foodplanner.detailesOfMeal.view.MealFragment;
    import com.example.foodplanner.home.pressenter.HomePresenter;
    import com.example.foodplanner.model.Category;
    import com.example.foodplanner.model.CategoryResponse;
    import com.example.foodplanner.model.Meal;
    import com.example.foodplanner.model.MealsResponse;
    import com.example.foodplanner.model.RepositoryImpl;
    import com.example.foodplanner.network.RemoteDataSourceImpl;

    import java.util.ArrayList;

    public class HomeFragment extends Fragment implements OnCategoryClickListener, IHomeView {
        private View view;
        private ImageView imageView;
        private TextView title;
        private RecyclerView recyclerView;
        private LinearLayoutManager layoutManager;
        private ArrayList<Category> categories = new ArrayList<>();
        private HomeAdapter mAdapter;
        private Meal mealOfTheDay;
        private HomePresenter presenter;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            initializeViews();
            initializePresenter();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mealOfTheDay != null) {
                        String mealId = mealOfTheDay.getId();
                        Bundle bundle = new Bundle();
                        bundle.putString("mealId", mealId);
                        MealFragment fragment = new MealFragment();
                        fragment.setArguments(bundle);

                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, fragment)
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });

            return view;
        }

        private void initializeViews() {
            title = view.findViewById(R.id.random_meal_tv);
            imageView = view.findViewById(R.id.random_meal_img);
            recyclerView = view.findViewById(R.id.recyclerview_category);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new HomeAdapter(getContext(), categories, this);
            recyclerView.setAdapter(mAdapter);
        }

        private void initializePresenter() {
            presenter = new HomePresenter(this, RepositoryImpl.getInstance(RemoteDataSourceImpl.getInstance(), LocalDataSourceImpl.getInstance(requireContext())));
            presenter.getCategories();
            presenter.getMealOfTheDay();
        }

        @Override
        public void onCategoryClicked(Category category) {
            Toast.makeText(getContext(), category.getName() + " is Clicked", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onGetCategoriesSuccess(CategoryResponse categoryResponse) {
            categories.clear();
            categories.addAll(categoryResponse.getCategories());
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onGetMealOfTheDaySuccess(MealsResponse mealsResponse) {
            if (!mealsResponse.getMeals().isEmpty()) {
                mealOfTheDay = mealsResponse.getMeals().get(0);
                setMealOfTheDay();
            }
        }

        private void setMealOfTheDay() {
            try {
                Glide.with(requireContext())
                        .load(mealOfTheDay.getImageUrl())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.loading)
                                .error(R.drawable.loading))
                        .into(imageView);
                title.setText(mealOfTheDay.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onNetworkFail(String errorMsg) {
            Toast.makeText(getContext(), "Network error: " + errorMsg, Toast.LENGTH_SHORT).show();
        }
    }