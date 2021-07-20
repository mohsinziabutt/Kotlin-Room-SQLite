package com.mohsinziabutt.roomdatabaseroomdatabase.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mohsinziabutt.roomdatabase.R
import com.mohsinziabutt.roomdatabase.model.User
import com.mohsinziabutt.roomdatabase.databinding.ListItemBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]

        holder.binding.userId.text = currentItem.id.toString()
        holder.binding.firstName.text = currentItem.firstName
        holder.binding.lastName.text = currentItem.lastName
        holder.binding.age.text = currentItem.age.toString()

        holder.binding.listItem.setOnClickListener { view ->

            val bundleParcel = Bundle()
            bundleParcel.putParcelable("bundleParcel", currentItem)
            view.findNavController().navigate(R.id.action_fragmentList_to_fragmentUpdate, bundleParcel)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setData(user: List<User>)
    {
        this.userList = user
        notifyDataSetChanged()
    }
}