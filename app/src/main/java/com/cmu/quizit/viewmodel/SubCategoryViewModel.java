package com.cmu.quizit.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmu.quizit.data.database.OptionDatabase;
import com.cmu.quizit.model.option.MainOption;
import com.cmu.quizit.model.option.Option;
import com.cmu.quizit.model.option.SubOption;

import java.util.ArrayList;

public class SubCategoryViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public MutableLiveData<SubOption> chosenCategory = new MutableLiveData<>();

    public void setChosenSubcategory(Context context, SubOption option){
        chosenCategory.setValue(option);
        chosenCategory.getValue().loadQuestions(context, option);
    }

    public LiveData<SubOption> getChosenCategory() {
        return chosenCategory;
    }
}