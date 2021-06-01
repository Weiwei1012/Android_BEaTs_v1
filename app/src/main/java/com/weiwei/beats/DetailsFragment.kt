package com.weiwei.beats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.weiwei.beats.database.RestaurantDatabase
import com.weiwei.beats.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

//        //retrieve the database dao
//        val application = requireNotNull(this.activity).application
//        val dataSource = RestaurantDatabase.getInstance(application).restaurantDatabaseDao

        //shared viewmodel with the activity
        viewModel = ViewModelProvider(requireActivity(),
            MyViewModelFactory(requireActivity().application)).get(MyViewModel::class.java)

        //retrieve the passed argument (selected scene's id from the recyclerview)
        val args = DetailsFragmentArgs.fromBundle(requireArguments())
        viewModel.getScene(args.rawId)

        //set an observer to the liveData and hence update the UI
        viewModel.selectedScene.observe(viewLifecycleOwner, Observer {
            //do data binding in the layout
            binding.scene = it
        })



        binding.mapButton.setOnClickListener {
            val passedScene = viewModel.selectedScene.value!!
            it.findNavController()
                .navigate(DetailsFragmentDirections.actionDetailsFragmentToMapsFragment(passedScene.name, passedScene.address))
        }

//        binding.mapButton.setOnClickListener {
//            val passedScene = viewModel.selectedScene.value!!
//            it.findNavController()
//                .navigate(DetailsFragmentDirections.actionDetailsFragmentToMapFragment(passedScene.name, passedScene.address))
//        }

        return binding.root
    }


}