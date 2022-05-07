package com.norbert.balazs.sliidechallangeapp.presentation.new_user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.norbert.balazs.sliidechallangeapp.R
import com.norbert.balazs.sliidechallangeapp.common.Resource
import com.norbert.balazs.sliidechallangeapp.databinding.NewUserDialogFragmentBinding
import com.norbert.balazs.sliidechallangeapp.presentation.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewUserDialogFragment : DialogFragment() {

    private var _layout: NewUserDialogFragmentBinding? = null

    private val layout get() = _layout!!

    private val viewModel: MainViewModel by activityViewModels()

    private var emailReady = false

    private var nameReady = false

    private var genderSelected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _layout = DataBindingUtil.inflate(
            inflater,
            R.layout.new_user_dialog_fragment,
            container,
            false
        )
        return layout.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _layout = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            layout.genderSelector.adapter = adapter

            layout.genderSelector.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        genderSelected = true
                        updateButtonState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        genderSelected = false
                        updateButtonState()
                    }
                }

            layout.etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // nothing to do here
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    emailReady = (p0?.length!! > 0)
                    updateButtonState()
                }

                override fun afterTextChanged(p0: Editable?) {
                    // nothing to do here
                }
            })

            layout.etName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    // nothing to do here
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    nameReady = (p0?.length!! > 0)
                    updateButtonState()
                }

                override fun afterTextChanged(p0: Editable?) {
                    // nothing to do here
                }
            })
        }

        layout.btnCreate.setOnClickListener {
            val name = layout.etName.text.toString()
            val email = layout.etEmail.text.toString()
            val gender = layout.genderSelector.selectedItem.toString()
            val isActive = if (layout.checkboxActive.isChecked) {
                "active"
            } else {
                "not active"
            }

            layout.btnCreate.isEnabled = false

            lifecycleScope.launch {
                viewModel.createUser(name, email, gender, isActive).collectLatest {
                    when (it) {
                        is Resource.Loading -> {
                            layout.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Error -> {
                            layout.btnCreate.isEnabled = true
                            layout.progressBar.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Resource.Success -> {
                            layout.btnCreate.isEnabled = true
                            layout.progressBar.visibility = View.GONE
                            Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    private fun updateButtonState() {
        layout.btnCreate.isEnabled = emailReady && nameReady && genderSelected
    }
}