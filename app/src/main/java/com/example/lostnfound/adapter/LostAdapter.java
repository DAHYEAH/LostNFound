package com.example.lostnfound.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostnfound.R;
import com.example.lostnfound.activity.MainActivity;
import com.example.lostnfound.ui.lost.detail.LostArray;
import com.example.lostnfound.ui.lost.detail.LostViewHolder;

import java.util.ArrayList;

public class LostAdapter extends RecyclerView.Adapter<LostViewHolder> {
    private ArrayList<LostArray> mDataset;
    private Context mContext;

    public LostAdapter(Context mContext, ArrayList<LostArray> myDataset) {
        mDataset = myDataset;
        this.mContext = mContext;
    }
    @Override
    public LostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lost_list, parent, false);  // recyclerview
        Log.d("hwang", "onCreateViewHolder");
        LostViewHolder vh = new LostViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(LostViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.view_title.setText(mDataset.get(position).title);
        holder.view_type.setText(mDataset.get(position).type);
        holder.view_memo.setText(mDataset.get(position).memo);
        holder.view_date.setText(mDataset.get(position).date);
        holder.view_time.setText(mDataset.get(position).time);
        holder.view_location.setText(mDataset.get(position).location);
        holder.view_posttime.setText(mDataset.get(position).postTime);
        Glide.with(mContext).load(mDataset.get(position).picture).centerCrop().override(300).into(holder.view_picture);
        final Context mycontext = holder.itemView.getContext();

        holder.view_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Toast.makeText(mycontext, mDataset.get(position).title+"", Toast.LENGTH_SHORT).show();
                MainActivity mainActivity = (MainActivity)mycontext;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
