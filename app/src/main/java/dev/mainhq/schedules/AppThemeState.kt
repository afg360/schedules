package dev.mainhq.schedules

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import kotlin.properties.Delegates

object AppThemeState {

    var prevVal by Delegates.notNull<Boolean>()

    /** Basic init function to be called when setting up a theme
     *  on the oncreate of an activity */
    fun setTheme(activityContext : Context, isDark : Boolean){
        prevVal = isDark
        if (prevVal) activityContext.setTheme(R.style.Theme_Schedules_Dark)
        else activityContext.setTheme(R.style.Theme_Schedules)
    }

    /** Called to see if the theme of the whole app changed
     *  relative to the activity*/
    fun hasThemeChanged(/** This variable serves as seeing the
                            state of isDark changing */
                        isDark: Boolean) : Boolean{
        return (prevVal || isDark) && !(prevVal && isDark)
    }
}