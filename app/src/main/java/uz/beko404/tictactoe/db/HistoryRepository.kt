package uz.beko404.tictactoe.db

import uz.beko404.tictactoe.models.History

class HistoryRepository(private val historyDao: HistoryDao) {

    suspend fun insert(history: History) {
        historyDao.setData(history)
    }

    suspend fun getData(): List<History> {
        return historyDao.getData()
    }
}
