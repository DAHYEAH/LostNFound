package com.example.lostnfound.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.lostnfound.R;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class InfoWindow extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;         // 파이어베이스 인증
    private DatabaseReference mDatabaseRef;     // 실시간 데이터베이스

    TextView posttime, title, type, memo, time, date;
    ImageView img;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_window);

        posttime = (TextView)findViewById(R.id.posttime);
        title = (TextView)findViewById(R.id.title);
        type = (TextView) findViewById(R.id.find_type);
        memo = (TextView) findViewById(R.id.find_memo);
        time = (TextView) findViewById(R.id.find_time);
        date = (TextView) findViewById(R.id.find_date);
        img = (ImageView)findViewById(R.id.img_find);

        Intent intent = getIntent();

        posttime.setText(intent.getStringExtra("posttime"));

        db.collection("find_post")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                        Log.e("CITY", document.getId() + " => " + document.getData());
                            MarkerOptions myMarker;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getId().equals(intent.getStringExtra("posttime"))){
                                    title.setText(document.getData().get("title").toString());
                                    type.setText(document.getData().get("type").toString());
                                    memo.setText(document.getData().get("memo").toString());
                                    time.setText(document.getData().get("time").toString());
                                    date.setText(document.getData().get("date").toString());
                                    type.setText(document.getData().get("type").toString());
                                    Glide.with(getApplication()).load(document.getData().get("photoUrl")).centerCrop().override(300).into(img);
                                    Log.e("yes",document.getId()+"   "+intent.getStringExtra("posttime"));
                                }else{
                                    Log.e("no",document.getId()+"   "+intent.getStringExtra("posttime"));
                                }

                            }
                        }
                        else {
                            Log.e("CITY", "Error getting documents: ", task.getException());
                        }
                    }
                });



    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();}
}