package com.example.wsac_app

import android.annotation.SuppressLint
import android.content.*
import android.content.res.Configuration
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
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
    //Website I Used Here for PostHere.io: https://posthere.io/706a-4262-85b5
    companion object {
        const val USERNAME = "CS3714"
        const val USER_ID = "WSAC_APP"
        const val TAG: String = "CS_3714_SEMESTER_PROJECT"
        const val URL = "https://posthere.io/"
        const val ROUTE = "706a-4262-85b5"
        const val APP_NAME = "VT We Suck At Cooking App"
        const val INITIALIZE_STATUS = "INITIALIZATION STATUS"
        const val APP_NOT_RUNNING_MSG = "APP IS NOT WORKING PROPERLY"
        const val APP_RUNNING_MSG = "APP IS OPERATIONAL"
        const val COMPLETE_INTENT = "COMPLETE INTENT"

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

    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: WSACViewModel

    //WSAC Notification Service and Broadcast Receiver
    private var wsacBroadcastReceiver: WSACBroadcastReceiver? = null
    private var startWSACNotificationServiceIntent: Intent? = null
    private var isInitialized: Boolean = false
    private var isBound: Boolean = false
    private var wsacNotificationService: WSACNotificationService? = null
    private val wsacServiceConnection = object : ServiceConnection {
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

    @SuppressLint("SetTextI18n")
    fun updateStatus(appName: String) {
        Log.d(MainActivity.APP_NAME, MainActivity.APP_RUNNING_MSG)
        Toast.makeText(applicationContext, getString(R.string.run_status), Toast.LENGTH_SHORT).show()
        MainActivity.appendWorkRequestEvent("$appName IS NOW ACTIVE, RUNNING, AND OPERATIONAL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ViewModel
        viewModel = ViewModelProvider(this)[WSACViewModel::class.java]

        //replace ActionBar with ToolBar
        //toolbar = findViewById<Toolbar>(R.id.toolbar)
        //setSupportActionBar(toolbar)

        //hamburger menu
        //TODO: use menu icon to close drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //find drawer view
        drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        navView = findViewById<NavigationView>(R.id.nav_view)

        setupDrawerContent(navView)

        //WSAC Notification Service and BroadcastReceiver
        if (savedInstanceState != null) {
            isInitialized = savedInstanceState.getBoolean(INITIALIZE_STATUS)
        }
        startWSACNotificationServiceIntent = Intent(this, WSACNotificationService::class.java)
        if (!isInitialized) {
            startService(startWSACNotificationServiceIntent)
            isInitialized = true
        }
        wsacBroadcastReceiver = WSACBroadcastReceiver(this)
        updateWSACAppName()
        MainActivity.appendWorkRequestEvent("WSAC APP HAS BEEN CREATED/INITIALIZED - NOW ACTIVE AND OPERATIONAL")
    }

    override fun onPause() {
        super.onPause()
        if (isBound) {
            unbindService(wsacServiceConnection)
            isBound = false
        }
        unregisterReceiver(wsacBroadcastReceiver)
        MainActivity.appendWorkRequestEvent("WSAC APP IS NOW PAUSED")
    }

    override fun onResume() {
        super.onResume()
        if (isInitialized && !isBound) {
            bindService(startWSACNotificationServiceIntent, wsacServiceConnection, Context.BIND_AUTO_CREATE)
        }
        registerReceiver(wsacBroadcastReceiver, IntentFilter(MainActivity.COMPLETE_INTENT))
        updateWSACAppName()
        MainActivity.appendWorkRequestEvent("WSAC APP IS NOW RESUMED AND OPERATIONAL ONCE MORE")
    }

    private fun updateWSACAppName() {
        val intent = Intent(MainActivity.COMPLETE_INTENT)
        intent.putExtra(MainActivity.APP_NAME, "VT WSAC APP")
        sendBroadcast(intent)
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
                    navigateWithClearStack(R.id.listFragment)
                R.id.nav_favorites_fragment ->
                    navigateWithClearStack(R.id.favoritesFragment)
                //navControl.navigate(R.id.action_recipeFragment_to_favoritesFragment)
                R.id.nav_submissions_fragment ->
                    if (viewModel.loggedIn) {
                        val toast: Toast = Toast.makeText(applicationContext, getString(R.string.welcome), Toast.LENGTH_SHORT)
                        toast.show()
                        navigateWithClearStack(R.id.submissionsFragment)
                    }
                    else
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