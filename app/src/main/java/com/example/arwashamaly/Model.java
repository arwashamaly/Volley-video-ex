package com.example.arwashamaly;

import java.util.ArrayList;

public class Model {
    ArrayList<Video> videoArrayList;

    public Model(ArrayList<Video> videoArrayList) {
        this.videoArrayList = videoArrayList;
    }
}
class Video{
   String video_id;
   String discription;
   String video_cover_image;

    public Video(String video_id, String discription, String video_cover_image) {
        this.video_id = video_id;
        this.discription = discription;
        this.video_cover_image = video_cover_image;
    }
}
