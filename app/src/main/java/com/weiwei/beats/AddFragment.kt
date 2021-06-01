package com.weiwei.beats

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.weiwei.beats.database.Restaurant
import androidx.lifecycle.Observer
import com.weiwei.beats.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: MyViewModel
    private var editMode = false
    private var newRestaurant = Restaurant("", "", "",0, "")
    val PICKUPIMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

//        //retrieve the database dao
//        val application = requireNotNull(this.activity).application
//        val dataSource = RestaurantDatabase.getInstance(application).restaurantDatabaseDao

        //shared viewmodel with the activity
        viewModel = ViewModelProvider(requireActivity(),
            MyViewModelFactory(requireActivity().application)).get(MyViewModel::class.java)

        //retrieve the passed argument (selected scene's id or 0 (add data))
        val args = AddFragmentArgs.fromBundle(requireArguments())
        editMode = (args.rawId != 0L)
        if (editMode) { //go editing the item
            viewModel.getScene(args.rawId)
            loadData()
        }
        else
            viewModel.selectedScene.value = null

        //set an observer to the liveData and hence update the UI
        viewModel.selectedScene.observe(viewLifecycleOwner, Observer {
            //do data binding in the layout
            binding.restaurant = it
        })

        //get input focus
        binding.nameEdit.requestFocus()

        //configure the spinner to select one city
        val adapter = ArrayAdapter(this.requireContext(), R.layout.spinner_layout, viewModel.cityList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.picker.adapter = adapter
        //set default selection
        if (newRestaurant.city.isNotEmpty()) binding.picker.setSelection(viewModel.cityList.indexOf(newRestaurant.city))
        //item selection handler
        binding.picker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCity = parent?.selectedItem.toString()
                selectedCity.let {
                    newRestaurant.city = it
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        //enable the photo pickup
        binding.selButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICKUPIMAGE)
        }

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment

        return binding.root
    }

    private fun loadData() {
        //restore the old data first
        viewModel.selectedScene.value?.let {
            newRestaurant = it.copy()
        }
    }

    private fun saveData() {
        //newRestaurant.city = binding.cityEdit.text.toString()
        newRestaurant.name = binding.nameEdit.text.toString()
        newRestaurant.address = binding.addressEdit.text.toString()
        newRestaurant.description = binding.descriptEdit.text.toString()

        //viewModel.insertScene(newRestaurant)

        //save data into the database
        if (editMode)
            viewModel.updateScene(newRestaurant)
        else
            viewModel.insertScene(newRestaurant)
        //simulate the press of the back button

        activity?.onBackPressed()
    }

    // get the photo file path returned from the intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICKUPIMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let {
                        newRestaurant.photoFile = it.toString()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> saveData()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        // Hide the keyboard.
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

}