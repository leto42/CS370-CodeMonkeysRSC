package com.example.cs370_codemonkeysrsc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adapter extends RecyclerView.Adapter {
    List<Model> modelList;

    public adapter(List<Model> modelList) {
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_layout,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((myViewHolder)holder).textLineTxt.setText(modelList.get(position).getTextLine());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView textLineTxt;
        Button submitButton;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textLineTxt = itemView.findViewById(R.id.textID);
            submitButton = itemView.findViewById(R.id.submitButtonID);
        }
    }
}
