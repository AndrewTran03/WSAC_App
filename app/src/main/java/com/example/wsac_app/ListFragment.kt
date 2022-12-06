package com.example.wsac_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wsac_app.databinding.FragmentListBinding

class ListFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel
    private lateinit var recyclerView: RecyclerView

    private var parser = Parser()
    val adapter = RecipeListAdapter()

    //Viewbinding
    private var _binding: FragmentListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

        //Viewmodel
        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

        //RecyclerView
        recyclerView = binding.listFragRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        //Sorting Functionality Event Listeners
        binding.listFragSortNameButton.setOnClickListener {
            viewModel.sortNameRegularList()
        }

        binding.listFragSortCostButton.setOnClickListener {
            viewModel.sortCostRegularList()
        }

        binding.listFragSortCaloriesButton.setOnClickListener {
            viewModel.sortCaloriesRegularList()
        }

        viewModel.currPreviewing = false

        viewModel.allRecipes.observe(
            viewLifecycleOwner,
            Observer<List<FoodItem>>{ recipes ->
                recipes?.let {
                    adapter.setRecipes(it)
                }
            }
        )

        MainActivity.appendWorkRequestEvent("LIST FRAGMENT - FRAGMENT VIEW CREATED")
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
            holder.view.findViewById<ImageView>(R.id.image).setImageResource(parser.getPhoto(recipes[position].name))

            holder.itemView.setOnClickListener() {
                viewModel.currentItem = recipes[position]
                MainActivity.appendWorkRequestEvent("LIST FRAGMENT - RECIPE ${viewModel.currentItem.name} SELECTED AND BEING VIEWED")
                view?.findNavController()?.navigate(R.id.action_listFragment_to_recipeFragment)
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

