package com.weiwei.beats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.weiwei.beats.databinding.FragmentWeatherBinding
import com.weiwei.beats.weather.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)

//get the viewModel
        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        //configure the layout data binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //configure the spinner
        // data source comes from an array
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, viewModel.cities_ch)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        //item selection handler
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPosition = parent?.selectedItemPosition
                selectedPosition?.let {
                    if (it != 0) { //not the first item (hint text only)
                        //send an Internet request to the weather website
                        viewModel.sendRetrofitRequest(viewModel.cities_en[it])

                        //visibility
                        binding.text1.visibility = View.VISIBLE
                        binding.text2.visibility = View.VISIBLE
                        binding.text3.visibility = View.VISIBLE
                        binding.text4.visibility = View.VISIBLE
                        binding.text5.visibility = View.VISIBLE
                    }
                    else {
                        viewModel.selectedCityWeather.value = null

                        binding.text1.visibility = View.INVISIBLE
                        binding.text2.visibility = View.INVISIBLE
                        binding.text3.visibility = View.INVISIBLE
                        binding.text4.visibility = View.INVISIBLE
                        binding.text5.visibility = View.INVISIBLE
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        return binding.root
    }

}