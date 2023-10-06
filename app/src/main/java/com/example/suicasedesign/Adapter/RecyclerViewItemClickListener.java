package com.example.suicasedesign.Adapter;

import android.view.View;

// interface is applied to provide the methods like single method
// view parameter represents the view that was clicked. When an item in the RecyclerView is clicked,
// the clicked view is passed as an argument.

// position This parameter represents the position of the clicked item in the RecyclerView's list.
// It indicates which item in the list was clicked

public interface RecyclerViewItemClickListener {
    public void onItemClick(View view, int position);
}
