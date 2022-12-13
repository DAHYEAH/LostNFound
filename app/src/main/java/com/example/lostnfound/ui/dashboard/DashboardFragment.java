package com.example.lostnfound.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.lostnfound.R;
import com.example.lostnfound.activity.CameraActivity;
import com.example.lostnfound.activity.GalleryActivity;
import com.example.lostnfound.activity.LoginActivity;
import com.example.lostnfound.databinding.FragmentDashboardBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private FirebaseAuth mFirebaseAuth;
    private ImageView profileImageView;
    private String profilePath;
    private Button camera, gallery, btn_logout, btn_change;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mFirebaseAuth = FirebaseAuth.getInstance();

        profileImageView = root.findViewById(R.id.profileImageView);
        camera = root.findViewById(R.id.camera);
        gallery = root.findViewById(R.id.gallery);
        btn_logout = root.findViewById(R.id.btn_logout);
        btn_change = root.findViewById(R.id.btn_change);

//        profileImageView.setImageResource(R.drawable.ic_launcher_foreground);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://lostnfound-6cf90.appspot.com");
        StorageReference storageRef = storage.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        storageRef.child("users/"+user.getUid()+"/profileImage.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(getContext()).load(uri).centerCrop().override(300).into(profileImageView);

//                Glide.with(getContext())
//                        .load(uri)
//                        .into(profileImageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
//                   if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){
////                       ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
//                   }
//                   else{
//                       Toast.makeText(getActivity(), "권한을 허용해 주세요1", Toast.LENGTH_SHORT).show();
//                   }
//                }
//                else {
//                    Intent intent = new Intent(getActivity(), GalleryActivity.class);
//                    startActivityForResult(intent, 0);
//                }
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 로그아웃 하기
                mFirebaseAuth.signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();// 현재 액티비티 파괴
            }
        });

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate();
                Toast.makeText(getContext(),"수정되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                profilePath = data.getStringExtra("profilePath");
                Log.e("로그dash:", "profilePath" + profilePath);
//                Bitmap bmp = BitmapFactory.decodeFile(profilePath);
//                profileImageView.setImageBitmap(bmp);
                Glide.with(this).load(profilePath).centerCrop().override(300).into(profileImageView);
//                profileImageView.setImageResource(g);
            }
        }
        else{
            Log.e("로그:","requestcode"+requestCode);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void profileUpdate(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final StorageReference mountainImagesRef = storageRef.child("users/"+user.getUid()+"/profileImage.jpg");

        try {
            InputStream stream = new FileInputStream(new File(profilePath));

            UploadTask uploadTask= mountainImagesRef.putStream(stream);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return mountainImagesRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloaduri = task.getResult();
                        Log.e("성공","성공: "+downloaduri);
                    }
                    else{
                        Log.e("로그","실패");
                    }
                }
            });
        }catch (FileNotFoundException e){
            Log.e("로그", "에러: "+e.toString());
        }
    }

}