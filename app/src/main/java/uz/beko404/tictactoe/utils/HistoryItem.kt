package uz.beko404.tictactoe.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.google.android.material.textview.MaterialTextView
import uz.beko404.tictactoe.R

class HistoryItem  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val player1TextView: MaterialTextView
    private val resultTextView: MaterialTextView
    private val player2TextView: MaterialTextView

    init {
        LayoutInflater.from(context).inflate(R.layout.history_item, this, true)

        player1TextView = findViewById(R.id.player_1)
        resultTextView = findViewById(R.id.result)
        player2TextView = findViewById(R.id.player_2)
    }

    fun setData(player1Name: String, result: String, player2Name: String) {
        player1TextView.text = player1Name
        resultTextView.text = result
        player2TextView.text = player2Name
    }
}