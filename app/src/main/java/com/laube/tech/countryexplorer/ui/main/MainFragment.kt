package com.laube.tech.countryexplorer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.laube.tech.countryexplorer.R
import com.laube.tech.countryexplorer.util.IconAnimator
import it.carlom.stikkyheader.core.StikkyHeaderBuilder
import kotlinx.android.synthetic.main.list_header.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val countryListAdapter = CountryListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        country_list.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }


        var dividerItemDecoration = DividerItemDecoration(this.context, LinearLayout.VERTICAL)
        //var headerDecoration = HeaderDecoration()
        country_list.addItemDecoration(dividerItemDecoration)

        val animation : IconAnimator = IconAnimator(header)

        StikkyHeaderBuilder.stickTo(country_list)
            .setHeader(R.id.header, view as ViewGroup?)
          //  .animator(animation )
            .build()

        refresh_text.setOnClickListener{
            country_list.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.fetchFromRemote()
        }
        observeViewModel()

    }
    fun observeViewModel(){
        viewModel.currentCountries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                country_list.visibility = View.VISIBLE
                countryListAdapter.updateCountryList(countries)
            }
        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    country_list.visibility = View.GONE
                }
            }
        })
    }


}