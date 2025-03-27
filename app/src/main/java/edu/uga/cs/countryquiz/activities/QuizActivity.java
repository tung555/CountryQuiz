package edu.uga.cs.countryquiz.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.uga.cs.countryquiz.R;
import edu.uga.cs.countryquiz.adapters.QuizPagerAdapter;
import edu.uga.cs.countryquiz.async.DatabaseAsyncTask;
import edu.uga.cs.countryquiz.fragments.QuizFragment;
import edu.uga.cs.countryquiz.fragments.QuizResultFragment;
import edu.uga.cs.countryquiz.models.Country;
import edu.uga.cs.countryquiz.models.Question;
import edu.uga.cs.countryquiz.utils.CSVReader;

public class QuizActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private QuizPagerAdapter adapter;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        viewPager = findViewById(R.id.viewPager);
        questions = new ArrayList<>();

        List<Country> allCountries = CSVReader.readCountriesFromCSV(this, "country_continent.csv");
        if (allCountries == null || allCountries.size() < 6) {
            Log.e("QuizActivity", "Not enough countries!");
            finish();
            return;
        }

        List<String> continents = Arrays.asList("Africa","Asia","Europe","North America","South America","Australia");
        Random rnd = new Random();
        List<Country> pool = new ArrayList<>(allCountries);

        for(int i=0; i<6; i++){
            int idx = rnd.nextInt(pool.size());
            Country c = pool.remove(idx);

            List<String> opts = new ArrayList<>();
            opts.add(c.getContinent());
            List<String> wrong = new ArrayList<>(continents);
            wrong.remove(c.getContinent());
            Collections.shuffle(wrong);
            opts.add(wrong.get(0));
            opts.add(wrong.get(1));
            Collections.shuffle(opts);

            questions.add(new Question(c, opts, c.getContinent()));
        }

        adapter = new QuizPagerAdapter(this, questions);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
            int prev = -1;
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                if(prev!=-1 && position>prev){
                    QuizFragment frag = (QuizFragment) getSupportFragmentManager()
                            .findFragmentByTag("f"+adapter.getItemId(prev));
                    if(frag!=null) frag.disableOptions();
                }
                prev = position;

                if(position == questions.size()){
                    int score = calculateScore();
                    QuizResultFragment frag = (QuizResultFragment) getSupportFragmentManager()
                            .findFragmentByTag("f"+adapter.getItemId(position));
                    if(frag!=null) frag.displayScore(score);
                    new DatabaseAsyncTask(QuizActivity.this).execute(score);
                }
            }
        });
    }

    private int calculateScore(){
        int sc=0;
        for(Question q: questions){
            if(q.getUserAnswer()!=null && q.getUserAnswer().equalsIgnoreCase(q.getCorrectAnswer())){
                sc++;
            }
        }
        return sc;
    }
}
