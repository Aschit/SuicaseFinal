package com.example.suicasedesign;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class add_items extends AppCompatActivity {

    // class variable
    private DatabaseHelper databaseHelper;  // making  connection with local database
    private EditText editTextName, editTextPrice, editTextDescription; //variables for entering item details
    private ImageView imageView; // to display the image
    private Uri imageUri;  // stores the URI of selected image

    public static Intent getIntent(Context context) {
        //This is a static method used to create an intent to start this activity.
        return new Intent(context, add_items.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        //This method is called when the activity is created.
        //It initializes the UI components, sets click listeners, and prepares the database helper.
        //It also sets an initial empty Uri for the image.


        imageUri = Uri.EMPTY;

        databaseHelper = new DatabaseHelper(this);
        Button buttonAddItem = findViewById(R.id.buttonSaveItem);
        editTextName = findViewById(R.id.editTextName);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextDescription = findViewById(R.id.editTextDescription);
        imageView = findViewById(R.id.circleImageViewItem);

        imageView.setOnClickListener(this::pickImage);
        buttonAddItem.setOnClickListener(this::saveItem);
    }

    //This method is called when the "Save" button is clicked.
    //It retrieves input data (name, price, description) from EditText fields.
    //It validates the input (e.g., checking if the name is empty, if the price is a valid number, and if the price is greater than zero).
    //If the input is valid, it attempts to insert the item into the database using the databaseHelper.
    //It displays a toast message to indicate success or failure and finishes the activity if the item is saved successfully.

    private void saveItem(View view) {
        String name = editTextName.getText().toString();
        if (name.isEmpty()) {
            editTextName.setError("Name field is empty");
            editTextName.requestFocus();
        }
        double price = 0;
        try {
            price = Double.parseDouble(editTextPrice.getText().toString());
        } catch (NullPointerException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Something wrong with price.",
                    Toast.LENGTH_SHORT
            ).show();
        } catch (NumberFormatException e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Price should be a number",
                    Toast.LENGTH_SHORT
            ).show();
        }
        if (price <= 0) {
            editTextPrice.setError("Price should be greater than 0.");
            editTextPrice.requestFocus();
        }
        String description = editTextDescription.getText().toString();
        if(description.isEmpty()) {
            editTextDescription.setError("Description is empty.");
            editTextDescription.requestFocus();
        }

        if (databaseHelper.insert(name, price, description, imageUri.toString(), false)) {
            Toast.makeText(getApplicationContext(), "Saved Successfully",
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }

    //This method is called when the ImageView for selecting an image is clicked.
    //It uses ImagePickerUtility.pickImage to display the process of picking an image from the device's storage.

    private void pickImage(View view) {

        ImagePickerUtility.pickImage(view, add_items.this);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);


        //This method is called when the image selection activity returns a result.
        //It retrieves the selected image's URI and sets it in the imageView to display the chosen image.
    }
}

