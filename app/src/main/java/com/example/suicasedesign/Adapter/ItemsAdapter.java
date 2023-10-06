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



// managing and binding data to the recycler view
// recycler view is responsible for to display the list of items effectively
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private final RecyclerViewItemClickListener itemClickListener;
    private final ArrayList<Item> items;


    // managing and binding the data in recycler view to give specific action.

    public ItemsAdapter(ArrayList<Item> items, RecyclerViewItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.items = items;

    }

    // on create view holder is needed to represent an item to the list

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items,parent,false);
        return new ItemViewHolder(view);
    }

    // on bind view holder is called ViewHolder with data from a specific item in the items list.
    // It sets the text and image views in the ViewHolder based on the Item object at the given position.

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

    //Returns the total number of items in the items list, which determines the size of the RecyclerView.

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView textViewName, textViewPrice, textViewDescription;


        //This inner class represents the ViewHolder for each item in the RecyclerView.
        //It holds references to the views within the item layout (imageView, textViewName, textViewPrice, textViewDescription).
        //It also sets an OnClickListener on the item view itself.


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewPrice = itemView.findViewById(R.id.item_price);
            textViewDescription = itemView.findViewById(R.id.item_dis);

            itemView.setOnClickListener(this::itemViewOnClick);
        }

        //This method is invoked when an item in the RecyclerView is clicked.
        // It triggers the onItemClick method of the itemClickListener, passing the clicked view and the adapter position.

        private void itemViewOnClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
