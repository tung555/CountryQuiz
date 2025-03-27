package edu.uga.cs.countryquiz.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import edu.uga.cs.countryquiz.R;
import edu.uga.cs.countryquiz.models.Question;
import java.util.List;

public class QuizFragment extends Fragment {

    private static final String ARG_QUESTION = "question";
    private Question question;

    private TextView tvQuestion;
    private RadioGroup rgOptions;
    private RadioButton rbOption1, rbOption2, rbOption3;

    public QuizFragment() { }

    public static QuizFragment newInstance(Question question) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (Question) getArguments().getSerializable(ARG_QUESTION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        tvQuestion = view.findViewById(R.id.question);
        rgOptions = view.findViewById(R.id.rgOptions);
        rbOption1 = view.findViewById(R.id.rbOption1);
        rbOption2 = view.findViewById(R.id.rbOption2);
        rbOption3 = view.findViewById(R.id.rbOption3);

        populateQuestion();

        rgOptions.setOnCheckedChangeListener((group, checkedId) -> {
            String answer = "";
            if (checkedId == R.id.rbOption1) answer = rbOption1.getText().toString();
            else if (checkedId == R.id.rbOption2) answer = rbOption2.getText().toString();
            else if (checkedId == R.id.rbOption3) answer = rbOption3.getText().toString();
            question.setUserAnswer(answer);
        });

        return view;
    }

    private void populateQuestion() {
        if (question != null && question.getCountry() != null) {
            tvQuestion.setText("Which continent is " + question.getCountry().getName() + " located in?");
            List<String> opts = question.getOptions();
            if (opts.size() >= 3) {
                rbOption1.setText(opts.get(0));
                rbOption2.setText(opts.get(1));
                rbOption3.setText(opts.get(2));
            }
        }
    }

    public void disableOptions() {
        rgOptions.setEnabled(false);
        rbOption1.setEnabled(false);
        rbOption2.setEnabled(false);
        rbOption3.setEnabled(false);
    }
}