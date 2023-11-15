package uz.beko404.tictactoe.ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.databinding.FragmentHomeBinding
import uz.beko404.tictactoe.db.HistoryViewModel
import uz.beko404.tictactoe.utils.SharedPref
import uz.beko404.tictactoe.utils.viewBinding

class Home : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private lateinit var sharedPref: SharedPref
    private lateinit var viewModel: HistoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HistoryViewModel::class.java]
        sharedPref = SharedPref(requireContext())
        setupUI()
        observeData()
    }

    private fun observeData() {
        viewModel.allHistory.observe(viewLifecycleOwner) { historyList ->

        }
    }

    private fun setupUI() = with(binding) {

        playerName.text = sharedPref.username

        multiplayer.setOnClickListener {
            sharedPref.withCPU = false
            findNavController().navigate(R.id.action_home_to_game)
        }

        cpu.setOnClickListener {
            sharedPref.withCPU = true
            findNavController().navigate(R.id.action_home_to_game)
        }

        email.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/bekdiyor04")))
        }
        history.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_history)
        }

        profile.setOnClickListener {
            showDialog(true, getString(R.string.player_title), sharedPref.username)
        }

        theme.setOnClickListener {
            changeTheme()
        }

        rate.setOnClickListener {
            openRateWindow()
        }
    }

    private fun showDialog(state: Boolean, title: String, username: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_profile, null)
        dialogBuilder.setView(dialogView)
        val b = dialogBuilder.create()
        b.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogView.findViewById<TextView>(R.id.player_name_title).text = title
        val usernameView = dialogView.findViewById<TextView>(R.id.username)
        usernameView.text = username
        val button = dialogView.findViewById<MaterialButton>(R.id.save)
        button.isEnabled = !usernameView.text.isNullOrEmpty()

        usernameView.doAfterTextChanged {
            button.isEnabled = !it.isNullOrEmpty()
        }

        button.setOnClickListener {
            if (state) {
                sharedPref.username = usernameView.text.toString()
                binding.playerName.text = sharedPref.username
            } else {
                sharedPref.opponent = usernameView.text.toString()
                findNavController().navigate(R.id.action_home_to_game)
            }
            b.dismiss()
        }
        b.show()
    }
    private fun changeTheme() {
        if (sharedPref.nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPref.nightMode = false
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPref.nightMode = true
        }
    }
    private fun openRateWindow() {
        val uri: Uri = Uri.parse("market://details?id=${activity?.packageName}")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=${activity?.packageName}")
                )
            )
        }
    }

}