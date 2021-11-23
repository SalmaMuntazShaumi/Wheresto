package com.example.apprestaurantportofoliio;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ListViewHolder>{

    private List<Model> teamList;
    private OnItemClickListener mListener1;
    private Context mContext;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener1 = listener;
    }

    public BookmarkAdapter(Context mContext, List<Model> teamList) {
        this.mContext = mContext;
        this.teamList = teamList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.bm_list, parent, false);
        return new ListViewHolder(view, mListener1);
    }


    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.tv_title.setText(teamList.get(position).getName());
        holder.tv_loc.setText(teamList.get(position).getCity());
        holder.tv_rate.setText(String.valueOf(teamList.get(position).getRating()));
        Picasso.get()
                .load(teamList.get(position).getPictureId())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_list);
    }

    @Override
    public int getItemCount() {
        return (teamList != null) ? teamList.size() : 0;
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_loc;
        private TextView tv_rate;
        private ImageView img_list;
        private RelativeLayout relativeLayout;

        public ListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title_list);
            tv_loc = itemView.findViewById(R.id.location);
            tv_rate = itemView.findViewById(R.id.tv_rate);
            img_list = itemView.findViewById(R.id.img_list);
            relativeLayout = itemView.findViewById(R.id.rv_layout_list_bookmark);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
