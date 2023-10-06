package com.example.suicasedesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// display the detail of specific item

public class items_details extends AppCompatActivity {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE = "image";
    public static final String IS_PURCHASED = "purchased";
    public static final int EDIT_ITEM_REQUEST = 10001;  //An integer constant representing a request code used when starting the edit item activity.
    // This code is used to identify the result when the edit item activity finishes.

    public static Intent getIntent(Context context, int id) {
        Intent intent = new Intent(context, items_details.class);
        intent.putExtra(ID, id);

        return intent;

        //This is a static method used to create an Intent for starting this activity with the ID of the item to display.
        // This is typically used to launch this activity for viewing a specific item.
    }

    private Item item;  //Item class used to store and manipulate item data.
    private DatabaseHelper databaseHelper;  // DatabaseHelper class for database operations.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_details);
        //This method is called when the activity is created.
        //It initializes UI components (ImageView, TextViews, Buttons)
        // and retrieves the item ID from the incoming Intent's extras.


        //It then calls the retrieveData method to fetch item details based on the provided ID and elements with these details.
        //It also sets click listeners for the "Edit" and "Share" buttons.

        item = new Item();
        databaseHelper = new DatabaseHelper(this);
        ImageView imageView = findViewById(R.id.imageViewItem);
        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewPrice = findViewById(R.id.textViewPrice);
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        Button buttonEdit = findViewById(R.id.buttonEditItem);
        Button buttonShare = findViewById(R.id.buttonShareItem);

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt(items_details.ID);
        Log.d("ItemDetails: id:", id+"");

        item = retrieveData(id);
        imageView.setImageURI(item.getImage());
        textViewName.setText(item.getName());
        textViewPrice.setText(item.getPrice().toString());
        textViewDescription.setText(item.getDescription());
        buttonEdit.setOnClickListener(v -> startEditItemActivity(v, item));
        buttonShare.setOnClickListener(this::startShareItemActivity);
    }


    //This method is called when the "Edit" button is clicked.
    //It starts the edit_items activity by creating an Intent and passing the current item as an extra.
    private void startEditItemActivity(View v, Item item) {
        startActivity(edit_items.getIntent(getApplicationContext(), item));
    }

    private Item retrieveData(int id) {
        Cursor cursor = databaseHelper.getElementById(id);
        cursor.moveToNext();

        //This method retrieves item details from the database based on the given item ID.
        //It queries the database using the getElementById method of DatabaseHelper and
        // constructs an Item object with the retrieved data.
        //It handles cases where an image URI might be missing

        Item item = new Item();
        item.setId(cursor.getInt(0));
        item.setName(cursor.getString(1));
        item.setPrice(cursor.getDouble(2));
        item.setDescription(cursor.getString(3));
        item.setImage(Uri.EMPTY);
        try {
            Uri imageUri = Uri.parse(cursor.getString(4));
            item.setImage(imageUri);
        } catch (NullPointerException e) {
            Toast.makeText(
                    this,
                    "Error occurred in identifying image resource!",
                    Toast.LENGTH_SHORT
            ).show();
        }
        item.setPurchased(cursor.getInt(5) == 1);

        Log.d("ItemDetails:", item.toString());
        return item;
    }

    private void startShareItemActivity(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "Check your Suitcase Application ");
        startActivity(Intent.createChooser(intent, "Share via"));
    }

}
//This method is called when the "Share" button is clicked.
//It creates an Intent for sharing content (text/plain) and sets the subject and text to share.
//It then starts the sharing activity using Intent.createChooser.

