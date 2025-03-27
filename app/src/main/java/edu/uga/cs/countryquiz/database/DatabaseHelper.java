package edu.uga.cs.countryquiz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "country_quiz.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_QUIZRESULTS_TABLE =
            "CREATE TABLE quizResults (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "quiz_date TEXT NOT NULL, " +
                    "quiz_score INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUIZRESULTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS quizResults");
        onCreate(db);
    }
}

