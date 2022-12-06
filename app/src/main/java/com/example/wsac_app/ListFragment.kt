package com.example.wsac_app

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

    //WSAC Notification Service
    private var wsacBroadcastReceiver: WSACBroadcastReceiver? = null
    private var startWSACNotificationServiceIntent: Intent? = null
    private var isInitialized: Boolean = false
    private var isBound: Boolean = false
    private var wsacNotificationService: WSACNotificationService? = null
    private val musicServiceConnection = object : ServiceConnection {
        @SuppressLint("SetTextI18n")
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder = iBinder as WSACNotificationService.MyBinder
            wsacNotificationService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            wsacNotificationService = null
            isBound = false
        }
    }

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

        //WSAC Notification Service
        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(SubmissionsFragment.INITIALIZE_STATUS)
            isBound = false
        }
        startWSACNotificationServiceIntent = Intent(requireContext(), WSACNotificationService::class.java)
        if (!isInitialized) {
            activity?.startService(startWSACNotificationServiceIntent)
            isInitialized = true
        }
        wsacBroadcastReceiver = WSACBroadcastReceiver(this)

        // Gets the sorting options from an array in strings.xml for dropdown
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sorts,
            android.R.layout.simple_spinner_item).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.sortDropdown?.adapter = adapter
        }

        //set spinner's event listener
        binding.sortDropdown?.setOnItemSelectedListener(object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // detect item selected
                if(parent.getItemAtPosition(pos).toString() == "Name (Ascending)") {
                    viewModel.sortNameRegularList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Name (Descending)") {
                    viewModel.sortNameRegularList(false)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Time (Ascending)") {
                    viewModel.sortTimeRegularList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Time (Descending)") {
                    viewModel.sortTimeRegularList(false)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cost (Ascending)") {
                    viewModel.sortCostRegularList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cost (Descending)") {
                    viewModel.sortCostRegularList(false)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cals (Ascending)") {
                    viewModel.sortCaloriesRegularList(true)
                }
                else if(parent.getItemAtPosition(pos).toString() == "Cals (Descending)") {
                    viewModel.sortCaloriesRegularList(false)
                }
                else { //Default Case
                    //do nothing...
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // when selection is empty
            }
        })

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

    override fun onPause() {
        super.onPause()
        if (isBound) {
            activity?.unbindService(musicServiceConnection)
            isBound = false
        }
        activity?.unregisterReceiver(wsacBroadcastReceiver)
        MainActivity.appendWorkRequestEvent("PLAY FRAGMENT - VIEW IS ON_PAUSE")
    }

    override fun onResume() {
        super.onResume()
        if (isInitialized && !isBound) {
            activity?.bindService(startWSACNotificationServiceIntent, musicServiceConnection, Context.BIND_AUTO_CREATE)
        }
        activity?.registerReceiver(wsacBroadcastReceiver, IntentFilter(WSACNotificationService.COMPLETE_INTENT))
        MainActivity.appendWorkRequestEvent("PLAY FRAGMENT - VIEW IS ON_RESUME")
    }

    @SuppressLint("SetTextI18n")
    fun updateStatus(appName: String) {
        MainActivity.appendWorkRequestEvent("$appName IS NOW RUNNING AND OPERATIONAL")
        Toast.makeText(requireContext(), "WSAC App is now running currently!", Toast.LENGTH_SHORT).show()
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
            //if the recipe is in favorites
            if(!viewModel.inFavorites(recipes[position])) {
                holder.view.findViewById<ImageView>(R.id.liked_padding).visibility = View.INVISIBLE
                holder.view.findViewById<ImageView>(R.id.heart_icon).visibility = View.INVISIBLE
            }

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

