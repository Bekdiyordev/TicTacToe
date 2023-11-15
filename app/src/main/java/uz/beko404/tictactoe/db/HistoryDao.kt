package uz.beko404.tictactoe.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.beko404.tictactoe.models.History

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setData(address: History): Long

    @Query("SELECT * FROM history")
    suspend fun getData(): List<History>

    @Query("DELETE FROM history")
    suspend fun clearAddresses()
}