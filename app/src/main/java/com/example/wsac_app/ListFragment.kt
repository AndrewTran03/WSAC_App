package com.example.wsac_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wsac_app.databinding.FragmentListBinding

class ListFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel
    private lateinit var recyclerView: RecyclerView

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

        //Test add stuff to recyclerview
        val testIngredients = arrayOf<String>("apple","banana")
        val testInstructions = arrayOf<String>("cut apple","slice banana")
        val testFoodItems = arrayListOf<FoodItem>()
        testFoodItems.add(FoodItem("Mac and Cheese",10,20.0,100,testIngredients,testInstructions,348534, 2 ))
        adapter.setRecipes(testFoodItems)


        MainActivity.appendWorkRequestEvent("LIST FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    inner class RecipeListAdapter() :
        RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {
        private var recipes = emptyList<FoodItem>()
        private var recipesBackup = emptyList<FoodItem>()
        var switch: Boolean = true

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

        //TODO CHANGE IMAGE RESOURCE
        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            holder.view.findViewById<TextView>(R.id.name).text = recipes[position].name
            holder.view.findViewById<ImageView>(R.id.image).setImageResource(R.drawable.mac)

            holder.itemView.setOnClickListener() {
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

