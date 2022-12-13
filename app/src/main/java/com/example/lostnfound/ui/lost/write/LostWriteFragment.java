package com.example.lostnfound.ui.lost.write;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.lostnfound.R;
import com.example.lostnfound.activity.CameraActivity;
import com.example.lostnfound.activity.GalleryActivity;
import com.example.lostnfound.activity.MainActivity;
import com.example.lostnfound.databinding.FragmentLostWriteBinding;
import com.example.lostnfound.ui.lost.LostInfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LostWriteFragment extends Fragment {

    private FragmentLostWriteBinding binding;
    TextView lost_date_edit, lost_time_edit;
    EditText lost_title, edit_lost_type, lost_memo, lost_location;
    ImageButton lost_date_cal, lost_time_clock, lost_camera, lost_gallery;
    ImageView img_lost;
    Spinner spinner;
    Calendar myCalendar;
    Button btn_post;
    String[] items;
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private String profilePath;
    LostInfo lostInfo = new LostInfo();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LostWriteViewModel LostWriteViewModel =
                new ViewModelProvider(this).get(LostWriteViewModel.class);

        binding = FragmentLostWriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edit_lost_type = root.findViewById(R.id.lost_type_write);
        spinner = root.findViewById(R.id.lost_type);
        lost_date_edit = root.findViewById(R.id.lost_date_edit);
        lost_time_edit = root.findViewById(R.id.lost_time_edit);
        lost_date_cal = root.findViewById(R.id.lost_date_cal);
        lost_time_clock = root.findViewById(R.id.lost_time_clock);
        myCalendar = Calendar.getInstance();
        items = getResources().getStringArray(R.array.type_array);
        lost_camera = root.findViewById(R.id.lost_camera);
        lost_gallery = root.findViewById(R.id.lost_gallery);
        btn_post = root.findViewById(R.id.btn_post);
        img_lost = root.findViewById(R.id.img_lost);
        lost_title = root.findViewById(R.id.lost_title);
        lost_memo = root.findViewById(R.id.lost_memo);
        lost_location = root.findViewById(R.id.lost_location);

        // 분실물 종류 선택하는 스피너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    edit_lost_type.setText("");
                    edit_lost_type.setHint("분실물 종류");
                } else {
                    edit_lost_type.setInputType(InputType.TYPE_NULL);
                    edit_lost_type.setText(items[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edit_lost_type.setText("");
                edit_lost_type.setHint("선택해주세요");
            }
        });

        DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        lost_date_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        lost_time_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);                //한국시간 : +9
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "AM";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "PM";
                        }
                        // EditText에 출력할 형식 지정
                        lost_time_edit.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("분실추정 시간을 선택해주세요");
                mTimePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mTimePicker.show();
            }
        });

        lost_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        lost_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                startActivityForResult(intent, 0);
            }
        });


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String title = lost_title.getText().toString();
//                String type = edit_lost_type.getText().toString();
//                String date = lost_date_edit.getText().toString();
//                String time = lost_time_edit.getText().toString();
////                String location = m_edit_pwd.getText().toString();
//                String memo = lost_memo.getText().toString();
//                profileUpdate();
                toDB();

            }
        });


        return root;
    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2021/07/26
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        lost_date_edit.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                profilePath = data.getStringExtra("profilePath");
                Log.e("로그:", "profilePath" + profilePath);
//                Bitmap bmp = BitmapFactory.decodeFile(profilePath);
//                profileImageView.setImageBitmap(bmp);
//                Glide.with(this).load(profilePath).centerCrop().override(300).into(img_lost);
//                profileImageView.setImageResource(g);
//                Glide.with(getActivity()).load(downloaduri.toString()).centerCrop().override(300).into(img_lost);
                profileUpdate();
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
        final StorageReference mountainImagesRef = storageRef.child("LostPhotos/"+user.getUid()+"/lost.jpg");

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
                        mFirebaseAuth = FirebaseAuth.getInstance();
                        mDatabaseRef = FirebaseDatabase.getInstance().getReference("LostNFound");
                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                        lostInfo.setTitle(lost_title.getText().toString());
                        lostInfo.setType(edit_lost_type.getText().toString());
                        lostInfo.setDate(lost_date_edit.getText().toString());
                        lostInfo.setTime(lost_time_edit.getText().toString());
                        lostInfo.setMemo(lost_memo.getText().toString());
                        lostInfo.setLocation(lost_location.getText().toString());
                        lostInfo.setPhotoUrl(downloaduri.toString());
                        Glide.with(getActivity()).load(downloaduri.toString()).centerCrop().override(300).into(img_lost);
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)+9;                //한국시간 : +9
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        lostInfo.setPostTime((Calendar.YEAR+2021)+"_"+(Calendar.MONTH+4)+"_"+(Calendar.DAY_OF_MONTH+11)+"_"+Integer.toString(hour-9)+"_"+Integer.toString(minute));

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
    private void startToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void toDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("lost_post").document(lostInfo.getPostTime()).set(lostInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // 포스팅 성공 !!!
                        startToast("포스팅 하였습니다.");
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish(); // 현재 액티비티 파괴
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        startToast("실패하였습니다.");
                    }
                });
    }
}
