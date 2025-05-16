package com.cmu.quizit.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmu.quizit.model.option.Option;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {
    MutableLiveData<ArrayList<Option>> mainOptions = new MutableLiveData<>();

    public void getMainOption(){

    }
}