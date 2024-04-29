package com.example.contacts;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CardAdapter.ItemClickListener, CardAdapter.ItemLongClickListener {

    private static final int ADD_CONTACT_REQUEST = 1;
    private static final int GALLERY_REQUEST_CODE = 100;

    int[] images = {
            R.drawable.icon1, R.drawable.icon2,
            R.drawable.icon3, R.drawable.icon4,
            R.drawable.icon5, R.drawable.icon6,
            R.drawable.icon7, R.drawable.icon8,
            R.drawable.icon9, R.drawable.icon10,
            R.drawable.icon11, R.drawable.icon12,
            R.drawable.icon13, R.drawable.icon14,
            R.drawable.icon15
    };

    ArrayList<CardModel> models = new ArrayList<>();
    CardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpModels();

        RecyclerView recyclerView = findViewById(R.id.rcView);
        adapter = new CardAdapter(this, this, this, models);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddDisplayContactActivity.class);
            startActivityForResult(intent, ADD_CONTACT_REQUEST);
        });
    }

    private void setUpModels() {
        String[] firstNames = getResources().getStringArray(R.array.firstNames);
        String[] lastNames = getResources().getStringArray(R.array.lastNames);

        for (int i = 0; i < firstNames.length; i++) {
            models.add(new CardModel(firstNames[i], images[i], lastNames[i]));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            String firstName = data.getStringExtra("firstName");
            String lastName = data.getStringExtra("lastName");
            String address = data.getStringExtra("address");
            String email = data.getStringExtra("email");
            String phone = data.getStringExtra("phone");
            String imageUri = data.getStringExtra("imageUri");

            Log.d("MainActivity", "Received data: " + firstName + " " + lastName + " " + address + " " + email + " " + phone + " " + imageUri);

            models.add(new CardModel(firstName, imageUri, lastName));
            adapter.notifyItemInserted(models.size());
            RecyclerView recyclerView = findViewById(R.id.rcView);
            recyclerView.scrollToPosition(models.size() - 1);
        }
    }

    @Override
    public void onItemClick(int position) {
        // Handle short click here
        CardModel selectedContact = models.get(position);
        Intent intent = new Intent(MainActivity.this, AddDisplayContactActivity.class);
        intent.putExtra("contactId", position); // Pass the contact ID to display its details
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        // Handle long click here (delete contact)
        models.remove(position);
        adapter.notifyItemRemoved(position);
    }
}