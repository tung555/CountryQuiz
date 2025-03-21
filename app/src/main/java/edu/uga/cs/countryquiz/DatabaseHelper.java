package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "countryQuiz.db";
    private static final int DATABASE_VERSION = 1;

    //tables
    public static final String TABLE_COUNTRIES = "countries";
    public static final String COLUMN_COUNTRY_ID = "_id";
    public static final String COLUMN_COUNTRY_NAME = "country_name";
    public static final String COLUMN_CONTINENT = "continent";

    public static final String TABLE_QUIZZES = "quizzes";
    public static final String COLUMN_QUIZ_ID = "_id";
    public static final String COLUMN_QUIZ_DATE = "date";
    public static final String COLUMN_QUIZ_SCORE = "score";

    private static final String CREATE_COUNTRIES_TABLE =
            "CREATE TABLE " + TABLE_COUNTRIES + " (" +
                    COLUMN_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_COUNTRY_NAME + " TEXT NOT NULL, " +
                    COLUMN_CONTINENT + " TEXT NOT NULL);";

    private static final String CREATE_QUIZZES_TABLE =
            "CREATE TABLE " + TABLE_QUIZZES + " (" +
                    COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_QUIZ_DATE + " TEXT NOT NULL, " +
                    COLUMN_QUIZ_SCORE + " INTEGER NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create both tables
        db.execSQL(CREATE_COUNTRIES_TABLE);
        db.execSQL(CREATE_QUIZZES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        // Recreate tables
        onCreate(db);
    }

    public void loadCountriesFromCSV(Context context) {
        AsyncTask.execute(() -> {
            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();

            try {
                InputStream inputStream = context.getResources().openRawResource(R.raw.country_continent);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                // Read CSV
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");

                    if (parts.length == 2) {
                        String country = parts[0].trim();
                        String continent = parts[1].trim();

                        // Insert the database
                        ContentValues values = new ContentValues();
                        values.put(COLUMN_COUNTRY_NAME, country);
                        values.put(COLUMN_CONTINENT, continent);

                        db.insert(TABLE_COUNTRIES, null, values);
                    }
                }

                db.setTransactionSuccessful();
                Log.d("DB", "Countries inserted into database successfully");

            } catch (Exception e) {
                Log.e("DB", "Error reading CSV file", e);
            } finally {
                db.endTransaction();
                db.close();
            }
        });
    }



}
