package com.example.apprestaurantportofoliio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.apprestaurantportofoliio.model.Model;
import com.example.apprestaurantportofoliio.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ListViewHolder>{

    private List<Model> dataList;
    private List<Model> filteredDataList;
    private OnItemClickListener mListener;
    private Context mContext;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adapter(Context mContext, List<Model> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.filteredDataList = dataList;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_list, parent, false);
        return new ListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.tv_title.setText(filteredDataList.get(position).getName());
        holder.tv_loc.setText(filteredDataList.get(position).getLocation());
        holder.tv_rate.setText(String.valueOf(filteredDataList.get(position).getRate()));
        Picasso.get()
                .load(dataList.get(position).getImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.img_list);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? filteredDataList.size() : 0;
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
            relativeLayout = itemView.findViewById(R.id.rv_layout_list);

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

    public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String Key = charSequence.toString();
                if(Key.isEmpty()){
                    filteredDataList = dataList;
                }
                else{

                    List<Model> lstFiltered = new ArrayList<>();
                    for( Model row: dataList){
                        if(row.getName().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);

                        }
                    }

                    filteredDataList = lstFiltered;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredDataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredDataList = (List<Model>)filterResults.values;
                notifyDataSetChanged();

            }
        };

    }



}
