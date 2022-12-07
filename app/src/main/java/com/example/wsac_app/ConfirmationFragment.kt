package com.example.wsac_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

class ConfirmationFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_confirmation, container, false)

        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

        MainActivity.appendWorkRequestEvent("CONFIRMATION FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
        MainActivity.appendWorkRequestEvent("CONFIRMATION FRAGMENT - VIEW IS PAUSED")
    }

    override fun onResume() {
        super.onResume()
        MainActivity.appendWorkRequestEvent("CONFIRMATION FRAGMENT - VIEW HAS BEEN RESUMED/RESTORED")
    }
}