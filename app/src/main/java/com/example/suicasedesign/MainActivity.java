package com.example.suicasedesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.suicasedesign.Adapter.ItemsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;  // A floating action button used to add new items.
    private ArrayList<Item> items; //A list to store items retrieved from a database.
    private DatabaseHelper databaseHelper; //database operations
    private RecyclerView itemRecyclerView; // RecyclerView used to display the list of items.
    private ItemsAdapter itemsAdapter; //An adapter to connect the RecyclerView with items
    private NavigationView navigationView;// A navigation view fordrawer menu.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Top Navigation View
        navigationView=findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {   //to handle navigation menu item clicks.

                int id = item.getItemId();
                if (id == R.id.item_home) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Click to home ", Toast.LENGTH_SHORT).show();
                }
                if (id == R.id.item_about) {
                    Toast.makeText(MainActivity.this, "Clickto about ", Toast.LENGTH_SHORT).show();
                }
                if (id == R.id.item_contact) {
                    Toast.makeText(MainActivity.this, "Clickto about ", Toast.LENGTH_SHORT).show();
                }
                if (id == R.id.item_setting) {
                    Toast.makeText(MainActivity.this, "Clickto about ", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

        });
        final DrawerLayout drawerLayout = findViewById(R.id.drewer);
        findViewById(R.id.imagemenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        // initialize data
        items = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        // recycler view setup
        itemRecyclerView = findViewById(R.id.orderRecycler);
        setupRecyclerView();

        // item touch helper setup
        setupItemTouchHelper();

        // It also initializes the FAB (Floating Action Button) for adding new items.
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> startActivity(add_items.getIntent(getApplicationContext())));

    }

    private void setupItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //This method sets up an ItemTouchHelper to handle swipe gestures on RecyclerView items.
            //It allows swiping items to the left for deletion and swiping items to the right for marking them as purchased.
            //When an item is swiped, it updates the item's status in the database and notifies the adapter of the change.

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Item item = items.get(position);

                if (direction == ItemTouchHelper.LEFT) {
                    databaseHelper.delete(item.getId());

                    items.remove(position);
                    itemsAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                    Toast.makeText(
                            getApplicationContext(),
                            "Item deleted.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else if (direction == ItemTouchHelper.RIGHT) {
                    item.setPurchased(true);

                    databaseHelper.update(
                            item.getId(),
                            item.getName(),
                            item.getPrice(),
                            item.getDescription(),
                            item.getImage().toString(),
                            item.isPurchased()
                    );
                    itemsAdapter.notifyItemChanged(position);

                    Toast.makeText(
                            getApplicationContext(),
                            "Item updated.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        itemTouchHelper.attachToRecyclerView(itemRecyclerView);
    }

    //This method is called when the activity starts or resumes.
    //It calls the retrieveData method to populate the items list with data from the database
    @Override
    protected void onStart() {
        super.onStart();
        retrieveData();
    }

    //This method queries the database using the getAll method of DatabaseHelper to retrieve all items.
    //It clears the items list and adds items retrieved from the database.
    //It updates the RecyclerView adapter to reflect the changes.
    private void retrieveData() {
        Cursor cursor = databaseHelper.getAll();
        if (cursor == null) {
            return;
        }

        items.clear();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.setId(cursor.getInt(0));
            item.setName(cursor.getString(1));
            item.setPrice(cursor.getDouble(2));
            item.setDescription(cursor.getString(3));
            item.setImage(Uri.parse(cursor.getString(4)));

            items.add(cursor.getPosition(), item);
            itemsAdapter.notifyItemChanged(cursor.getPosition());
            Log.d("MainActivity", "Item: " + item.getId() + " added at " + cursor.getPosition());
        }
    }

    private void setupRecyclerView(
    ) {
        itemsAdapter = new ItemsAdapter(items, (view, position) -> startActivity(items_details.getIntent(
                getApplicationContext(),
                items.get(position).getId())
        ));
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemRecyclerView.setAdapter(itemsAdapter);
    }

    //This method initializes the RecyclerView and sets up the adapter (ItemsAdapter) to display the items.
    //It also specifies a click listener for each item to start the items_details activity when an item is clicked.
}

