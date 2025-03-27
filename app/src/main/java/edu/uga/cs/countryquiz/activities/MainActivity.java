package edu.uga.cs.countryquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import edu.uga.cs.countryquiz.R;

public class MainActivity extends AppCompatActivity {

    private Button btnStartQuiz, btnViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Optionally populate the database from CSV (run AsyncTask)
        //new DatabaseAsyncTask(this).execute();

        setContentView(R.layout.activity_main);

        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        btnViewResults = findViewById(R.id.btnViewResults);

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });

        btnViewResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                startActivity(intent);
            }
        });
    }
}
