package com.example.lostnfound.ui.lost.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnfound.R;
import com.example.lostnfound.activity.MainActivity;
import com.example.lostnfound.adapter.LostAdapter;
import com.example.lostnfound.databinding.FragmentLostBinding;
import com.example.lostnfound.ui.lost.detail.LostArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LostFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentLostBinding binding;
    MainActivity mainActivity;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LostViewModel LostViewModel =
                new ViewModelProvider(this).get(LostViewModel.class);

        binding = FragmentLostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mRecyclerView = root.findViewById(R.id.lost_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ImageButton btn_lost_write = root.findViewById(R.id.btn_lost_write);

        btn_lost_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.moveToLostWrite();
            }
        });

        db.collection("lost_post")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                        Log.e("CITY", document.getId() + " => " + document.getData());
                            mRecyclerView.setVerticalScrollBarEnabled(true);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(getActivity()).getOrientation());
                            mRecyclerView.addItemDecoration(dividerItemDecoration);
                            ArrayList<LostArray> myDataset = new ArrayList<LostArray>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                myDataset.add(new LostArray("제목 : "+document.getData().get("title"),
                                        "종류 : "+document.getData().get("type"),
                                        "세부정보 : "+document.getData().get("memo"),
                                        "게시날짜 : "+document.getId(),
                                        "일시"+document.getData().get("date"),
                                        "시간"+document.getData().get("time"),
                                        document.getData().get("photoUrl").toString(),
                                        document.getData().get("location").toString()));
                                Log.e("뭘까", (String) document.getData().get("photoUrl"));
                            }
                            mAdapter = new LostAdapter(getActivity(), myDataset);
                            mRecyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d("CITY", "Error getting documents: ", task.getException());
                        }
                    }
                });
        // Recycler view adapter
//        LostAdapter adapter = new LostAdapter(myDataset);
//
        // Recycler view item click event 처리
//        mAdapter.SetOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(View a_view, int a_position) {
//                final LostArray item = myDataset.get(a_position);
//                Toast.makeText(getContext(), item.title + " Click event", Toast.LENGTH_SHORT).show();
//            }
//        });
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textUsername::setText);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textEmail::setText);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}

