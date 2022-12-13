package com.example.lostnfound.ui.lost.detail;

public class LostArray {
    public String title, type, memo, postTime, date, time, picture, location;

    public LostArray(String str_title, String str_type, String str_memo, String str_postTime, String date, String time, String picture, String location) {
        this.title = str_title;
        this.type = str_type;
        this.memo = str_memo;
        this.postTime = str_postTime;
        this.date = date;
        this.time = time;
        this.picture = picture;
        this.location = location;
    }
}
