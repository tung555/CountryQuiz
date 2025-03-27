package edu.uga.cs.countryquiz.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uga.cs.countryquiz.R;
import edu.uga.cs.countryquiz.activities.MainActivity;
import edu.uga.cs.countryquiz.activities.ResultsActivity;

public class QuizResultFragment extends Fragment {
    private static final String ARG_SCORE = "score";
    private Integer score = null;

    public QuizResultFragment() { }

    public static QuizResultFragment newInstance(int score) {
        QuizResultFragment f = new QuizResultFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCORE, score);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quizresult, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.finalScore);

        if (getArguments() != null) {
            score = getArguments().getInt(ARG_SCORE);
        }
        if (score != null) {
            tv.setText("You scored " + score + "/6!");
        }

        Button btnHomePage = view.findViewById(R.id.btnHomePage);
        Button btnViewPast = view.findViewById(R.id.btnViewPastResults);

        btnHomePage.setOnClickListener(v -> {
            requireActivity().finish();
            startActivity(new Intent(requireContext(), MainActivity.class));
        });

        btnViewPast.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ResultsActivity.class));
        });
    }

    public void displayScore(int newScore) {
        score = newScore;
        View v = getView();
        if (v != null) {
            TextView tv = v.findViewById(R.id.finalScore);
            if (tv != null) {
                tv.setText("You scored " + newScore + "/6!");
            }
        }
    }
}
