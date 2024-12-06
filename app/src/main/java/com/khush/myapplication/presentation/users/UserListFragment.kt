package com.khush.myapplication.presentation.users

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.appizona.yehiahd.fastsave.FastSave
import com.khush.myapplication.R
import com.khush.myapplication.data.adapter.UserAdapter
import com.khush.myapplication.data.local.model.UserEntity
import com.khush.myapplication.databinding.FragmentUserListBinding
import com.khush.myapplication.util.Constants.IS_USER_LOGIN
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(), UserAdapter.ItemClickListener {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var userList: List<UserEntity>
    private val viewModel: UserListViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FastSave.getInstance().saveBoolean(IS_USER_LOGIN, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        binding = FragmentUserListBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            userList = viewModel.getAllUsers().toList()
            if (userList.isEmpty()) {
                activity?.runOnUiThread {
                    binding.llNoData.visibility = View.VISIBLE
                    binding.rcvUsers.visibility = View.GONE
                }
            } else {
                activity?.runOnUiThread {
                    binding.llNoData.visibility = View.GONE
                    binding.rcvUsers.visibility = View.VISIBLE
                    setData(userList)
                }
            }
        }

        binding.layoutTopbar.ivAdd.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addUserFragment)
        }

        binding.layoutTopbar.ivBack.setOnClickListener {
            activity?.finish()
        }

        return view
    }

    private fun setData(userList: List<UserEntity>) {
        userAdapter = UserAdapter(userList, this)
        binding.rcvUsers.adapter = userAdapter
    }

    override fun onUserItemClick() {
        findNavController().navigate(R.id.action_userListFragment_to_weatherFragment)
    }

}