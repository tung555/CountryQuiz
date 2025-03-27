package edu.uga.cs.countryquiz.utils;

import android.content.Context;
import edu.uga.cs.countryquiz.models.Country;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Country> readCountriesFromCSV(Context context, String fileName) {
        List<Country> countries = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            // Optionally skip header:
            // reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 2) {
                    String countryName = tokens[0].trim();
                    String continent = tokens[1].trim();
                    // id set to 0 (auto-generated in DB)
                    countries.add(new Country(0, countryName, continent));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countries;
    }
}
