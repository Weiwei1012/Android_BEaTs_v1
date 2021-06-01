package com.weiwei.beats.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.weiwei.beats.AboutFragmentDirections
import com.weiwei.beats.R

class TrafficAdapter(val aboutList: List<About>): RecyclerView.Adapter<TrafficAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val aboutImage: ImageView = view.findViewById(R.id.about_icon)
        val aboutText: TextView = view.findViewById(R.id.about_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.about_recyclerview_layout, parent, false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {view: View ->  //onClick 
            //Toast.makeText(parent.context, ballList[viewHolder.adapterPosition].name, Toast.LENGTH_SHORT).show()
            //view.findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
            val select_name = aboutList[viewHolder.bindingAdapterPosition].about_text
            val select_web = aboutList[viewHolder.bindingAdapterPosition].about_web
            view.findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToAboutWebFragment(select_name,select_web))
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val about = aboutList[position]  //position at the data set 
        holder.aboutImage.setImageResource(about.about_img)
        holder.aboutText.text = about.about_text
    }


}