package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.common.APPLICATION_TAG
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.common.base.BaseFragment
import com.norbert.balazs.sliidechallangeapp.databinding.LandingPageFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LandingPageFragment : BaseFragment<LandingPageFragmentBinding>() {

    private val viewModel: LandingPageVM by viewModels()

    override fun getLayoutId() = R.layout.landing_page_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.loadUsersAsync().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.i(APPLICATION_TAG, "Loading users")
                    }
                    is Resource.Success -> {
                        Log.i(APPLICATION_TAG, "Loading succeeded: " + resource.data?.size)
                    }
                    is Resource.Error -> {
                        Log.i(APPLICATION_TAG, "Loading failed")
                    }
                }
            }
        }
    }

}