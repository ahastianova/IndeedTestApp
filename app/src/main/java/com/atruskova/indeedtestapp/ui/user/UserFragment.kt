package com.atruskova.indeedtestapp.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.atruskova.indeedtestapp.R

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        userViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = getString(R.string.username, it)
        })
        return root
    }
}