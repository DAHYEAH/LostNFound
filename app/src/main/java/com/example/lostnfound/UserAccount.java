package com.example.lostnfound;

/**
 * 사용자 계저 정보 모델 클래스
 */

public class UserAccount {
    private String idToken;     // Firebase Uid (교유 토근정보)
//    private String photourl;
    private String name;
    private String major;
    private String number;
    private String emailId;     // 이메일 아이디
    private String password;    //비밀번호


    public UserAccount(){ }

    public void setAll(String idToken, String name, String major, String number, String emailId, String password){
        this.idToken = idToken;
//        this.photourl = photourl;
        this.name = name;
        this.major = major;
        this.number = number;
        this.emailId = emailId;
        this.password = password;
    }
    public String getIdToken() { return idToken; }
    public void setIdToken(String idToken) { this.idToken = idToken; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

//    public String getPhotoUrl() { return photourl; }
//    public void setPhotoUrl(String photourl) { this.photourl = photourl; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
