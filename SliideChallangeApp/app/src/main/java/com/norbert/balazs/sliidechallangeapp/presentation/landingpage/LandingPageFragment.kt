package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.common.base.BaseFragment
import com.norbert.balazs.sliidechallangeapp.databinding.LandingPageFragmentBinding

class LandingPageFragment : BaseFragment<LandingPageFragmentBinding>() {

    companion object {
        fun newInstance() = LandingPageFragment()
    }

    private val viewModel: LandingPageVM by viewModels()

    override fun getLayoutId() = R.layout.landing_page_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}