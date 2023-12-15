package com.example.neweco.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.neweco.MainActivity
import com.example.neweco.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.view.*


class LoginFragment : Fragment() {
    private lateinit var registrationFragment: RegistrationFragment

    private lateinit var database : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        registrationFragment = RegistrationFragment()


        val sharedPreferences : SharedPreferences = requireContext().getSharedPreferences("ecoSharedPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        view.nextbut1.setOnClickListener{
            var mail: String = view.mail.text.toString()
            var pass: String = view.pass.text.toString()
            if (mail != "" && pass != ""){
                var newmail = ""
                for (i in mail.toString()){
                    if (i == '@'){
                        newmail += ' '
                    }
                    else if(i == '.'){
                        newmail += ' '
                    }
                    else{
                        newmail += i
                    }
                }
                mail = newmail

                database = FirebaseDatabase.getInstance().getReference("users")
                database.child(mail.toString()).get().addOnSuccessListener {
                    if (it.exists()) {
                        val newpass = it.child("pass").value
                        val name = it.child("name").value
                        val surname = it.child("surname").value
                        val lastname = it.child("lastname").value
                        Log.d("pass", pass)
                        Log.d("pass", newpass.toString())
                        if (pass == newpass.toString()){
                            var newmail = ""
                            var firsttime = true
                            for (i in mail){
                                if (i == ' ' && firsttime == true){
                                    newmail += '@'
                                    firsttime = false
                                }
                                else if (i == ' ' && firsttime == false){
                                    newmail += '.'

                                }
                                else{
                                    newmail += i
                                }
                            }
                            mail = newmail
                            editor.putString("mail", mail).apply()
                            editor.putString("name", name.toString()).apply()
                            editor.putString("lastname", lastname.toString()).apply()
                            editor.putString("surname", surname.toString()).apply()
                            editor.putString("log", "yes").apply()
                            Toast.makeText(getActivity(), "Вы успешно вошли в аккаунт!", Toast.LENGTH_LONG).show()
                            val slideUpAnimation: Animation =
                                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)

                            // Примените анимацию к вашему представлению (например, к фрагменту)
                            view.startAnimation(slideUpAnimation)
                            view.visibility = View.GONE
                            (activity as MainActivity).makemapvisible()

                        }else{
                            Toast.makeText(getActivity(), "Вы ввели неверный пароль", Toast.LENGTH_LONG).show()

                        }
                    }
                    else{
                        Toast.makeText(getActivity(), "Такого пользователя нет", Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(getActivity(), "Ошибка сети", Toast.LENGTH_LONG).show()

                }
            }else{
                Toast.makeText(getActivity(), "Вы не ввели почту или пароль", Toast.LENGTH_LONG).show()
            }
        }

        view.goreg.setOnClickListener{
            val slideUpAnimation: Animation =
                AnimationUtils.loadAnimation(requireContext(), R.anim.slide_left)

            // Примените анимацию к вашему представлению (например, к фрагменту)
            view.startAnimation(slideUpAnimation)
            setFragment(registrationFragment)
        }

        return view
    }


    private fun setFragment(fragment: Fragment){

        val fragmentTransaction = getParentFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.reglog, fragment)
        fragmentTransaction.commit()
    }

}