package com.norbert.balazs.sliidechallangeapp.presentation.landingpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.databinding.LandingPageFragmentBinding
import com.norbert.balazs.sliidechallangeapp.presentation.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LandingPageFragment : Fragment() {

    private var _layout: LandingPageFragmentBinding? = null

    private val layout get() = _layout!!

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _layout =
            DataBindingUtil.inflate(layoutInflater, R.layout.landing_page_fragment, null, true)
        return layout.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layout = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layout.rvUsers.layoutManager = LinearLayoutManager(context)
        layout.rvUsers.adapter = UsersAdapter()
        layout.rvUsers.addItemDecoration(SpaceItemDecoration(8))

        lifecycleScope.launchWhenStarted {
            viewModel.isLoading.collectLatest {
                layout.progressBar.visibility = if (it) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.usersList.collectLatest {
                (layout.rvUsers.adapter as UsersAdapter).update(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.isFailed.collectLatest { isError ->
                if (isError) {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        layout.fab.setOnClickListener {
            findNavController().navigate(R.id.action_landingPageFragment_to_newUserDialogFragment)
        }
    }
}