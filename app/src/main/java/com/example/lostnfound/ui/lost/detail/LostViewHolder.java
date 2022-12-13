package com.example.lostnfound.ui.lost.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostnfound.R;

import java.util.ArrayList;

public class LostViewHolder extends RecyclerView.ViewHolder {
    public TextView view_title;
    public TextView view_type;
    public TextView view_memo;
    public TextView view_posttime;
    public TextView view_time;
    public TextView view_date;
    public ImageView view_picture;
    public TextView view_location;
    public CardView view_card;
    private ArrayList<LostArray> mDataset;;

    public LostViewHolder(View itemView) {
        super(itemView);

        view_title = (TextView) itemView.findViewById(R.id.title);
        view_type = (TextView) itemView.findViewById(R.id.type);
        view_memo = (TextView) itemView.findViewById(R.id.memo);
        view_card = (CardView) itemView.findViewById(R.id.lost_item_list);
        view_posttime= (TextView) itemView.findViewById(R.id.postTime);
        view_date = (TextView) itemView.findViewById(R.id.date);
        view_time = (TextView) itemView.findViewById(R.id.time);
        view_picture = (ImageView) itemView.findViewById(R.id.picture);
        view_location = (TextView) itemView.findViewById(R.id.location);

    }
}
