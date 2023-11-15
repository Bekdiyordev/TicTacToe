package uz.beko404.tictactoe.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.beko404.tictactoe.models.History

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    val allHistory: LiveData<List<History>> = repository.allHistory()

    fun insert(history: History) = viewModelScope.launch {
        repository.insert(history)
    }
}
