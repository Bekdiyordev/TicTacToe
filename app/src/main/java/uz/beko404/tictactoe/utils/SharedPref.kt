package uz.beko404.tictactoe.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPref constructor(context: Context) {

    private var mySharedPref: SharedPreferences =
        context.getSharedPreferences("filename", Context.MODE_PRIVATE)

    var username: String
        set(value) = mySharedPref.edit().putString("username", value).apply()
        get() = mySharedPref.getString("username", "Player")!!

    var opponent: String
        set(value) = mySharedPref.edit().putString("opponent", value).apply()
        get() = mySharedPref.getString("opponent", "Opponent")!!

    var nightMode: Boolean
        get() = mySharedPref.getBoolean("night_mode", false)
        set(value) = mySharedPref.edit { putBoolean("night_mode", value) }

    var withCPU: Boolean
        get() = mySharedPref.getBoolean("with_cpu", false)
        set(value) = mySharedPref.edit { putBoolean("with_cpu", value) }

    var isEntered: Boolean
        get() = mySharedPref.getBoolean("entered", false)
        set(value) = mySharedPref.edit { putBoolean("entered", value) }

}