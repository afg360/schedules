package dev.mainhq.schedules

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.bottomappbar.BottomAppBar
import dev.mainhq.schedules.fragments.Alarms
import dev.mainhq.schedules.fragments.Favourites
import dev.mainhq.schedules.fragments.Home
import dev.mainhq.schedules.fragments.Map

//TODO
//when updating the app (especially for new stm txt files), will need
//to show to user storing favourites of "deprecated buses" that it has changed
//to another bus (e.g. 435 -> 465)

class MainActivity : AppCompatActivity() {

    private lateinit var activityType : ActivityType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo
        if (false) { //TODO check if config file exists
            val intent = Intent(this.applicationContext, Config::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.main_activity)
        activityType = ActivityType.HOME
        setFragment()
        setBackground()
        setButtons()
    }

    private fun setFragment(){
        when(activityType){
            ActivityType.HOME -> {
                val home = Home()
                //make home method to hide the bottom nav bar when searchview is expanded
                //FIXME for testing
                //findViewById<CoordinatorLayout>(R.id.bottomNavCoordLayout).visibility = View.GONE
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, home).commit()
                //onBackPressedDispatcher.addCallback {
                //    home.onBackPressed()
                //}
            }
            ActivityType.MAP -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, Home()).commit()
            }
            ActivityType.ALARMS -> {
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, Home()).commit()
            }
        }
    }

    private fun setButtons(){
        findViewById<LinearLayout>(R.id.homeScreenButton).setOnClickListener {
            if (activityType != ActivityType.HOME){
                activityType = ActivityType.HOME
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, Home()).commit()
                setBackground()
            }
        }
        findViewById<LinearLayout>(R.id.mapButton).setOnClickListener {
            if (activityType != ActivityType.MAP){
                activityType = ActivityType.MAP
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, Map()).commit()
                setBackground()
            }
        }
        findViewById<LinearLayout>(R.id.alarmsButton).setOnClickListener {
            if (activityType != ActivityType.ALARMS){
                activityType = ActivityType.ALARMS
                supportFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, Alarms()).commit()
                setBackground()
            }
        }
    }

    private fun setBackground(){
        setDefaultBackgroundColors()
        when(activityType){
            ActivityType.HOME -> {
                findViewById<LinearLayout>(R.id.homeScreenButton).setBackgroundColor(com.google.android.material.R.attr.colorControlHighlight)
            }
            ActivityType.MAP -> {
                findViewById<LinearLayout>(R.id.mapButton).setBackgroundColor(com.google.android.material.R.attr.colorControlHighlight)
            }
            ActivityType.ALARMS -> {
                findViewById<LinearLayout>(R.id.alarmsButton).setBackgroundColor(com.google.android.material.R.attr.colorControlHighlight)
            }
        }
    }

    private fun setDefaultBackgroundColors(){
        val typedValue = TypedValue()
        theme.resolveAttribute(androidx.appcompat.R.attr.selectableItemBackground, typedValue, true)
        findViewById<LinearLayout>(R.id.homeScreenButton).setBackgroundResource(typedValue.resourceId)
        findViewById<LinearLayout>(R.id.mapButton).setBackgroundResource(typedValue.resourceId)
        findViewById<LinearLayout>(R.id.alarmsButton).setBackgroundResource(typedValue.resourceId)
    }
}

enum class ActivityType{
    HOME, MAP, ALARMS
}