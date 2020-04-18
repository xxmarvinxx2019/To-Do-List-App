package com.example.to_do_list_app;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    public ArrayList<Todo> list;
    private OnNoteListener onNoteListener;
    public MyAdapter(Context context, ArrayList<Todo> list, OnNoteListener onNoteListener){
        this.context = context;
        this.list = list;
        this.onNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout,null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new MyViewHolder(view, onNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Todo student = list.get(position);
        String id = student.getId();
        holder.text1.setText(student.getId());
        holder.text2.setText(student.getDescription());
        holder.text3.setText(student.getDateofrecord());
        holder.text4.setText(student.getTimeofrecord());
        holder.itemView.setTag(id);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1,text2,text3,text4;
        OnNoteListener OnNoteListener;
        public MyViewHolder(View view, OnNoteListener OnNoteListener){
            super(view);
            text1 = view.findViewById(R.id.texts1);
            text2 = view.findViewById(R.id.texts2);
            text3 = view.findViewById(R.id.texts3);
            text4 = view.findViewById(R.id.texts4);
            this.OnNoteListener = OnNoteListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            OnNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
    public void addItems(ArrayList<Todo> newlist){
        int position = list.size() + 1;
        newlist.addAll(newlist);
        notifyItemChanged(position, newlist);
    }
}
