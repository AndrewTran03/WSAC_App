package com.example.wsac_app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoritesFragment : Fragment() {

    //Class Variables
    private lateinit var viewModel: WSACViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var foodItems: ArrayList<String> // TODO: Change this to a list of FoodItems when applicable
    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_favorites, container, false)

        viewModel = ViewModelProvider(requireActivity())[WSACViewModel::class.java]

        foodItems = arrayListOf<String>()
        foodItems.add("0")
        foodItems.add("1")
        foodItems.add("2")
        foodItems.add("3")
        foodItems.add("4")
        foodItems.add("5")
        foodItems.add("6")

        recyclerView = view.findViewById<RecyclerView>(R.id.favorites_frag_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        viewAdapter = RecyclerViewAdapter(foodItems, activity as MainActivity)
        recyclerView.adapter = viewAdapter

        MainActivity.appendWorkRequestEvent("FAVORITES FRAGMENT - FRAGMENT VIEW CREATED")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    inner class RecyclerViewAdapter(private val myDataset: ArrayList<String>, private val mainActivity: MainActivity):
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_list, parent, false)
            return ViewHolder(view, mainActivity)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(myDataset[position])
        }

        override fun getItemCount(): Int {
            return myDataset.size
        }

        inner class ViewHolder(private val view: View, private val activity: MainActivity) : RecyclerView.ViewHolder(view) {
            fun bindItems(s: String) {

            }
        }
    }
}

