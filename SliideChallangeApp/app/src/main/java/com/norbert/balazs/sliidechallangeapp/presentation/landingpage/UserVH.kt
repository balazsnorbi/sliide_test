package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.norbert.balazs.sliidechallangeapp.databinding.ListItemUserBinding
import com.norbert.balazs.sliidechallangeapp.domain.model.User
import java.text.SimpleDateFormat
import java.util.*

class UserVH(private val layout: ListItemUserBinding) : RecyclerView.ViewHolder(layout.root) {

    fun bindTo(user: User) {
        layout.tvName.text = user.name
        layout.tvEmail.text = user.emailAddress
        layout.tvCreationTime.text = toDate(user.date)
    }

    @SuppressLint("SimpleDateFormat")
    fun toDate(millis: Long): String {
        return SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(Date(millis))
    }
}