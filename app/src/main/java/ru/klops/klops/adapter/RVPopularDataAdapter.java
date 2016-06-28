package ru.klops.klops.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RVPopularDataAdapter extends RecyclerView.Adapter<RVPopularDataAdapter.PopularDataViewHolder> {
    @Override
    public PopularDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PopularDataViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PopularDataViewHolder extends RecyclerView.ViewHolder {
        public PopularDataViewHolder(View itemView) {
            super(itemView);
        }
    }
}
