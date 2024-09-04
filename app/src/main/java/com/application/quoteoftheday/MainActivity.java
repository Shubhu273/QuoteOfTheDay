package com.application.quoteoftheday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1; // Request code for starting InputQuoteActivity
    private TextView quoteTextView;
    private Button refreshButton, shareButton, favoriteButton, viewFavoritesButton, inputQuoteButton;
    private List<String> quotes;
    private String currentQuote;
    private QuoteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database
        db = Room.databaseBuilder(getApplicationContext(), QuoteDatabase.class, "quote-db")
                .allowMainThreadQueries()
                .build();

        // Initialize views
        quoteTextView = findViewById(R.id.quoteTextView);
        refreshButton = findViewById(R.id.refreshButton);
        shareButton = findViewById(R.id.shareButton);
        favoriteButton = findViewById(R.id.favoriteButton);
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton);
        inputQuoteButton = findViewById(R.id.inputQuoteButton);

        // List of quotes
        quotes = Arrays.asList(
                "The best way to predict the future is to create it.",
                "You are never too old to set another goal or to dream a new dream.",
                "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                "The only way to do great work is to love what you do.",
                "If you can dream it, you can achieve it."
        );

        // Load and display a quote
        loadQuote();

        // Refresh button click listener
        refreshButton.setOnClickListener(v -> loadQuote());

        // Share button click listener
        shareButton.setOnClickListener(v -> shareQuote());

        // Favorite button click listener
        favoriteButton.setOnClickListener(v -> saveQuoteToFavorites());

        // View Favorites button click listener
        viewFavoritesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        // Input quote button click listener
        inputQuoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InputQuoteActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void loadQuote() {
        Random random = new Random();
        currentQuote = quotes.get(random.nextInt(quotes.size()));
        quoteTextView.setText(currentQuote);

        // Save the current quote in SharedPreferences for daily display
        SharedPreferences sharedPreferences = getSharedPreferences("QuoteApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dailyQuote", currentQuote);
        editor.apply();
    }

    private void shareQuote() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, currentQuote);
        startActivity(Intent.createChooser(shareIntent, "Share Quote"));
    }

    private void saveQuoteToFavorites() {
        QuoteEntity quoteEntity = new QuoteEntity();
        quoteEntity.setQuote(currentQuote);
        db.quoteDao().insertQuote(quoteEntity);

        // Show a message or update the UI if necessary
        favoriteButton.setText("Saved!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String userQuote = data.getStringExtra("USER_QUOTE");
            currentQuote = userQuote;
            quoteTextView.setText(currentQuote);

            // Save the current user input quote in SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("QuoteApp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("dailyQuote", currentQuote);
            editor.apply();
        }
    }
}
