package com.example.arwashamaly;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arwashamaly.databinding.VideoItemBinding;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    ArrayList<Video> videoArrayList ;
    Context context;

    public VideoAdapter(ArrayList<Video> videoArrayList, Context context) {
        this.videoArrayList = videoArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemBinding binding = VideoItemBinding.inflate(LayoutInflater.from(parent.getContext())
        ,parent,false);
        return new VideoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        Video video=videoArrayList.get(position);
        holder.tvVideoId.setText(video.video_id);
        holder.tvDiscription.setText(video.discription);
        Glide.with(context).load(video.video_cover_image).into(holder.imgVideoCover);
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder {
        TextView tvVideoId , tvDiscription;
        ImageView imgVideoCover;
        public VideoHolder(@NonNull VideoItemBinding binding) {
            super(binding.getRoot());
            tvVideoId=binding.tvVideoId;
            tvDiscription=binding.tvDiscription;
            imgVideoCover =binding.imgVideoCover;
        }

    }
}
