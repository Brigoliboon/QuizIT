package com.cmu.quizit.ui.quiz.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cmu.quizit.R;
import com.cmu.quizit.data.database.OptionDatabase;
import com.cmu.quizit.model.option.MainOption;
import com.cmu.quizit.model.option.Option;
import com.cmu.quizit.ui.adapter.CategoryOptionAdapter;
import com.cmu.quizit.viewmodel.MainCategoryViewModel;
import com.cmu.quizit.viewmodel.SubCategoryViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainCategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static MainCategoryViewModel mainCategoryViewmodel ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment categories.
     */
    // TODO: Rename and change types and number of parameters
    public static MainCategoryFragment newInstance(String param1, String param2) {
        MainCategoryFragment fragment = new MainCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.option_choices);

        CategoryOptionAdapter viewHolder = new CategoryOptionAdapter(requireContext(), OptionDatabase.getMainOptions(requireContext()), option -> {
            // TODO
            mainCategoryViewmodel = new ViewModelProvider(requireActivity()).get(MainCategoryViewModel.class);
            mainCategoryViewmodel.setPickedOption(option);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SubCategoryFragment.newInstance())
                    .addToBackStack(null) // So pressing back can return to QuizFragment if needed
                    .commit();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(viewHolder);

    }
}