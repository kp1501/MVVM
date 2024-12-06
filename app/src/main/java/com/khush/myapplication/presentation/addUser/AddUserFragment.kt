package com.khush.myapplication.presentation.addUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.khush.myapplication.R
import com.khush.myapplication.data.local.model.ErrorField
import com.khush.myapplication.databinding.FragmentAddUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddUserFragment : Fragment() {

    private lateinit var binding: FragmentAddUserBinding
    private val viewModel: AddUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_user, container, false)
        binding = FragmentAddUserBinding.bind(view)

        binding.layoutTopbar.tvTitle.text = activity?.getString(R.string.add_user)
        binding.layoutTopbar.ivAdd.visibility = View.GONE

        binding.btnAddUser.setOnClickListener {
            viewModel.insertUser(
                binding.etFName.text.toString(),
                binding.etLName.text.toString(),
                binding.etEmail.text.toString()
            )
        }

        binding.layoutTopbar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.insertEvent.collect { insertEvent ->
                when (insertEvent) {
                    is AddUserViewModel.InsertEvent.WrongInput -> {
                        if (insertEvent.errorModel.errorField == ErrorField.FIRST_NAME) {
                            Toast.makeText(
                                this@AddUserFragment.context,
                                insertEvent.errorModel.errorMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (insertEvent.errorModel.errorField == ErrorField.LAST_NAME) {
                            Toast.makeText(
                                this@AddUserFragment.context,
                                insertEvent.errorModel.errorMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                        if (insertEvent.errorModel.errorField == ErrorField.EMAIL) {
                            Toast.makeText(
                                this@AddUserFragment.context,
                                insertEvent.errorModel.errorMessage, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is AddUserViewModel.InsertEvent.InsertSuccessful -> {
                        Toast.makeText(
                            this@AddUserFragment.context,
                            "User added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.etFName.text.clear()
                        binding.etLName.text.clear()
                        binding.etEmail.text.clear()
                        findNavController().popBackStack()
                    }
                }
            }
        }

        return view
    }
}