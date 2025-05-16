package com.cmu.quizit.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnswerViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<Integer> counter = new MutableLiveData<>();
    private MutableLiveData<Integer> score = new MutableLiveData<>(0);

    public void setCounter(int counter) {
        this.counter.setValue(counter);
    }

    public LiveData<Integer> getCounter() {
        return counter;
    }

    public LiveData<Integer> getScore() {
        return score;
    }

    public void incrScore() {
        this.score.setValue(this.score.getValue() + 1);
    }
}