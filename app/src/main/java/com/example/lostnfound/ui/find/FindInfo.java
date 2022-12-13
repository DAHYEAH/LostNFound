package com.example.lostnfound.ui.find;

public class FindInfo {
    private String title;
    private String type;
    private String date;
    private String time;
    private String lat;
    private String lng;
    private String memo;
    private String photoUrl;
    private String postTime;

    public FindInfo(){ }

    public FindInfo(String title, String type, String date, String time, String lat, String lng, String memo, String photoUrl, String postTime){
        this.title = title;
        this.type = type;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lng = lng;
        this.memo = memo;
        this.photoUrl = photoUrl;
        this.postTime = postTime;
    }
    public String getTitle(){return this.title;}
    public void setTitle(String title) {this.title = title;}

    public String getType(){return this.type;}
    public void setType(String type) {this.type = type;}

    public String getDate(){return this.date;}
    public void setDate(String date) {this.date = date;}

    public String getTime(){return this.time;}
    public void setTime(String time) {this.time = time;}

    public String getLat(){return this.lat;}
    public void setLat(String lat) {this.lat = lat;}

    public String getLng(){return this.lng;}
    public void setLng(String lng) {this.lng = lng;}

    public String getMemo(){return this.memo;}
    public void setMemo(String memo) {this.memo = memo;}

    public String getPhotoUrl(){return this.photoUrl;}
    public void setPhotoUrl(String photoUrl) {this.photoUrl = photoUrl;}

    public String getPostTime(){return this.postTime;}
    public void setPostTime(String postTime) {this.postTime = postTime;}
}
