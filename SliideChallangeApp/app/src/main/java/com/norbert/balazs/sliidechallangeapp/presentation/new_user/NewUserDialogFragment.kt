package com.norbert.balazs.sliidechallangeapp.presentation.new_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.norbert.balazs.sliidechallangeapp.R

class NewUserDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.new_user_dialog_fragment,
            container, false
        )
    }

}