package com.example.neweco.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neweco.MainActivity
import com.example.neweco.R
import kotlinx.android.synthetic.main.fragment_my_profile.view.*


class MyProfileFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_profile, container, false)

        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("ecoSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        var getname = sharedPreferences.getString("name", "none")
        var getsurname = sharedPreferences.getString("surname", "none")
        var getmail = sharedPreferences.getString("mail", "none")
        var fullname = getname + getsurname
        view.name.text = fullname.toString()
        view.changename.text = getname.toString()
        view.mail.text = getmail.toString()

        view.returnback.setOnClickListener {
            (activity as MainActivity).makecamgone()
        }

        return view
    }


}