package edu.uga.cs.countryquiz.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.uga.cs.countryquiz.fragments.QuizFragment;
import edu.uga.cs.countryquiz.fragments.QuizResultFragment;
import edu.uga.cs.countryquiz.models.Question;

import java.util.List;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private final List<Question> questions;

    public QuizPagerAdapter(@NonNull FragmentActivity fa, List<Question> questions) {
        super(fa);
        this.questions = questions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < questions.size()) {
            return QuizFragment.newInstance(questions.get(position));
        } else {
            return QuizResultFragment.newInstance(0);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size() + 1;
    }
}
