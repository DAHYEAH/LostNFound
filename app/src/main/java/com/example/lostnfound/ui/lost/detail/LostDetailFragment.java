package com.example.lostnfound.ui.lost.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lostnfound.databinding.FragmentLostDetailBinding;

public class LostDetailFragment extends Fragment {

    private FragmentLostDetailBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LostDetailViewModel LostDetailViewModel =
                new ViewModelProvider(this).get(LostDetailViewModel.class);

        binding = FragmentLostDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }  @Override

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    private void startToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}