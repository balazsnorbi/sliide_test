package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.domain.model.User

class UsersAdapter(
    private val deleteUserListener: DeleteUserListener
) : RecyclerView.Adapter<UserVH>() {

    private val usersList = mutableListOf<User>()

    fun update(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(
            UsersDiffUtilCallback(newList, usersList)
        )
        diffResult.dispatchUpdatesTo(this)
        usersList.clear()
        usersList.addAll(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserVH {
        return UserVH(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        val user = usersList[position]
        holder.bindTo(user)
        holder.itemView.setOnLongClickListener {
            it.context?.let { context ->
                AlertDialog.Builder(it.context)
                    .setTitle(context.getString(R.string.user_confirmation_title))
                    .setMessage(context.getString(R.string.user_confirmation_content))
                    .setPositiveButton(
                        android.R.string.ok
                    ) { _, _ ->
                        deleteUserListener.onUserDeleteRequested(user.id)
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .show()
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}