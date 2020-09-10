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
import kotlinx.android.synthetic.main.main_fragment.*
import me.everything.android.ui.overscroll.IOverScrollDecor
import me.everything.android.ui.overscroll.IOverScrollState
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter


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

        country_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }
        val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayout.VERTICAL)
        country_list.addItemDecoration(dividerItemDecoration)
        refresh_text.setOnClickListener {
            country_list.visibility = View.GONE
            list_Error.visibility = View.GONE
            loading_View.visibility = View.VISIBLE
            viewModel.fetchFromRemote()
        }


        val mVertOverScrollEffect: IOverScrollDecor? = VerticalOverScrollBounceEffectDecorator(
            RecyclerViewOverScrollDecorAdapter(
                country_list
            )
        )

        // Here we use the OverScroll library to simulate the over scroll on iOS
        // the over scroll changes the size of the header and layout
        mVertOverScrollEffect?.setOverScrollUpdateListener { decor, state, offset ->
            if(state == IOverScrollState.STATE_IDLE){

            }else {

            }

        }
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.currentCountries.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                country_list.visibility = View.VISIBLE
                countryListAdapter.updateCountryList(countries)
            }
        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                list_Error.visibility = if (it) View.VISIBLE else View.GONE
                list_Error.text = getString(R.string.an_error_occured_while_loading_data)
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loading_View.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_Error.visibility = View.GONE
                    country_list.visibility = View.GONE
                }
            }
        })
    }


}

