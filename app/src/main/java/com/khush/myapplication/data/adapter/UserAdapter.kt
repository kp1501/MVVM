package com.khush.myapplication.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.khush.myapplication.R
import com.khush.myapplication.data.local.model.UserEntity

class UserAdapter(
    private val userList: List<UserEntity>, private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.userName.text = userList[position].firstName + userList[position].lastName
        holder.userEmail.text = userList[position].email

        holder.itemView.setOnClickListener {
            itemClickListener.onUserItemClick()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userItem: CardView = itemView.findViewById(R.id.item_user)
        val userName: TextView = itemView.findViewById(R.id.tv_user_name)
        val userEmail: TextView = itemView.findViewById(R.id.tv_user_email)
    }

    interface ItemClickListener {
        fun onUserItemClick()
    }
}