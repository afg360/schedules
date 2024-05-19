package dev.mainhq.schedules

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceManager
import dev.mainhq.schedules.R
import dev.mainhq.schedules.fragments.SettingsPreferences

class Settings : AppCompatActivity(), MenuProvider {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("dark-mode", true)) setTheme(R.style.Theme_Schedules_Dark)
        else setTheme(R.style.Theme_Schedules)
        setContentView(R.layout.settings)
        supportFragmentManager.beginTransaction()
            .replace(R.id.preferencesFragmentContainer, SettingsPreferences())
            .commit()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.back_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        menuItem.setChecked(true)
        return true
    }

    fun changeTheme(){
        finish()
        startActivity(intent)
    }

}
