package com.norbert.balazs.sliidechallangeapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.databinding.DetailFragmentLayoutBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _layout: DetailFragmentLayoutBinding? = null

    private val layout get() = _layout!!

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _layout = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.detail_fragment_layout,
            null,
            true
        )
        return layout.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layout.tvHello.text = args.userName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layout = null
    }
}