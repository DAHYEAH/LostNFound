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
import com.example.lostnfound.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText m_edit_name, m_edit_major, m_edit_number, m_edit_email, m_edit_pwd, m_edit_pwd2; //회원가입 입력필드
    private Button mBtnRegister; //회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("LostNFound");

        m_edit_name = findViewById(R.id.edit_name);
        m_edit_major = findViewById(R.id.edit_major);
        m_edit_number = findViewById(R.id.edit_number);
        m_edit_email = findViewById(R.id.edit_email);
        m_edit_pwd = findViewById(R.id.edit_pwd);
        m_edit_pwd2 = findViewById(R.id.edit_pwd2);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입 처리 시작
                String strName = m_edit_name.getText().toString();
                String strMajor = m_edit_major.getText().toString();
                String strNumber = m_edit_number.getText().toString();
                String strEmail = m_edit_email.getText().toString();
                String strPwd = m_edit_pwd.getText().toString();
                String strPwd2 = m_edit_pwd2.getText().toString();

                if(strName.length()==0){
                    startToast("이름을 입력해주세요");
                }
                if(strMajor.length()==0){
                    startToast("학과을 입력해주세요");
                }
                if(strNumber.length()==0){
                    startToast("학번을 입력해주세요");
                }
                if(strEmail.length()==0){
                    startToast("이메일을 입력해주세요");
                }
                else if(strPwd.length()==0){
                    startToast("비밀번호를 입력해주세요");
                }
                else if(strPwd2.length()==0){
                    startToast("비밀번호 다시입력을 입력해주세요");
                }
                else if(strPwd.equals(strPwd2)) {
                    //FirebaseAuth 진행
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                                UserAccount account = new UserAccount();
                                account.setIdToken(firebaseUser.getUid());
                                account.setName(strName);
                                account.setMajor(strMajor);
                                account.setNumber(strNumber);
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPwd);

                                // setValue : database에 insert(삽입) 행위
//                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
//                                //DB저장
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                db.collection("users").document(firebaseUser.getUid()).set(account)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                // 회원가입 성공 !!!
                                                startToast("회원가입에 성공하셨습니다.");
                                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish(); // 현재 액티비티 파괴
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                startToast("회원가입에 실패하셨습니다.1");
                                            }
                                        });

//                                startToast("회원가입에 성공하셨습니다.");
//                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish(); // 현재 액티비티 파괴
                            }
                            else {
                                startToast("회원가입에 실패하셨습니다.2");
                            }
                        }
                    });
                }
                else{
                    startToast("비밀번호가 틀렸습니다...!");
                }
            }
        });
    }
    private void startToast(String msg){Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();}
}