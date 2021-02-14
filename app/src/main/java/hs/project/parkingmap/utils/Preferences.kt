package hs.project.parkingmap.utils

import android.content.Context
import androidx.preference.PreferenceManager

class Preferences {

    /**
     *  String
     */
    fun putSharedPreference(context: Context?, key: String?, value: String?) {
        if (context == null) return
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getSharedPreference(context: Context?, key: String?, defaultValue: String?): String? {
        if (context == null) return null
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, defaultValue)
    }

    /**
     * Integer
     */
    fun putSharedPreference(context: Context?, key: String?, value: Int) {
        if (context == null) return
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun getIntSharedPreference(context: Context?, key: String?): Int {
        if (context == null) return 0
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(key, 0)
    }

    /**
     * Boolean
     */
    fun putSharedPreference(context: Context?, key: String?, value: Boolean) {
        if (context == null) return
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBooleanSharedPreference(context: Context?, key: String?): Boolean {
        if (context == null) return false
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean(key, false)
    }


    /**
     *  key 에 대한 값이 없을 경우 null 반환
     */
    fun getSharedPreference(context: Context?, key: String?): String? {
        if (context == null) return null
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString(key, null)
    }

}