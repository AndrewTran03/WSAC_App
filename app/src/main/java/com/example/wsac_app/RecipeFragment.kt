package com.example.wsac_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    private var likeButton: ToggleButton?= null

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

        if(viewModel.currPreviewing) {
            nameText?.text = viewModel.previewItem.name
            timeText?.text = "${viewModel.previewItem.time} m"
            costText?.text = "$${usingKotlinStringFormat(viewModel.previewItem.cost, 2)}"
            calText?.text = "${viewModel.previewItem.cal} cal"

            val ingredientList: Array<String> = viewModel.previewItem.ingredients
            var ingredientListDisplay: String = ""
            for(element in ingredientList) {
                ingredientListDisplay += element + "\n"
            }
            ingredientsText?.text = ingredientListDisplay

            val instructionList: Array<String> = viewModel.previewItem.instructions
            var instructionListDisplay: String = ""
            for(element in instructionList) {
                instructionListDisplay += element + "\n"
            }
            instructionsText?.text = instructionListDisplay

            photoImage = view.findViewById(R.id.recipe_image)
            photoImage?.setImageResource(viewModel.previewItem.photoId!!)

            //code for Made Times buttons
            view.findViewById<TextView>(R.id.made_text).text =
                Html.fromHtml("Made: <b>${viewModel.previewItem.madeTimes}</b> times(s)")
            view.findViewById<FloatingActionButton>(R.id.plus_button).setOnClickListener {
                viewModel.previewItem.madeTimes++
                view.findViewById<TextView>(R.id.made_text).text =
                    Html.fromHtml("Made: <b>${viewModel.previewItem.madeTimes}</b> times(s)")
            }
            view.findViewById<FloatingActionButton>(R.id.minus_button).setOnClickListener {
                viewModel.previewItem.madeTimes--
                view.findViewById<TextView>(R.id.made_text).text =
                    Html.fromHtml("Made: <b>${viewModel.previewItem.madeTimes}</b> times(s)")
            }
        } else {
            nameText?.text = viewModel.currentItem.name
            timeText?.text = "${viewModel.currentItem.time} m"
            costText?.text = "$${usingKotlinStringFormat(viewModel.currentItem.cost, 2)}"
            calText?.text = "${viewModel.currentItem.cal} cal"

            val ingredientList: Array<String> = viewModel.currentItem.ingredients
            var ingredientListDisplay: String = ""
            for(element in ingredientList) {
                ingredientListDisplay += element + "\n"
            }
            ingredientsText?.text = ingredientListDisplay

            val instructionList: Array<String> = viewModel.currentItem.instructions
            var instructionListDisplay: String = ""
            for(element in instructionList) {
                instructionListDisplay += element + "\n"
            }
            instructionsText?.text = instructionListDisplay

            photoImage = view.findViewById(R.id.recipe_image)
            photoImage?.setImageResource(viewModel.currentItem.photoId!!)

            //code for Made Times buttons
            view.findViewById<TextView>(R.id.made_text).text =
                Html.fromHtml("Made: <b>${viewModel.currentItem.madeTimes}</b> times(s)")
            view.findViewById<FloatingActionButton>(R.id.plus_button).setOnClickListener {
                viewModel.currentItem.madeTimes++
                view.findViewById<TextView>(R.id.made_text).text =
                    Html.fromHtml("Made: <b>${viewModel.currentItem.madeTimes}</b> times(s)")
            }
            view.findViewById<FloatingActionButton>(R.id.minus_button).setOnClickListener {
                if(viewModel.currentItem.madeTimes > 0) {
                    viewModel.currentItem.madeTimes--
                    view.findViewById<TextView>(R.id.made_text).text =
                        Html.fromHtml("Made: <b>${viewModel.currentItem.madeTimes}</b> times(s)")
                } else {
                    Toast.makeText(requireContext(), "Error! Please don't create a negative value for number of made times!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //code for Liked ToggleButton
        likeButton = view.findViewById<ToggleButton>(R.id.like_button)
        likeButton?.text = "LIKE"
        likeButton?.textOff = "LIKE"
        likeButton?.textOn = "UNLIKE"
        if(viewModel.currPreviewing) {
            likeButton?.setTextColor(Color.parseColor("#753740"))
            likeButton?.isEnabled = false
            likeButton?.isClickable = false
        }
        else {
            likeButton?.isEnabled = true
            likeButton?.isClickable = true
            if (viewModel.inFavorites(viewModel.currentItem)) {
                likeButton?.isChecked = true
            } else {
                likeButton?.text = "LIKE"
                likeButton?.isChecked = false
            }
            likeButton?.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.addToFavorites()
                } else {
                    viewModel.removeFromFavorites()
                }
            }
        }
    }

    private fun usingKotlinStringFormat(input: Double, scale: Int): kotlin.String {
        return "%.${scale}f".format(input)
    }
}