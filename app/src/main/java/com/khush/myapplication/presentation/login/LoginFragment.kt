package com.khush.myapplication.presentation.login

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
import com.khush.myapplication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            viewModel.loginProcess(binding.etEmail.text.toString(), binding.etPass.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginEvent.collect { loginEvent ->
                when (loginEvent) {
                    is LoginViewModel.LoginEvent.WrongInput -> {
                        if (loginEvent.errorModel.errorField == ErrorField.PASSWORD) {
                            Toast.makeText(
                                this@LoginFragment.context,
                                loginEvent.errorModel.errorMessage, Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@LoginFragment.context,
                                loginEvent.errorModel.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is LoginViewModel.LoginEvent.NavigateToUserListScreen -> {
                        findNavController().navigate(R.id.action_loginFragment_to_userListFragment)
                    }
                }
            }
        }

        return view
    }

}