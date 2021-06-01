package com.weiwei.beats

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.weiwei.beats.adapter.RestaurantAdapter
import com.weiwei.beats.adapter.SwipeHandler
import com.weiwei.beats.database.RestaurantDatabase
import com.weiwei.beats.databinding.FragmentHomePageBinding

class HomePageFragment : Fragment() {

    private lateinit var binding: FragmentHomePageBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_page, container, false)

//        //retrieve the database dao
//        val application = requireNotNull(this.activity).application
//        val dataSource = RestaurantDatabase.getInstance(application).restaurantDatabaseDao

        //get the shared viewModel associated with the activity
        viewModel = ViewModelProvider(requireActivity(),
            MyViewModelFactory(requireActivity().application)).get(MyViewModel::class.java)

        //setup RecyclerView
        //val layoutManager = LinearLayoutManager(this.activity)
        val layoutManager = GridLayoutManager(this.activity, 1, GridLayoutManager.VERTICAL, false)
        binding.recyclerViewHome.layoutManager = layoutManager
        val adapter = RestaurantAdapter(requireActivity(), viewModel) //based on ListAdapter
        binding.recyclerViewHome.adapter = adapter
        //binding.recyclerView.addItemDecoration(DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))

        //setup swipe handler
        val swipeHandler = ItemTouchHelper(SwipeHandler(adapter,0,(ItemTouchHelper.LEFT)))
        swipeHandler.attachToRecyclerView(binding.recyclerViewHome)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //observe any changes on the data source of the recylerview
        //sceneList is a livedata return by the database query
        viewModel.sceneList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)  //submit the up-to-date sceneList to the recyclerView
            }
        })

        //enable options menu
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_menu, menu)

        // Initialize Search View
        searchView = menu.findItem(R.id.searchView)?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchScene(query!!)
                hideKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.setOnCloseListener {
            viewModel.getAllScenes()
            searchView.onActionViewCollapsed()
            hideKeyboard()
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.
//        onNavDestinationSelected(item, requireView().findNavController())
//                || super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.addFragment -> requireView().findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToAddFragment(0))
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideKeyboard() {
        // Hide the keyboard.
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

}