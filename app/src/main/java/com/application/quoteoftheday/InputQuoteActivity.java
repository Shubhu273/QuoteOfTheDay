package com.application.quoteoftheday;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class InputQuoteActivity extends AppCompatActivity {

    private EditText inputQuoteEditText;
    private Button submitQuoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_quote);

        inputQuoteEditText = findViewById(R.id.inputQuoteEditText);
        submitQuoteButton = findViewById(R.id.submitQuoteButton);

        submitQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userQuote = inputQuoteEditText.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("USER_QUOTE", userQuote);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close the input activity and return to the main activity
            }
        });
    }
}
