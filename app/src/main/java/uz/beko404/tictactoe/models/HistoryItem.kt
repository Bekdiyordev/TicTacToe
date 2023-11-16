package uz.beko404.tictactoe.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val playerName: String,
    val opponentName: String,
    val result: String,
    val date: String
)
