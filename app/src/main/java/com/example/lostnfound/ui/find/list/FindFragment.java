package com.example.lostnfound.ui.find.list;

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
import com.example.lostnfound.adapter.FindAdapter;
import com.example.lostnfound.databinding.FragmentFindBinding;
import com.example.lostnfound.ui.find.detail.FindArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FindFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FragmentFindBinding binding;
    private DatabaseReference mDatabaseRef; // 데이터베이스
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    MainActivity mainActivity;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FindViewModel FindViewModel =
                new ViewModelProvider(this).get(FindViewModel.class);

        binding = FragmentFindBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

//        final TextView textinformation = binding.textLost;
//        final TextView textUsername = binding.textUsername;
//        final TextView textEmail = binding.textEmail;
//        LostViewModel.getText().observe(getViewLifecycleOwner(), textinformation::setText);
        mRecyclerView = root.findViewById(R.id.find_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ImageButton btn_find_write = root.findViewById(R.id.btn_find_write);






//        mDatabaseRef.child("find_post").child(user.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                FindInfo findInfo = snapshot.getValue(FindInfo.class);
//
//                Log.e("HIIIIIIIIIIIIIII",findInfo.getTitle());
//                myDataset.add(new FindArray("title: ", "type: ", "memo: "));
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

    db.collection("find_post")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
//                        Log.e("CITY", document.getId() + " => " + document.getData());
                        mRecyclerView.setVerticalScrollBarEnabled(true);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(getActivity()).getOrientation());
                        mRecyclerView.addItemDecoration(dividerItemDecoration);
                        ArrayList<FindArray> myDataset = new ArrayList<FindArray>();
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            myDataset.add(new FindArray("제목 : "+document.getData().get("title"),
                                    "종류 : "+document.getData().get("type"),
                                    "세부정보 : "+document.getData().get("memo"),
                                    "게시날짜 : "+document.getId(),
                                    "일시"+document.getData().get("date"),
                                    "시간"+document.getData().get("time"),
                                    document.getData().get("photoUrl").toString(),
                                    document.getData().get("lat").toString(),
                                    document.getData().get("lng").toString()));
                            Log.e("뭘까", (String) document.getData().get("photoUrl"));
                        }
                        mAdapter = new FindAdapter(getActivity(), myDataset);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        Log.d("CITY", "Error getting documents: ", task.getException());
                    }
                }
            });
//        //CollectionReference 는 파이어스토어의 컬렉션을 참조하는 객체다.
//        CollectionReference productRef = db.collection("find_post");
//        //get()을 통해서 해당 컬렉션의 정보를 가져온다.
//        productRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                //작업이 성공적으로 마쳤을때
//                if (task.isSuccessful()) {
//                    //컬렉션 아래에 있는 모든 정보를 가져온다.
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        //document.getData() or document.getId() 등등 여러 방법으로
//                        //데이터를 가져올 수 있다.
//
//                        Log.e("Collection", String.valueOf(document.getId()));
////                        Log.e("HIIIIIIIIIIIIIII",findInfo.getTitle());
//                        mRecyclerView.setVerticalScrollBarEnabled(true);
//                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(getActivity()).getOrientation());
//                        mRecyclerView.addItemDecoration(dividerItemDecoration);
//                        ArrayList<FindArray> myDataset = new ArrayList<FindArray>();
//                        myDataset.add(new FindArray("title: "+document.getId(), "type: ", "memo: "));
//                        mAdapter = new FindAdapter(getActivity(), myDataset);
//                        mRecyclerView.setAdapter(mAdapter);
//                    }
//                    //그렇지 않을때
//                } else {
//                    Log.e("Collection", "hmm..");
//                }
//            }
//        });

//        Log.e("HIIIIIIIIIIIIIII",user.getUid());
//        DocumentReference docRef = db.collection("find_post").document(user.getUid());
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        FindInfo findInfo = document.toObject(FindInfo.class);
//
//                        Log.e("HIIIIIIIIIIIIIII",findInfo.getTitle());
//                        mRecyclerView.setVerticalScrollBarEnabled(true);
//                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(getActivity()).getOrientation());
//                        mRecyclerView.addItemDecoration(dividerItemDecoration);
//                        ArrayList<FindArray> myDataset = new ArrayList<FindArray>();
//                        myDataset.add(new FindArray("title: "+findInfo.getTitle(), "type: ", "memo: "));
//                        mAdapter = new FindAdapter(getActivity(), myDataset);
//                        mRecyclerView.setAdapter(mAdapter);
//
////                        Log.d("a", "DocumentSnapshot data: " + document.getData());
//                    } else {
//                        Log.d("A", "No such document");
//                    }
//                } else {
//                    Log.d("b", "get failed with ", task.getException());
//                }
//            }
//        });
//        myDataset.add(new FindArray("title2: ", "type2: ", "memo2: "));
//        myDataset.add(new FindArray("title3: ", "type3: ", "memo3: "));
//        myDataset.add(new FindArray("title4: ", "type4: ", "memo4: "));
//        myDataset.add(new FindArray("title5: ", "type5: ", "memo5: "));

        btn_find_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.moveToFindWrite();
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
