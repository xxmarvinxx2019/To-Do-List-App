package com.example.to_do_list_app;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetViewHolder extends RecyclerView.ViewHolder {

    public TextView t1,t2;

    public SetViewHolder(@NonNull View itemView) {
        super(itemView);
        t1 = itemView.findViewById(R.id.texts1);
        t2 = itemView.findViewById(R.id.texts2);
    }
}
