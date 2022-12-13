package com.example.lostnfound.ui.find.write;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FindWriteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FindWriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is FindWrite fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}