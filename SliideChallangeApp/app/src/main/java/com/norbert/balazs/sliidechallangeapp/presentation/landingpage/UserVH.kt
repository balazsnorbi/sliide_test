package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import androidx.recyclerview.widget.RecyclerView
import com.norbert.balazs.sliidechallangeapp.databinding.ListItemUserBinding
import com.norbert.balazs.sliidechallangeapp.domain.model.User

class UserVH(private val layout: ListItemUserBinding) : RecyclerView.ViewHolder(layout.root) {

    fun bindTo(user: User) {
        layout.tvName.text = user.name
        layout.tvEmail.text = user.emailAddress
        layout.tvCreationTime.text = user.date
    }
}