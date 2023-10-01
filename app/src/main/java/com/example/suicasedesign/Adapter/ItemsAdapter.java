package com.example.suicasedesign.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suicasedesign.Item;
import com.example.suicasedesign.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private final RecyclerViewItemClickListener itemClickListener;
    private final ArrayList<Item> items;

    public ItemsAdapter(ArrayList<Item> items, RecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        holder.textViewName.setText(item.getName());
        if(item.isPurchased()) {
            holder.textViewName.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.baseline_check_24, 0);
        }
        holder.textViewPrice.setText(String.valueOf(item.getPrice()));
        holder.textViewDescription.setText(item.getDescription());
        Uri imageUri = item.getImage();
        if(imageUri != null) {
            holder.imageView.setImageURI(imageUri);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView textViewName, textViewPrice, textViewDescription;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewPrice = itemView.findViewById(R.id.item_price);
            textViewDescription = itemView.findViewById(R.id.item_dis);

            itemView.setOnClickListener(this::itemViewOnClick);
        }

        private void itemViewOnClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
