package com.weiwei.beats.adapter

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.weiwei.beats.HomePageFragmentDirections
import com.weiwei.beats.MyViewModel
import com.weiwei.beats.database.Restaurant
import com.weiwei.beats.databinding.HomeRecyclerviewLayoutBinding

//adapter class for the recyclerview (use ListAdapter internally)
//passed arguments: view for display the alertdialog, viewModel for accessing the database
class RestaurantAdapter(val view: Context, val viewModel: MyViewModel) : ListAdapter<Restaurant, RestaurantAdapter.ViewHolder>(BallDiffCallback()), SwipeHandlerInterface {

    class BallDiffCallback : DiffUtil.ItemCallback<Restaurant>() {
        override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(val binding: HomeRecyclerviewLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HomeRecyclerviewLayoutBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            //get the selected scene's id in the database
            val rawId = getItem(viewHolder.bindingAdapterPosition).id
            val name = getItem(viewHolder.bindingAdapterPosition).name

            //pass the id to the detailfragment
            it.findNavController()
                .navigate(HomePageFragmentDirections.actionHomePageFragmentToDetailsFragment(rawId, name))

        }

        viewHolder.itemView.setOnLongClickListener {  //edit the item
            //get the selected scene's id in the database
            val rawId = getItem(viewHolder.bindingAdapterPosition).id
            //pass the id to the addFragment
            it.findNavController().navigate(HomePageFragmentDirections.actionHomePageFragmentToAddFragment(rawId))
            true
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scene = getItem(position)
        holder.binding.scene = scene
        holder.binding.executePendingBindings()
    }

    override fun onItemDelete(position: Int) {
        //the view has been removed out of the screen
        val deletedScene = getItem(position)
        AlertDialog.Builder(view).apply {
            setTitle("Delete this restaurant?")
            setCancelable(false)
            setPositiveButton("Yes") {dialog, which ->
                viewModel.deleteScene(deletedScene) //delete the scene from the database
            }
            setNegativeButton("No") {dialog, which ->
                notifyItemChanged(position) //restore the view
            }
            show()
        }
    }

}

////for resolve app:setImage in the item_layout.xml
//@BindingAdapter("setImage")
//fun ImageView.setSceneImage(scene: Restaurant?) {
//    scene?.let {
//        if (scene.photoFile.isNotEmpty()) {
//            Glide.with(this.context)
//                .load(Uri.parse(scene.photoFile))
//                .apply(RequestOptions().centerCrop())
//                .into(this)
//        } else {
//            setImageResource(scene.photoId)
//        }
//    }
//}