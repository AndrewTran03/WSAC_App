package com.example.wsac_app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class RecipeFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel
    private var nameText: TextView?= null
    private var timeText: TextView ?= null
    private var costText: TextView ?= null
    private var calText: TextView ?= null
    private var ingredientsText: TextView ?= null
    private var instructionsText: TextView ?= null
    private var photoImage: ImageView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_recipe, container, false)

        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

        MainActivity.appendWorkRequestEvent("RECIPE FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameText = view.findViewById(R.id.recipe_title)
        timeText = view.findViewById(R.id.time_text)
        costText = view.findViewById(R.id.cost_text)
        calText = view.findViewById(R.id.cal_text)

        ingredientsText = view.findViewById(R.id.ingredients_text)
        instructionsText = view.findViewById(R.id.instructions_text)

        nameText?.text = viewModel.currentItem?.name
        timeText?.text = "${viewModel.currentItem?.time} m"
        //TODO: Get Doubles to display in price format (two digits after decimal)
        costText?.text = "$${viewModel.currentItem?.cost}"
        calText?.text = "${viewModel.currentItem?.cal} cal"

        var ingredientList: Array<String> = viewModel.currentItem?.ingredients!!
        var ingredientListDisplay: String = ""
        for(element in ingredientList) {
            ingredientListDisplay += element + "\n"
        }
        ingredientsText?.text = ingredientListDisplay

        var instructionList: Array<String> = viewModel.currentItem?.instructions!!
        var instructionListDisplay: String = ""
        for(i in (0..instructionList.size - 1)) {
            instructionListDisplay += instructionList[i] + "\n"
        }
        instructionsText?.text = instructionListDisplay

        photoImage = view.findViewById(R.id.recipe_image)
        photoImage?.setImageResource(viewModel.currentItem?.photoId!!)
    }
}