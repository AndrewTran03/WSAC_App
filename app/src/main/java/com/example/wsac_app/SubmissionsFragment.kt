package com.example.wsac_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class SubmissionsFragment : Fragment() {

    private var previewButton: Button?= null
    private var submitButton: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_submissions, container, false)

        previewButton = view.findViewById(R.id.preview_button)
        previewButton?.setOnClickListener( object: View.OnClickListener {
            override fun onClick(v: View?) {
                view.findNavController()?.navigate(R.id.action_submissionsFragment_to_recipeFragment)
            }
        })

        submitButton = view.findViewById(R.id.submit_button)
        submitButton?.setOnClickListener( object: View.OnClickListener {
            override fun onClick(v: View?) {
                view.findNavController()?.navigate(R.id.action_submissionsFragment_to_confirmationFragment)
            }
        })

        MainActivity.appendWorkRequestEvent("SUBMISSIONS FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }
}