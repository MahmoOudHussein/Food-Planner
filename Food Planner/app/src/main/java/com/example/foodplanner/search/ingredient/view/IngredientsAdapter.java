package com.example.foodplanner.search.ingredient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.foodplanner.R;
import com.example.foodplanner.model.Ingredient;


import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> items;
    private final Context context;
    private final OnIngredientClickListener listener;

    public IngredientsAdapter(Context context, List<Ingredient> items, OnIngredientClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Ingredient> ingredients) {
        this.items = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sesrch_by_ingrediant_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUrl = "https://www.themealdb.com/images/ingredients/" + items.get(position).getName() + "-Small.png";
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading))
                .into(holder.search_by_ingredient_image_view);

        holder.name.setText(items.get(position).getName());

        holder.row.setOnClickListener(v -> listener.onIngredientClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView search_by_ingredient_image_view;
        TextView name;
        CardView row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            search_by_ingredient_image_view = itemView.findViewById(R.id.search_by_ingredient_image_view);
            name = itemView.findViewById(R.id.search_by_ingredient_title);
            row = itemView.findViewById(R.id.search_by_ingredient_card_view);
        }
    }
}