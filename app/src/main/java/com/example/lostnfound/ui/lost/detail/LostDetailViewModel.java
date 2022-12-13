package com.example.lostnfound.ui.lost.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LostDetailViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LostDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is LostDetail fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}