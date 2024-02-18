package com.example.foodplanner.search.categories.view;

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
import com.example.foodplanner.model.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Category> items;
    private final Context context;
    private final OnCategoryClickListener listener;

    public CategoriesAdapter(Context context, List<Category> items, OnCategoryClickListener listener) {
        super();
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    public void setList(List<Category> categories) {
        this.items = categories;
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
        String imageUrl = items.get(position).getImageUrl();
        Glide.with(context)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading))
                .into(holder.imageView);

        holder.name.setText(items.get(position).getName());

        holder.search_by_category_cardView.setOnClickListener(v -> listener.onCategoryClicked(items.get(position)));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        CardView search_by_category_cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.search_category_image_view);
            name = itemView.findViewById(R.id.search_category_title);
            search_by_category_cardView = itemView.findViewById(R.id.search_by_category_cardView);
        }
    }
}
