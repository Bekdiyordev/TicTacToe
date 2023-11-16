package uz.beko404.tictactoe.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.models.HistoryItem
import uz.beko404.tictactoe.databinding.FragmentGameBinding
import uz.beko404.tictactoe.db.AppDatabase
import uz.beko404.tictactoe.db.HistoryRepository
import uz.beko404.tictactoe.db.HistoryViewModel
import uz.beko404.tictactoe.db.HistoryViewModelFactory
import uz.beko404.tictactoe.utils.SharedPref
import uz.beko404.tictactoe.utils.viewBinding
import java.text.SimpleDateFormat
import java.util.Date

class Game : BaseFragment(R.layout.fragment_game) {
    private lateinit var sharedPref: SharedPref
    private val binding by viewBinding { FragmentGameBinding.bind(it) }
    private val board = Array(3) { IntArray(3) { 0 } }
    private var isPlayerTurn = true
    private var playerScoreCount = 0
    private var opponentScoreCount = 0
    private lateinit var viewModel: HistoryViewModel
    private var hasHistory = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val historyDao = AppDatabase.getDatabase(requireContext()).getHistoryDao()
        val repository = HistoryRepository(historyDao)
        viewModel = ViewModelProvider(this, HistoryViewModelFactory(repository))[HistoryViewModel::class.java]
        sharedPref = SharedPref(requireContext())
        setupUI()
    }

    private fun setupUI() = with(binding) {
        playerName.text = sharedPref.username
        opponentName.text = sharedPref.opponent

        p11.setOnClickListener {
            itemClick(it)
        }
        p12.setOnClickListener {
            itemClick(it)
        }
        p13.setOnClickListener {
            itemClick(it)
        }
        p21.setOnClickListener {
            itemClick(it)
        }
        p22.setOnClickListener {
            itemClick(it)
        }
        p23.setOnClickListener {
            itemClick(it)
        }
        p31.setOnClickListener {
            itemClick(it)
        }
        p32.setOnClickListener {
            itemClick(it)
        }
        p33.setOnClickListener {
            itemClick(it)
        }
    }

    private fun itemClick(view: View) {
        val tag = view.tag.toString()
        if (checkPlace(tag = tag)) {
            clearFocus()
            if (isPlayerTurn) {
                (view as ShapeableImageView).setImageResource(R.drawable.ic_x)
                board[tag.substring(0, 1).toInt()][tag.substring(1).toInt()] = 1
                binding.playerTurn.visibility = View.INVISIBLE
                binding.opponetTurn.visibility = View.VISIBLE
            } else {
                (view as ShapeableImageView).setImageResource(R.drawable.ic_o)
                board[tag.substring(0, 1).toInt()][tag.substring(1).toInt()] = 2
                binding.playerTurn.visibility = View.VISIBLE
                binding.opponetTurn.visibility = View.INVISIBLE
            }

            isPlayerTurn = !isPlayerTurn
            checkGameFinished()
        } else
            Toast.makeText(requireContext(), "This place is busy", Toast.LENGTH_SHORT).show()
    }

    private fun clearFocus() = with(binding) {
        p11.isClickable = false
        p12.isClickable = false
        p13.isClickable = false
        p21.isClickable = false
        p22.isClickable = false
        p23.isClickable = false
        p31.isClickable = false
        p32.isClickable = false
        p33.isClickable = false
    }

    private fun setFocus() = with(binding) {
        p11.isClickable = true
        p12.isClickable = true
        p13.isClickable = true
        p21.isClickable = true
        p22.isClickable = true
        p23.isClickable = true
        p31.isClickable = true
        p32.isClickable = true
        p33.isClickable = true
    }

    private fun checkGameFinished() {
        if (isDraw())
            showResult(0)
        else {
            if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != 0) findWinner(
                1
            )
            if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != 0) findWinner(
                2
            )
            if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != 0) findWinner(
                3
            )
            if (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != 0) findWinner(
                4
            )
            if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != 0) findWinner(
                5
            )
            if (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != 0) findWinner(
                6
            )
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) findWinner(
                7
            )
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) findWinner(
                8
            )
            else setFocus()
        }
    }

    private fun isDraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2)
                if (board[i][j] == 0)
                    return false
        }
        return true
    }

    private fun findWinner(i: Int) {
        when (i) {
            1 -> {
                showResult(board[0][0])
                binding.winLine.visibility = View.VISIBLE
                binding.line1.visibility = View.VISIBLE
            }

            2 -> {
                showResult(board[1][0])
                binding.winLine.visibility = View.VISIBLE
                binding.line2.visibility = View.VISIBLE
            }

            3 -> {
                showResult(board[2][0])
                binding.winLine.visibility = View.VISIBLE
                binding.line3.visibility = View.VISIBLE
            }

            4 -> {
                showResult(board[0][0])
                binding.winLine.visibility = View.VISIBLE
                binding.winLine.rotation = -90f
                binding.line1.visibility = View.VISIBLE
            }

            5 -> {
                showResult(board[0][1])
                binding.winLine.visibility = View.VISIBLE
                binding.winLine.rotation = -90f
                binding.line2.visibility = View.VISIBLE
            }

            6 -> {
                showResult(board[0][2])
                binding.winLine.visibility = View.VISIBLE
                binding.winLine.rotation = -90f
                binding.line3.visibility = View.VISIBLE
            }

            7 -> {
                showResult(board[1][1])
                binding.winLine.visibility = View.VISIBLE
                binding.winLine.rotation = 45f
                binding.line2.visibility = View.VISIBLE

            }

            8 -> {
                showResult(board[1][1])
                binding.winLine.visibility = View.VISIBLE
                binding.winLine.rotation = -45f
                binding.line2.visibility = View.VISIBLE
            }
        }
    }

    private fun showDialog(type: Int) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = this.layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_result, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setCancelable(false)
        val b = dialogBuilder.create()
        b.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val icon = dialogView.findViewById<ImageView>(R.id.winner_image)
        val text = dialogView.findViewById<TextView>(R.id.winner_name)
        val reset = dialogView.findViewById<ImageView>(R.id.reset)
        val exit = dialogView.findViewById<ImageView>(R.id.exit)

        when (type) {
            0 -> {
                text.text = getString(R.string.draw)
                icon.setImageResource(R.drawable.ic_draw)
            }

            1 -> {
                text.text = sharedPref.username.plus(" ").plus(getString(R.string.winner))
                icon.setImageResource(R.drawable.ic_x)
                text.setTextColor(Color.parseColor("#Bf208AFF"))
                reset.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#Bf208AFF"))
                exit.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#Bf208AFF"))
            }

            2 -> {
                text.text = sharedPref.opponent.plus(" ").plus(getString(R.string.winner))
                icon.setImageResource(R.drawable.ic_o)
                text.setTextColor(Color.parseColor("#BfF83E3E"))
                reset.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BfF83E3E"))
                exit.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#BfF83E3E"))
            }
        }

        reset.setOnClickListener {
            resetGame()
            b.dismiss()
        }
        exit.setOnClickListener {
            saveHistory()
            findNavController().navigateUp()
            b.dismiss()
        }
        b.show()
    }

    private fun saveHistory() {
        viewModel.insert(
            HistoryItem(
                playerName = sharedPref.username,
                opponentName = sharedPref.opponent,
                result = "$playerScoreCount : $opponentScoreCount",
                date = getCurrentDate()
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hasHistory)
            saveHistory()
    }



    private fun showResult(type: Int) = with(binding) {
        hasHistory = true
        if (type == 1) {
            playerScoreCount++
            playerScore.text = playerScoreCount.toString()
        } else if (type == 2) {
            opponentScoreCount++
            opponentScore.text = opponentScoreCount.toString()
        }

        if (type == 0)
            showDialog(type)
        else
            lifecycleScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    showDialog(type)
                }
            }
    }

    private fun checkPlace(tag: String): Boolean {
        return board[tag.substring(0, 1).toInt()][tag.substring(1).toInt()] == 0
    }

    private fun resetGame() = with(binding) {
        clearBoard()
        setFocus()
        p11.setImageResource(R.drawable.placeholder)
        p12.setImageResource(R.drawable.placeholder)
        p13.setImageResource(R.drawable.placeholder)
        p21.setImageResource(R.drawable.placeholder)
        p22.setImageResource(R.drawable.placeholder)
        p23.setImageResource(R.drawable.placeholder)
        p31.setImageResource(R.drawable.placeholder)
        p32.setImageResource(R.drawable.placeholder)
        p33.setImageResource(R.drawable.placeholder)
        line1.visibility = View.INVISIBLE
        line2.visibility = View.INVISIBLE
        line3.visibility = View.INVISIBLE
        winLine.rotation = 0f
        winLine.visibility = View.GONE
        board.isFocusable = false
    }

    private fun clearBoard() {
        for (i in 0 until 3) {
            for (j in 0 until 3)
                board[i][j] = 0
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }
}