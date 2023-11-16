package uz.beko404.tictactoe.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.beko404.tictactoe.models.HistoryItem

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {

    val allHistoryItem =  MutableLiveData<List<HistoryItem>>()

    fun insert(historyItem: HistoryItem) = viewModelScope.launch {
        repository.insert(historyItem)
    }

    fun getData() = viewModelScope.launch{
        allHistoryItem.value = repository.getData()
    }
}
