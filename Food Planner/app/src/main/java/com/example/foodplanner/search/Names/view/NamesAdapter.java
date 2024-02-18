package com.example.foodplanner.search.Names.view;

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
import com.example.foodplanner.model.Meal;


import java.util.List;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ViewHolder> {

    private List<Meal> items;
    private final Context context;
    private final OnMealClickListener listener;

    public NamesAdapter(Context context, List<Meal> items, OnMealClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Meal> meals) {
        this.items = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_by_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context)
                .load(items.get(position).getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading))
                .into(holder.imageView);

        holder.titleView.setText(items.get(position).getName());

        holder.cardView.setOnClickListener(v -> listener.onMealClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_category_image_view);
            titleView = itemView.findViewById(R.id.search_category_title);
            cardView = itemView.findViewById(R.id.search_by_category_cardView);
        }
    }
}
