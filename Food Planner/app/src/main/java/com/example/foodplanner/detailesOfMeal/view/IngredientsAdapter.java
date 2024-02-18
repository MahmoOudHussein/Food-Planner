package com.example.foodplanner.detailesOfMeal.view;

import android.annotation.SuppressLint;
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


import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<String> ingredients, measures;
    private final Context context;
    private final OnIngredientClickListener listener;

    public IngredientsAdapter(@NonNull Context context, List<String> ingredients, List<String> measures, OnIngredientClickListener listener) {
        super();
        this.context = context;
        this.ingredients = ingredients;
        this.measures = measures;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<String> ingredients, List<String> measures) {
        this.ingredients = ingredients;
        this.measures = measures;
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.ingredientName_tv.setText(ingredients.get(position) + "\n" + measures.get(position));
        } catch (IndexOutOfBoundsException e) { e.printStackTrace(); }
        String url = "https://www.themealdb.com/images/ingredients/" + ingredients.get(position).replace(" ", "%20") + "-Small.png";
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading))
                .into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName_tv;
        ImageView ingredientImage;
        CardView row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientName_tv = itemView.findViewById(R.id.ingredient_title);
            ingredientImage = itemView.findViewById(R.id.ingredient_img);
        }
    }
}
