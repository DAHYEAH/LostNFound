package com.example.lostnfound.ui.lost.write;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LostWriteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LostWriteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is LostWrite fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}