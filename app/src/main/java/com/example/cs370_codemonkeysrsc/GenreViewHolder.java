package com.example.cs370_codemonkeysrsc;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GenreViewHolder extends RecyclerView.ViewHolder {
    private TextView genreTextView;

    public GenreViewHolder(@NonNull View itemView) {
        super(itemView);
        genreTextView = itemView.findViewById(R.id.genre_text);
    }

    public void bindView(Model model, final OnGenreItemClickListener listener) {
        genreTextView.setText(model.getTextLine());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onGenreItemClick(model.getID());
            }
        });
    }

}
