package com.example.lostnfound.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostnfound.R;
import com.example.lostnfound.activity.FindDetailActivity;
import com.example.lostnfound.ui.find.detail.FindArray;
import com.example.lostnfound.ui.find.detail.FindViewHolder;

import java.util.ArrayList;

public class FindAdapter extends RecyclerView.Adapter<FindViewHolder> {
    private ArrayList<FindArray> mDataset;
    private Context mContext;

    public FindAdapter(Context mContext, ArrayList<FindArray> myDataset) {
        mDataset = myDataset;
        this.mContext = mContext;
    }
    @Override
    public FindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_list, parent, false);  // recyclerview
        Log.d("hwang", "onCreateViewHolder");
        FindViewHolder vh = new FindViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FindViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.view_title.setText(mDataset.get(position).title);
        holder.view_type.setText(mDataset.get(position).type);
        holder.view_memo.setText(mDataset.get(position).memo);
        holder.view_posttime.setText(mDataset.get(position).posttime);
        holder.view_date.setText(mDataset.get(position).date);
        holder.view_time.setText(mDataset.get(position).time);
//        holder.view_picture.setImageURI(Uri.parse(mDataset.get(position).picture));
        Log.e("a",mDataset.get(position).picture);
        Glide.with(mContext).load(mDataset.get(position).picture).centerCrop().override(300).into(holder.view_picture);
        final Context mycontext = holder.itemView.getContext();

        holder.view_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FindDetailActivity.class);
                intent.putExtra("lat",mDataset.get(position).lat);
                intent.putExtra("lng",mDataset.get(position).lng);
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
