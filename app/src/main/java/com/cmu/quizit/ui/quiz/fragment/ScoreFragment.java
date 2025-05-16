package com.cmu.quizit.ui.quiz.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.cmu.quizit.R;
import com.cmu.quizit.viewmodel.AnswerViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreFragment extends Fragment {

    private AnswerViewModel answerViewModel;
    private TextView scoreText;
    private Button goBackButton;

    public ScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ScoreFragment.
     */
    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        
        scoreText = view.findViewById(R.id.score);
        goBackButton = view.findViewById(R.id.go_back_button);

        // Observe the score
        answerViewModel.getScore().observe(getViewLifecycleOwner(), score -> {
            scoreText.setText(String.valueOf(score));
        });

        // Handle Go Back button click
        goBackButton.setOnClickListener(v -> {
            // Clear the back stack and return to the quiz start
            requireActivity().getSupportFragmentManager().popBackStack(null, 0);
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainCategoryFragment.newInstance("",""))
                    .commit();
        });

        return view;
    }
}