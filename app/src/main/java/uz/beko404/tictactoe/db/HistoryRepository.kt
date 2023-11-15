package uz.beko404.tictactoe.db

import androidx.lifecycle.LiveData
import uz.beko404.tictactoe.models.History

class HistoryRepository(private val historyDao: HistoryDao) {

    val allHistory: LiveData<List<History>> = historyDao.getData()

    suspend fun insert(history: History) {
        historyDao.setData(history)
    }
}
