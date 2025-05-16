package com.cmu.quizit.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmu.quizit.model.option.Option;

public class MainCategoryViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    MutableLiveData<Option> pickedOption = new MutableLiveData<>();
    public void setPickedOption(Option opt) {
        pickedOption.setValue(opt);
    }

    public MutableLiveData<Option> getChosenOption(){
        return pickedOption;
    }
}