package com.example.lostnfound.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostnfound.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PasswordResetActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;         // 파이어베이스 인증
    private DatabaseReference mDatabaseRef;     // 실시간 데이터베이스
    private EditText m_edit_email;  // 로그인 입력필드
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("LostNFound");

        m_edit_email = findViewById(R.id.edit_email);

        Button btn_login = findViewById(R.id.btn_send);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 요청
                String strEmail = m_edit_email.getText().toString();

                if(strEmail.length() > 0) {
                    mFirebaseAuth.sendPasswordResetEmail(strEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // 이메일보내기 성공 !!!
                                        startToast("이메일을 성공적으로 보냈습니다.");
//                                        Intent intent = new Intent(PasswordResetActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish(); // 현재 액티비티 파괴
                                    } else {
                                        startToast("이메일 보내기 실패");
                                    }
                                }
                            });
                }
                else{
                    startToast("이메일을 입력해 주세요.");
                }
            }
        });
    }
    private void startToast(String msg){Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();}
}
