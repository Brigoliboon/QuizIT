package com.cmu.quizit.ui.quiz.fragment;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import android.app.AlertDialog;

import com.cmu.quizit.R;
import com.cmu.quizit.data.database.OptionDatabase;
import com.cmu.quizit.model.question.Question;
import com.cmu.quizit.ui.adapter.CategoryOptionAdapter;
import com.cmu.quizit.ui.adapter.ChoicesAdapter;
import com.cmu.quizit.viewmodel.AnswerViewModel;
import com.cmu.quizit.viewmodel.MainCategoryViewModel;
import com.cmu.quizit.viewmodel.SubCategoryViewModel;

import java.util.ArrayList;

public class AnswerFragment extends Fragment {

    private SubCategoryViewModel subCategoryViewModel;
    private AnswerViewModel answerViewModel;
    private TextView quiz_curr_cnt;
    private RecyclerView recyclerView;
    private CountDownTimer countDownTimer;

    private ChoicesAdapter viewHolder;

    public static AnswerFragment newInstance() {
        return new AnswerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Add back button functionality
        ImageView backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            // Show confirmation dialog before going back
            new AlertDialog.Builder(requireContext(), R.style.MyDialogTheme)
                .setTitle("Leave Quiz")
                .setMessage("Are you sure you want to leave? Your progress will be lost.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .setNegativeButton("No", null)
                .show();
        });

        answerViewModel = new ViewModelProvider(requireActivity()).get(AnswerViewModel.class);
        subCategoryViewModel = new ViewModelProvider(requireActivity()).get(SubCategoryViewModel.class);

        subCategoryViewModel.getChosenCategory().observe(getViewLifecycleOwner(), category->{
            ArrayList<Question> questions = category.getQuestions();
            displayQuestionUI(view, questions);

        });
    }

    public void displayQuestionUI(View view, ArrayList<Question> questions){
        quiz_curr_cnt = view.findViewById(R.id.quiz_curr_cnt);
        recyclerView = view.findViewById(R.id.answer_choices);

        answerViewModel.setCounter(0);
        answerViewModel.getCounter().observe(getViewLifecycleOwner(), count->{
            if (count >= questions.size()) {
                // Navigate to score fragment
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, ScoreFragment.newInstance())
                        .commit();
                return;
            }

            startProgressTimer(view, count);

            quiz_curr_cnt.setText(String.valueOf(count + 1));
            Question current_question = questions.get(count);

            TextView question = view.findViewById(R.id.question);
            question.setText(current_question.getQuestion());

            if (viewHolder != null) {
                viewHolder.resetState();
            }

            viewHolder = new ChoicesAdapter(requireContext(), current_question.getChoices(), choice -> {
                // Cancel the timer when an answer is selected
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                
                Toast.makeText(requireContext(), "Confirm!", Toast.LENGTH_SHORT).show();
                Log.d("Choices", current_question.getChoices().toString());
                if (choice.isCorrect()){
                    answerViewModel.incrScore();
                    Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(requireContext(), "Incorrect!", Toast.LENGTH_SHORT).show();
                }

                // Add a delay before moving to the next question or showing score
                new Handler().postDelayed(() -> {
                    if (count < questions.size() - 1) {
                        answerViewModel.setCounter(count + 1);
                    } else {
                        // This is the last question, navigate to score fragment
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, ScoreFragment.newInstance())
                                .commit();
                    }
                }, 1500); // 1.5 second delay
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(viewHolder);
        });
    }

    private void startProgressTimer(View view, int count){
        ProgressBar progressBar = view.findViewById(R.id.progress_timer);
        progressBar.setMax(40);
        progressBar.setProgress(40);
        progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.primary_bg)));

        CountDownTimer countDownTimer = getCountDownTimer(count, progressBar);
        countDownTimer.cancel();
        countDownTimer.start();
        Log.d("Thread Inspection", Thread.currentThread().toString());
    }

    // Singleton
    @NonNull
    private CountDownTimer getCountDownTimer(int count, ProgressBar progressBar) {
        int totalTime = 40000;
        if (countDownTimer == null) {
            countDownTimer = new CountDownTimer(totalTime, 1000) {
                public void onTick(long millisUntilFinished) {
                    int progress = (int) (millisUntilFinished / 1000);
                    if (progress <= 10) {
                        progressBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.wrong)));
                    }
                    progressBar.setProgress(progress);
                }

                public void onFinish() {
                    progressBar.setProgress(0);
                    if (count != 14) {
                        answerViewModel.setCounter(count + 1);
                    }
                }

            };
        }
        return countDownTimer;
    }

    private void updateChoiceStatusUI(View view, int containerId,int textId, int colorId){
        // TODO
    }
}