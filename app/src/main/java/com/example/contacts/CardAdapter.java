package com.example.contacts;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CardModel> models;
    private ItemClickListener itemClickListener;
    private ItemLongClickListener itemLongClickListener;

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public interface ItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnItemLongClickListener(ItemLongClickListener listener) {
        this.itemLongClickListener = listener;
    }

    public CardAdapter(Context context, ItemClickListener clickListener, ItemLongClickListener longClickListener, ArrayList<CardModel> models) {
        this.context = context;
        this.itemClickListener = clickListener;
        this.itemLongClickListener = longClickListener;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardModel model = models.get(position);
        holder.imageView.setImageResource(model.GetImage());
        holder.textViewFirstName.setText(model.GetText());
        holder.textViewLastName.setText(model.GetText2());

        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (itemLongClickListener != null) {
                itemLongClickListener.onItemLongClick(position);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewFirstName;
        TextView textViewLastName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView2);
            textViewFirstName = itemView.findViewById(R.id.textView);
            textViewLastName = itemView.findViewById(R.id.textView2);
        }
    }
}