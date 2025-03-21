package edu.uga.cs.countryquiz;

import android.database.Cursor;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_COUNTRIES, null);

            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();

            if (count == 0) {
                dbHelper.loadCountriesFromCSV(this);
                Log.d("DB", "Loading data from CSV...");
            } else {
                Log.d("DB", "Database already populated");

            }
        }
    }
