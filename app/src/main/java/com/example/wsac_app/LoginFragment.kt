package com.example.wsac_app

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.wsac_app.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    //Class Variables
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        //Listeners
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                password = s.toString()
            }
        })

        binding.loginButton.setOnClickListener {
                if (password.equals("admin")) {
                    val toast: Toast = Toast.makeText(activity?.applicationContext, "Welcome", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    val toast: Toast = Toast.makeText(activity?.applicationContext, "Please enter correct password", Toast.LENGTH_SHORT)
                    toast.show()
                }
        }

        MainActivity.appendWorkRequestEvent("LOGIN FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }
}