package com.covid_19.Adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.covid_19.R;
import com.covid_19.fregments.DetailsFragment;
import com.covid_19.model.Constants;
import com.covid_19.model.CovidWithCountries;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    public static ArrayList<CovidWithCountries> countries;
    public static ArrayList<CovidWithCountries> listFilteredCountries;

    private FragmentActivity fragmentActivity;
   private  CountriesFilter countriesFilter;
    public CountryAdapter(ArrayList<CovidWithCountries> countries, FragmentActivity homeActivity) {
        this.countries = countries;
        this.listFilteredCountries = countries;
        this.fragmentActivity = homeActivity;
        countriesFilter=new CountriesFilter();
        ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCountryName.setText(listFilteredCountries.get(position).getCountry());
        Glide.with(holder.imgCountry)
                .load(listFilteredCountries.get(position).getCountryInfo().getFlag()).into(holder.imgCountry);
    }

    @Override
    public int getItemCount() {
        return listFilteredCountries.size();
    }


    public Filter getFilter(){
        return countriesFilter;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCountry;
        TextView tvCountryName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCountryName = itemView.findViewById(R.id.textView);
            imgCountry = itemView.findViewById(R.id.imgCountry);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("ttt", getAdapterPosition() + "positon");
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.POSITION, getAdapterPosition());
                   DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(bundle);
                    fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_home, detailsFragment)
                           .addToBackStack("stacks").commit();

                }
            });

        }


    }



 class  CountriesFilter extends Filter {

     @Override
     protected FilterResults performFiltering(CharSequence charSequence) {
         String searchQuery=charSequence.toString();
          FilterResults filterResults=new FilterResults();
         List<CovidWithCountries>tempList=new ArrayList<>();
           for (CovidWithCountries covidWithCountries :countries){
                  if (covidWithCountries.getCountry().contains(searchQuery)){
                       tempList.add(covidWithCountries);
                  }
           }
           filterResults.values=tempList;
           filterResults.count=tempList.size();


         return filterResults;
     }

     @Override
     protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
         listFilteredCountries= (ArrayList<CovidWithCountries>) filterResults.values;
         notifyDataSetChanged();

     }
 }
}
