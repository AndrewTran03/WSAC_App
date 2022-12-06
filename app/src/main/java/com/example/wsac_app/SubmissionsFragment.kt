package com.example.wsac_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.wsac_app.databinding.FragmentLoginBinding
import com.example.wsac_app.databinding.FragmentSubmissionsBinding

class SubmissionsFragment : Fragment() {

    //Class Variables
    private var previewButton: Button?= null
    private var submitButton: Button?= null

    private lateinit var viewModel: WSACViewModel
    private var _binding: FragmentSubmissionsBinding? = null
    private val binding get() = _binding!!

    private var parser = Parser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        //EditText binding and listeners
        _binding = FragmentSubmissionsBinding.inflate(inflater, container, false)
        val view = binding.root

        //Viewmodel
        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

        //Title listener
        binding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.previewItem.name = parser.getTitle(s.toString())
                viewModel.previewItem.photoId = parser.getPhoto(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        //Time listener
        binding.timeEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.previewItem.time = parser.getTime(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        //Cost listener
        binding.costEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.previewItem.cost = parser.getCost(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        //Cal listener
        binding.calsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.previewItem.cal = parser.getCal(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        //Ingredients listener
        binding.ingredientsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.previewItem.ingredients = parser.getIngredients(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        //Instructions listener
        binding.instructionsEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.previewItem.instructions = parser.getInstructions(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })

        previewButton = view.findViewById(R.id.preview_button)
        previewButton?.setOnClickListener( object: View.OnClickListener {
            override fun onClick(v: View?) {
                viewModel.currPreviewing = true
                viewModel.setPreviewFoodItem(viewModel.previewItem.copy())
                MainActivity.appendWorkRequestEvent("SUBMISSIONS FRAGMENT - RECIPE ${viewModel.currentItem.name} BEING PREVIEWED")
                view.findNavController()?.navigate(R.id.action_submissionsFragment_to_recipeFragment)
            }
        })

        submitButton = view.findViewById(R.id.submit_button)
        submitButton?.setOnClickListener( object: View.OnClickListener {
            override fun onClick(v: View?) {
                viewModel.currPreviewing = false
                viewModel.currentItem = viewModel.previewItem.copy()
                //Log.d("BEFORE SUBMIT", viewModel.recipeList.size.toString())
                viewModel.addFoodItem()
                //Log.d("AFTER SUBMIT", viewModel.recipeList.size.toString())
                MainActivity.appendWorkRequestEvent("SUBMISSIONS FRAGMENT - RECIPE ${viewModel.currentItem.name} SUBMITTED TO LIST FRAGMENT")
                view.findNavController()?.navigate(R.id.action_submissionsFragment_to_confirmationFragment)
            }
        })

        MainActivity.appendWorkRequestEvent("SUBMISSIONS FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}