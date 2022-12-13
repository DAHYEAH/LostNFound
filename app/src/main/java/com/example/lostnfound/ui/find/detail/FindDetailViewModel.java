package com.example.lostnfound.ui.find.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindDetailViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FindDetailViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is LostDetail fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}