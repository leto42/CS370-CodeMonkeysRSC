package com.example.cs370_codemonkeysrsc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenreAdapter extends RecyclerView.Adapter<GenreViewHolder> {

    private List<Model> models;
    private OnGenreItemClickListener listener;

    public GenreAdapter(List<Model> models, OnGenreItemClickListener listener) {
        this.models = models;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_layout,parent,false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        holder.bindView(models.get(position), this.listener);
    }


    @Override
    public int getItemCount() {
        return models.size();
    }
}
