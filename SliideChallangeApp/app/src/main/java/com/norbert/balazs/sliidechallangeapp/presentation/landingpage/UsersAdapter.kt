package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.domain.model.User

class UsersAdapter : RecyclerView.Adapter<UserVH>() {

    private val usersList = mutableListOf<User>()

    fun update(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(
            UsersDiffUtilCallback(newList, usersList)
        )
        usersList.clear()
        usersList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        return UserVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.bindTo(usersList[position])
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}