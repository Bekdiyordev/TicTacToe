package uz.beko404.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import uz.beko404.tictactoe.utils.SharedPref

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                )
        sharedPref = SharedPref(this)
        setTheme()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setTheme() {
        if (sharedPref.nightMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}