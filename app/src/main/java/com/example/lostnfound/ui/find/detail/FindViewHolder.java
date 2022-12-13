package com.example.lostnfound.ui.find.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnfound.R;

import java.util.ArrayList;

public class FindViewHolder extends RecyclerView.ViewHolder {
    public TextView view_title;
    public TextView view_type;
    public TextView view_memo;
    public TextView view_posttime, view_date, view_time;
    public ImageView view_picture;
    public CardView view_card;
    private ArrayList<FindArray> mDataset;;

    public FindViewHolder(View itemView) {
        super(itemView);

        view_title = (TextView) itemView.findViewById(R.id.title);
        view_type = (TextView) itemView.findViewById(R.id.type);
        view_memo = (TextView) itemView.findViewById(R.id.memo);
        view_posttime= (TextView) itemView.findViewById(R.id.posttime);
        view_date = (TextView) itemView.findViewById(R.id.date);
        view_time = (TextView) itemView.findViewById(R.id.time);
        view_picture = (ImageView) itemView.findViewById(R.id.picture);
        view_card = (CardView) itemView.findViewById(R.id.find_item_list);
    }
}
