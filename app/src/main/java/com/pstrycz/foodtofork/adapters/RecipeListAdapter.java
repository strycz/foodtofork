package com.pstrycz.foodtofork.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pstrycz.foodtofork.R;
import com.pstrycz.foodtofork.rest.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListAdapter
        extends RecyclerView.Adapter<RecipeListAdapter.FoodViewHolder> {

    private final List<Recipe> recipeList;
    private Context context;

    public interface FoodAdapterClickListener {
        void onSearchResultItemClicked(Recipe recipe);
    }

    public RecipeListAdapter(List<Recipe> items) {
        recipeList = items;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);

        FoodViewHolder viewHolder = new FoodViewHolder(view);
        viewHolder.context = context;

        return viewHolder;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        context = recyclerView.getContext();
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, final int position) {

        holder.bindData(recipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public Context context;

        @BindView(R.id.recipe_thumbnail)
        ImageView recipeThumbnail;
        @BindView(R.id.recipe_label)
        TextView recipeLabel;

        public FoodViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
            this.view = view;
        }

        public void bindData(Recipe recipe) {
            recipeLabel.setText(recipe.getTitle());

            view.setOnClickListener(v -> ((FoodAdapterClickListener)context).onSearchResultItemClicked(recipe));

            Picasso.with(context)
                    .load(recipe.getImageUrl())
                    .resize(200, 200)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(recipeThumbnail);

        }
    }
}