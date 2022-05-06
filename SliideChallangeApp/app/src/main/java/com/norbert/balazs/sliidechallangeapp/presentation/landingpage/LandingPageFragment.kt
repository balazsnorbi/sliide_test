package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import androidx.fragment.app.viewModels
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.common.base.BaseFragment
import com.norbert.balazs.sliidechallangeapp.databinding.LandingPageFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingPageFragment : BaseFragment<LandingPageFragmentBinding>() {

    private val viewModel: LandingPageVM by viewModels()

    override fun getLayoutId() = R.layout.landing_page_fragment

    override fun onStart() {
        super.onStart()
        viewModel.getUsers()
    }

}