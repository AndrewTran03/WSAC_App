package com.example.wsac_app

import android.annotation.SuppressLint
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    //Class Variables
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration

    //Website I Used Here for PostHere.io: https://posthere.io/706a-4262-85b5
    companion object {
        const val USERNAME = "CS3714"
        const val USER_ID = "WSAC_APP"
        const val TAG: String = "CS_3714_SEMESTER_PROJECT"
        const val URL = "https://posthere.io/"
        const val ROUTE = "706a-4262-85b5"
        const val APP_NAME = "VT We Suck At Cooking App"

        fun appendWorkRequestEvent(event: String) {
            val inputData = Data.Builder().putString("username", MainActivity.USERNAME)
                .putString("date", Calendar.getInstance().time.toString())
                .putString("userID", MainActivity.USER_ID)
                .putString("event", event).build()

            val uploadWorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            val setUploadWorkRequestWithInputData = uploadWorkRequest.setInputData(inputData).build()
            WorkManager.getInstance().beginUniqueWork(event, ExistingWorkPolicy.REPLACE, setUploadWorkRequestWithInputData)
                .enqueue()
        }
    }

    @SuppressLint("SetTextI18n")
    fun updateStatus(appName: String) {
        MainActivity.appendWorkRequestEvent("$appName IS NOW RUNNING AND OPERATIONAL")
        Toast.makeText(applicationContext, "WSAC App is now running currently!", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //replace ActionBar with ToolBar
        //toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)

        //hamburger menu
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //find drawer view
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        navView = findViewById<NavigationView>(R.id.nav_view)

        setupDrawerContent(navView)
    }

    private fun setupDrawerContent(navView: NavigationView) {
        navView.setNavigationItemSelectedListener (
            object: NavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    selectDrawerItem(item)
                    return true
                }
        })
    }

    fun selectDrawerItem(item: MenuItem) {
        try {
            //val navControl = Navigation.findNavController(this, R.id.fragment)
            /**
            when (item.itemId) {
                R.id.nav_recipe_fragment -> (RecipeFragment::class.java).newInstance()
                    //navControl.navigate(R.id.action_recipeFragment_to_favoritesFragment)
                R.id.nav_favorites_fragment -> (FavoritesFragment::class.java).newInstance()
                    //navControl.navigate(R.id.action_recipeFragment_to_favoritesFragment)
                R.id.nav_submissions_fragment -> (LoginFragment::class.java).newInstance()
                else -> (RecipeFragment::class.java).newInstance()
            }
            */
            //replace current fragment with new
            /**
            supportFragmentManager.commit {
                replace(R.id.constraint_layout, fragment, "NEW FRAGMENT")
                addToBackStack(null)
            }
            */

            when(item.itemId) {
                R.id.nav_recipe_fragment ->
                    navigateWithClearStack(R.id.recipeFragment)
                R.id.nav_favorites_fragment ->
                    navigateWithClearStack(R.id.favoritesFragment)
                //navControl.navigate(R.id.action_recipeFragment_to_favoritesFragment)
                R.id.nav_submissions_fragment ->
                    navigateWithClearStack(R.id.loginFragment)
                else -> navigateWithClearStack(R.id.recipeFragment)
            }

            //highlight selected item in NavigationView
            navView.setCheckedItem(item)
            //set action bar title
            title = item.title
            //close nav drawer
            drawer.closeDrawers()

        }
        catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //open or close sidebar with up/hamburger action
        if(item.itemId == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //create a new nav graph to allow fragments to use the activity's nav controller
    private fun navigateWithClearStack(destination: Int) {
        val navController = findNavController(R.id.fragment)
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(destination)

        navController.graph = graph
    }
}