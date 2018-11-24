package com.example.zizoj.retrofitfirstapp.Utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zizoj.retrofitfirstapp.Network.model.Forecastday;
import com.example.zizoj.retrofitfirstapp.R;

import java.util.ArrayList;
import java.util.List;

public class ForecastingListItemAdapter extends RecyclerView.Adapter<ForecastingListItemAdapter.HolderView>{

    List<Forecastday> mForcastdayList = new ArrayList<>();

    public ForecastingListItemAdapter(List<Forecastday> ForcastdayList){

        this.mForcastdayList = ForcastdayList;

    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.forcasting_layout_item,parent,false);

        HolderView view = new HolderView(layout);

        return view;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {

        holder.TempDate.setText(mForcastdayList.get(position).getDate());
        holder.TempMin.setText(mForcastdayList.get(position).getDay().getMintempC().toString() + (char) 0x00B0);
        holder.TempMax.setText(mForcastdayList.get(position).getDay().getMaxtempC().toString() + (char) 0x00B0);

        String ImageURL = mForcastdayList.get(position).getDay().getCondition().getIcon();
        UniversalImageLoader.setImage("http:"+ ImageURL , holder.WeatherIcon , null , "");
    }

    @Override
    public int getItemCount() {
        return mForcastdayList.size();
    }

    class HolderView extends RecyclerView.ViewHolder {

        TextView TempDate , TempMin , TempMax ;

        ImageView WeatherIcon;

        public HolderView(View itemView) {
            super(itemView);

            TempDate = itemView.findViewById(R.id.WeatherforcastTime);
            TempMin =itemView.findViewById(R.id.WeatherforcastTempMin);
            TempMax = itemView.findViewById(R.id.WeatherforcastTempMax);

            WeatherIcon = itemView.findViewById(R.id.Forcastin_Weather_Icon);
        }
    }
}
