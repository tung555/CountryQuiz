package edu.uga.cs.countryquiz.async;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.uga.cs.countryquiz.database.DatabaseHelper;

public class DatabaseAsyncTask extends AsyncTask<Integer, Void, Void> {

    private final Context context;

    public DatabaseAsyncTask(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    protected Void doInBackground(Integer... scores) {
        int score = scores[0];
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("quiz_date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        cv.put("quiz_score", score);

        db.insert("quizzes", null, cv);
        db.close();
        return null;
    }
}
