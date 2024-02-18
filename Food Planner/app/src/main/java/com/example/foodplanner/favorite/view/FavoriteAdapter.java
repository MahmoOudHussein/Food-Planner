package com.example.foodplanner.favorite.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Meal;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<Meal> items;
    private final Context context;
    private final OnMealClickListener listener;

    public FavoriteAdapter(Context context, List<Meal> items, OnMealClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Meal> products) {
        this.items = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (context == null || items == null) {
            return;
        }
        Meal meal = items.get(position);
        if (meal == null) {
            return;
        }

        Glide.with(context)
                .load(meal.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading))
                .into(holder.favorite_img);

        holder.meal_favorite_name.setText(meal.getName());

        holder.favorite_card_view.setOnClickListener(v -> listener.onMealClicked(meal));
        holder.remove_btn.setOnClickListener(v -> {
            listener.onRemoveClicked(meal);
        });
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView favorite_img;
        TextView meal_favorite_name;
        CardView favorite_card_view;
        Button remove_btn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favorite_img = itemView.findViewById(R.id.favorite_image_view);
            meal_favorite_name = itemView.findViewById(R.id.favorite_meal_name);
            favorite_card_view = itemView.findViewById(R.id.favorite_card_view);
            remove_btn = itemView.findViewById(R.id.remove_btn);
        }
    }
}
