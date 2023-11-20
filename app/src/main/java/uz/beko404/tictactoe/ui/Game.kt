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

    private var difficultyLevel: Int = 2
    private val cpuSymbol = R.drawable.ic_o

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val historyDao = AppDatabase.getDatabase(requireContext()).getHistoryDao()
        val repository = HistoryRepository(historyDao)
        viewModel = ViewModelProvider(
            this,
            HistoryViewModelFactory(repository)
        )[HistoryViewModel::class.java]
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
                isPlayerTurn = false
            } else {
                (view as ShapeableImageView).setImageResource(R.drawable.ic_o)
                board[tag.substring(0, 1).toInt()][tag.substring(1).toInt()] = 2
                binding.playerTurn.visibility = View.VISIBLE
                binding.opponetTurn.visibility = View.INVISIBLE
                isPlayerTurn = true
            }


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
        if (sharedPref.withCPU && !isPlayerTurn) {
            playCPUMove()
        }
    }

    private fun checkGameFinished() {
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
            else {
                if (isDraw())
                    showResult(0)
                setFocus()
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
            if (sharedPref.withCPU)
                resetGameForCPU()
            else
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

    private fun playCPUMove() {
        when (difficultyLevel) {
            1 -> playEasyCPUMove()
            2 -> playMediumCPUMove()
            3 -> playHardCPUMove()
            // Add more levels if needed
        }
    }

    private fun playEasyCPUMove() {
        // Oddiy algoritm (masalan, random joylash)
        val emptyCells = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == 0) {
                    emptyCells.add(Pair(i, j))
                }
            }
        }
        if (emptyCells.isNotEmpty()) {
            val randomIndex = (0 until emptyCells.size).random()
            val (i, j) = emptyCells[randomIndex]
            updateBoardForCPUMove(i, j)
        }
    }

    private fun playMediumCPUMove() {
        // O'rta darajali algoritm
        val playerSymbol = R.drawable.ic_x
        val cpuSymbol = R.drawable.ic_o

        // Qo'yingan joy qo'yishni tekshirish
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == 0) {
                    // Check for a winning move for CPU
                    board[i][j] = 2
                    if (checkWinningMove()) {
                        updateBoardForCPUMove(i, j)
                        return
                    }
                    board[i][j] = 0
                }
            }
        }

        // Muqobil qo'yingan joyni blok qilish
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == 0) {
                    // Check for a blocking move for the opponent
                    board[i][j] = 1
                    if (checkWinningMove()) {
                        updateBoardForCPUMove(i, j)
                        return
                    }
                    board[i][j] = 0
                }
            }
        }

        // Agar qo'yingan joy bo'lsangiz, random joylash
        playEasyCPUMove()
    }


    private fun playHardCPUMove() {
        // Expert darajali algoritm
        val playerSymbol = R.drawable.ic_x
        val cpuSymbol = R.drawable.ic_o

        // Qo'yingan joy qo'yishni tekshirish
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == 0) {
                    // Check for a winning move for CPU
                    board[i][j] = 2
                    if (checkWinningMove()) {
                        updateBoardForCPUMove(i, j)
                        return
                    }
                    board[i][j] = 0
                }
            }
        }

        // Muqobil qo'yingan joyni blok qilish
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (board[i][j] == 0) {
                    // Check for a blocking move for the opponent
                    board[i][j] = 1
                    if (checkWinningMove()) {
                        updateBoardForCPUMove(i, j)
                        return
                    }
                    board[i][j] = 0
                }
            }
        }

        // Agar barcha joylar qo'yingan bo'lsa, random joylash
        playEasyCPUMove()
    }

    private fun checkWinningMove(): Boolean {
        // Check if CPU or the opponent has a winning move
        for (i in 0 until 3) {
            // Check rows
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                return true
            }
            // Check columns
            if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                return true
            }
        }
        // Check diagonals
        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            return true
        }
        if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            return true
        }
        return false
    }


    private fun updateBoardForCPUMove(i: Int, j: Int) {
        board[i][j] = 2 // 2 - CPU belgisi
        val cellId = "$i$j"
        val cellView = binding.root.findViewWithTag<ShapeableImageView>(cellId)
        cellView.setImageResource(cpuSymbol)
        togglePlayerTurn()
        checkGameFinished()
    }

    private fun checkGameFinishedWithCPU() {
        if (isDraw()) {
            showResult(0)
        } else {
            showResult(2)
            // Qo'yingan bo'lsa, showResult(2) chaqirish
            // Qo'yingan bo'lmasa, setFocus() chaqirish
        }
    }

    private fun togglePlayerTurn() {
        isPlayerTurn = !isPlayerTurn
        if (isPlayerTurn) {
            binding.playerTurn.visibility = View.VISIBLE
            binding.opponetTurn.visibility = View.INVISIBLE
        } else {
            binding.playerTurn.visibility = View.INVISIBLE
            binding.opponetTurn.visibility = View.VISIBLE
        }
    }

    private fun resetGameForCPU() {
        // O'yinni tiklash uchun barcha o'zgarishlarni boshqarish
        playerScoreCount = 0
        opponentScoreCount = 0
        binding.playerScore.text = "0"
        binding.opponentScore.text = "0"
        resetGame()
    }
}