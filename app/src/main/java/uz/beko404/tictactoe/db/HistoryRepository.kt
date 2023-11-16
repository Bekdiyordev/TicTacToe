package uz.beko404.tictactoe.db

import uz.beko404.tictactoe.models.HistoryItem

class HistoryRepository(private val historyDao: HistoryDao) {

    suspend fun insert(historyItem: HistoryItem) {
        historyDao.setData(historyItem)
    }

    suspend fun getData(): List<HistoryItem> {
        return historyDao.getData()
    }
}
