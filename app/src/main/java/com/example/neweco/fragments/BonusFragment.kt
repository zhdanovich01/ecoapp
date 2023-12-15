package com.example.neweco.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neweco.MainActivity
import com.example.neweco.R
import kotlinx.android.synthetic.main.fragment_bonus.view.*


class BonusFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_bonus, container, false)

        view.returnback2.setOnClickListener {
            (activity as MainActivity).makecamgone()
        }

        return view
    }


}