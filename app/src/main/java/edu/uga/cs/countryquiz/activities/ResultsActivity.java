package edu.uga.cs.countryquiz.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import edu.uga.cs.countryquiz.R;
import edu.uga.cs.countryquiz.database.DatabaseHelper;
import edu.uga.cs.countryquiz.models.QuizResult;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private TextView tvResultsContent;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        tvResultsContent = findViewById(R.id.resultsContent);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        new LoadResultsTask().execute();
    }

    private class LoadResultsTask extends AsyncTask<Void, Void, List<QuizResult>> {
        @Override
        protected List<QuizResult> doInBackground(Void... voids) {
            List<QuizResult> results = new ArrayList<>();
            DatabaseHelper helper = new DatabaseHelper(ResultsActivity.this);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT quiz_date, quiz_score FROM quizzes ORDER BY quiz_date DESC", null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String date = cursor.getString(cursor.getColumnIndex("quiz_date"));
                    int score = cursor.getInt(cursor.getColumnIndex("quiz_score"));
                    results.add(new QuizResult(date, score));
                }
                cursor.close();
            }
            db.close();
            return results;
        }

        @Override
        protected void onPostExecute(List<QuizResult> results) {
            if (results.isEmpty()) {
                tvResultsContent.setText("No past quizzes found.");
                return;
            }
            StringBuilder builder = new StringBuilder();
            for (QuizResult r : results) {
                builder.append("Date: ")
                        .append(r.getQuizDate())
                        .append(" — Score: ")
                        .append(r.getQuizScore())
                        .append("\n");
            }
            tvResultsContent.setText(builder.toString());
        }
    }
}
