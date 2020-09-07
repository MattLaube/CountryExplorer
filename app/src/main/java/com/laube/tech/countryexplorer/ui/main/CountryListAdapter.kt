package com.laube.tech.countryexplorer.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laube.tech.countryexplorer.R
import com.laube.tech.countryexplorer.model.Country
import com.laube.tech.countryexplorer.util.loadSVG
import kotlinx.android.synthetic.main.country_item.view.*
import java.text.DecimalFormat

class CountryListAdapter (val countryList: ArrayList<Country>): RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>(){
    class CountryListViewHolder(var view: View) : RecyclerView.ViewHolder(view)
    private val popFormat = DecimalFormat("#,###",)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_item, parent, false)
        return CountryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.view.country_name.text = countryList[position].name
        holder.view.population_text.text = popFormat.format(countryList[position].population)
        holder.view.flag_image.loadSVG(countryList[position].flag )
    }

    override fun getItemCount(): Int {
       return countryList.size
    }

    fun updateCountryList(newCountry: List<Country>){
        countryList.clear()
        countryList.addAll(newCountry)
        notifyDataSetChanged()
    }
}