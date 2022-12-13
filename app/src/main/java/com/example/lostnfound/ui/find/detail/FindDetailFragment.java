package com.example.lostnfound.ui.find.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lostnfound.databinding.FragmentFindDetailBinding;

public class FindDetailFragment extends Fragment {

    private FragmentFindDetailBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FindDetailViewModel FindDetailViewModel =
                new ViewModelProvider(this).get(FindDetailViewModel.class);

        binding = FragmentFindDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        String posttime = getArguments().getString("posttime");
//        Bundle extra = this.getArguments();
//        if(extra != null) {
//            extra = getArguments();
//
//
//            Toast.makeText(getActivity(),posttime,Toast.LENGTH_SHORT).show();
//        }
//        else
//            Toast.makeText(getActivity(),"no",Toast.LENGTH_SHORT).show();



        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    private void startToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}