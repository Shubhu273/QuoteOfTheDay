package com.application.quoteoftheday;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private QuoteAdapter adapter;
    private QuoteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database
        db = Room.databaseBuilder(getApplicationContext(), QuoteDatabase.class, "quote-db")
                .allowMainThreadQueries()
                .build();

        // Load and display favorite quotes
        List<QuoteEntity> favoriteQuotes = db.quoteDao().getAllQuotes();
        adapter = new QuoteAdapter(favoriteQuotes);
        recyclerView.setAdapter(adapter);
    }
}

