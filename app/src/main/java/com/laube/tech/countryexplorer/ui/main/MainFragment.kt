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

    var startTextSize: Float = 30f
    var startLayoutHeight: Int = 80
    var linearLayout = ViewGroup.LayoutParams(0, startLayoutHeight)
    private lateinit var viewModel: MainViewModel
    private val countryListAdapter = CountryListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        country_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countryListAdapter
        }
        val dividerItemDecoration = DividerItemDecoration(this.context, LinearLayout.VERTICAL)
        country_list.addItemDecoration(dividerItemDecoration)
        refresh_text.setOnClickListener {
            country_list.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.fetchFromRemote()
        }

        header_text.textSize = startTextSize
        linearLayout = header_layout.layoutParams
        startLayoutHeight = linearLayout.height
        val mVertOverScrollEffect: IOverScrollDecor? = VerticalOverScrollBounceEffectDecorator(
            RecyclerViewOverScrollDecorAdapter(
                country_list
            )
        )

        mVertOverScrollEffect?.setOverScrollUpdateListener { decor, state, offset ->

            if(state == IOverScrollState.STATE_IDLE){
                header_text.textSize = startTextSize
                linearLayout.height = startLayoutHeight

            }else {
                var maxSize = (startTextSize + (offset+ 1 )/ 10)

                header_text.textSize = (startTextSize + (offset+ 1 )/ 10)
                linearLayout.height = startLayoutHeight + offset.toInt()
            }
            if (header_layout != null) {
               // header_layout.layoutParams = linearLayout
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
                listError.visibility = if (it) View.VISIBLE else View.GONE
                listError.text = getString(R.string.an_error_occured_while_loading_data)
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

