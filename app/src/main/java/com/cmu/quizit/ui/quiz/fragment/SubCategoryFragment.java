package com.cmu.quizit.ui.quiz.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmu.quizit.R;
import com.cmu.quizit.data.database.OptionDatabase;
import com.cmu.quizit.model.option.MainOption;
import com.cmu.quizit.model.option.SubOption;
import com.cmu.quizit.ui.adapter.CategoryOptionAdapter;
import com.cmu.quizit.viewmodel.MainCategoryViewModel;
import com.cmu.quizit.viewmodel.SubCategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryFragment extends Fragment {

    private static MainCategoryViewModel mainCategoryViewmodel;
    private static SubCategoryViewModel subCategoryViewModel;
    public static SubCategoryFragment newInstance() {
        return new SubCategoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Add back button functionality
        ImageView backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        observeOptionData(view);
    }

    public void observeOptionData(View view){
        mainCategoryViewmodel = new ViewModelProvider(requireActivity()).get(MainCategoryViewModel.class);
        TextView categoryTitle = view.findViewById(R.id.categoryTitle);

        mainCategoryViewmodel.getChosenOption().observe(getViewLifecycleOwner(), result -> {
            MainOption chosenOption = (MainOption) result;
            categoryTitle.setText(chosenOption.getTitle());

            displaySubcategories(view,chosenOption.getSubOptions());
        });
    }

    public void displaySubcategories(View view, List<SubOption> subOptions){
        RecyclerView recyclerView = view.findViewById(R.id.subcategory_recycler);
        mainCategoryViewmodel = new ViewModelProvider(requireActivity()).get(MainCategoryViewModel.class);
        subCategoryViewModel = new ViewModelProvider(requireActivity()).get(SubCategoryViewModel.class);

        CategoryOptionAdapter viewHolder = new CategoryOptionAdapter(requireContext(), subOptions, option -> {
            // TODO
            subCategoryViewModel.setChosenSubcategory(getContext(), (SubOption) option);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AnswerFragment.newInstance())
                    .addToBackStack(null) // So pressing back can return to QuizFragment if needed
                    .commit();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(viewHolder);
    }
}