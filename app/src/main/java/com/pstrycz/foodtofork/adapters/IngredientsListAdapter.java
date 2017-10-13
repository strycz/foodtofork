package com.pstrycz.foodtofork.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pstrycz.foodtofork.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsListAdapter
        extends RecyclerView.Adapter<IngredientsListAdapter.FoodViewHolder> {

    private final List<String> items;

    public IngredientsListAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_item, parent, false);

        FoodViewHolder viewHolder = new FoodViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FoodViewHolder holder, final int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        public Context context;

        @BindView(R.id.ingredient_label)
        TextView ingredientLabel;

        public FoodViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        public void bindData(String string) {
            ingredientLabel.setText(string);

        }
    }
}