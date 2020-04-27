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

public class MyCustomAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryModelslist;
    private List<CountryModel> countryModelslistFiltered;
    public MyCustomAdapter(Context context, List<CountryModel> countryModelslist) {
        super(context, R.layout.custom_listview, countryModelslist);

        this.context = context;
        this.countryModelslist = countryModelslist;
        this.countryModelslistFiltered = countryModelslist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listview, null, true);
        TextView countryname = view.findViewById(R.id.txtcountryname);
        ImageView flagimage = view.findViewById(R.id.imagecountry);

        countryname.setText(countryModelslistFiltered.get(position).getCountry());
        Glide.with(context).load(countryModelslistFiltered.get(position).getFlag()).into(flagimage);
        return view;
    }

    @Override
    public int getCount() {
        return countryModelslistFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelslistFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModelslist.size();
                    filterResults.values = countryModelslist;

                }else{
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModel itemsModel:countryModelslist){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModelslistFiltered = (List<CountryModel>) results.values;
                CountryActivity.countryModelList = (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
