package com.example.covid19update;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter2 extends ArrayAdapter<LocalModel> {

    private Context context;
    private List<LocalModel> localModelList;
    public MyCustomAdapter2(Context context, List<LocalModel> localModelList) {
        super(context, R.layout.custom_area, localModelList);

        this.context = context;
        this.localModelList = localModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_area, null, true);
        TextView areaname = view.findViewById(R.id.areaname);
        TextView areaamount = view.findViewById(R.id.areaamount);

        areaname.setText(localModelList.get(position).getState());
        areaamount.setText(localModelList.get(position).getCases());
        return view;
    }

    @Override
    public int getCount() {
        return localModelList.size();
    }

    @Nullable
    @Override
    public LocalModel getItem(int position) {
        return localModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
