package uz.beko404.tictactoe.ui

import android.os.Bundle
import android.view.View
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.databinding.FragmentGameBinding
import uz.beko404.tictactoe.utils.viewBinding

class Game : BaseFragment(R.layout.fragment_game) {
    private val binding by viewBinding { FragmentGameBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding){

    }
}