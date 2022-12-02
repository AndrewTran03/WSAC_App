package com.example.wsac_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.wsac_app.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel
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

        //Viewmodel
        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

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

        //Listeners
        binding.loginButton.setOnClickListener {
                if (password.equals("admin")) {
                    val toast: Toast = Toast.makeText(activity?.applicationContext, "Welcome, Admin", Toast.LENGTH_SHORT)
                    viewModel.loggedIn = true
                    toast.show()
                    MainActivity.appendWorkRequestEvent("LOGIN FRAGMENT - SUCCESSFUL LOGIN")
                    view.findNavController()?.navigate(R.id.action_loginFragment_to_submissionsFragment)
                } else {
                    val toast: Toast = Toast.makeText(activity?.applicationContext, "Please enter correct password", Toast.LENGTH_SHORT)
                    toast.show()
                    MainActivity.appendWorkRequestEvent("LOGIN FRAGMENT - INVALID LOGIN ATTEMPT")
                }
        }

        MainActivity.appendWorkRequestEvent("LOGIN FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}