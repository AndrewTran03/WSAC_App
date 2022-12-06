package com.example.wsac_app

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wsac_app.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel
    private lateinit var recyclerView: RecyclerView

    private var parser = Parser()
    val adapter = RecipeListAdapter()

    //Viewbinding
    private var _binding: FragmentFavoritesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root

        //Viewmodel
        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

        //RecyclerView
        recyclerView = binding.favoritesFragRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        // gets the sorting options from an array in strings.xml for dropdown
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sorts,
            android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.sortFavoritesDropdown?.adapter = adapter
        }

        //set spinner's event listener
        binding.sortFavoritesDropdown?.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // detect item selected
                if(parent.getItemAtPosition(pos).toString() == "Name (Ascending)") {
                    viewModel.sortNameFavList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Name (Descending)") {
                    viewModel.sortNameFavList(false)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Time (Ascending)") {
                    viewModel.sortTimeFavList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Time (Descending)") {
                    viewModel.sortTimeFavList(false)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cost (Ascending)") {
                    viewModel.sortCostFavList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cost (Descending)") {
                    viewModel.sortCostFavList(false)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cals (Ascending)") {
                    viewModel.sortCaloriesFavList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cals (Descending)") {
                    viewModel.sortCaloriesFavList(false)
                }
                else {
                    //do nothing
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // when selection is empty
            }
        })

        viewModel.currPreviewing = false

        viewModel.likedRecipes.observe(
            viewLifecycleOwner,
            Observer<List<FoodItem>>{ recipes ->
                recipes?.let {
                    adapter.setRecipes(it)
                }
            }
        )

        MainActivity.appendWorkRequestEvent("FAVORITES FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    inner class RecipeListAdapter() :
        RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {
        private var recipes = emptyList<FoodItem>()
        private var recipesBackup = emptyList<FoodItem>()

        internal fun setRecipes(recipes: List<FoodItem>) {
            recipesBackup = recipes
            this.recipes = recipes
            notifyDataSetChanged()
        }

        fun restore() {
            recipes = recipesBackup
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return recipes.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false)
            return RecipeViewHolder(v)
        }

        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            holder.view.findViewById<TextView>(R.id.name).text = recipes[position].name
            holder.view.findViewById<TextView>(R.id.name).setTextColor(
                ContextCompat.getColor(requireContext(), R.color.white))
            holder.view.findViewById<CardView>(R.id.list_card)?.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.cadet_blue))
            holder.view.findViewById<ImageView>(R.id.image).setImageResource(parser.getPhoto(recipes[position].name))

            holder.itemView.setOnClickListener() {
                viewModel.currentItem = recipes[position]
                MainActivity.appendWorkRequestEvent("FAVORITES FRAGMENT - RECIPE ${viewModel.currentItem.name} SELECTED AND BEING VIEWED")
                view?.findNavController()?.navigate(R.id.action_favoritesFragment_to_recipeFragment)
            }
        }

        inner class RecipeViewHolder(val view: View) : RecyclerView.ViewHolder(view),
            View.OnClickListener {
            override fun onClick(view: View?) {
                if (view != null) {

                }
            }
        }
    }
}

