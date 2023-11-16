package uz.beko404.tictactoe.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.Entity
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.adapters.HistoryAdapter
import uz.beko404.tictactoe.databinding.FragmentHistoryBinding
import uz.beko404.tictactoe.db.AppDatabase
import uz.beko404.tictactoe.db.HistoryRepository
import uz.beko404.tictactoe.db.HistoryViewModel
import uz.beko404.tictactoe.db.HistoryViewModelFactory
import uz.beko404.tictactoe.models.HistoryItem
import uz.beko404.tictactoe.utils.viewBinding

class History : BaseFragment(R.layout.fragment_history) {
    private val binding by viewBinding { FragmentHistoryBinding.bind(it) }
    private val adapter by lazy { HistoryAdapter() }
    private lateinit var viewModel: HistoryViewModel
    private val dataList = ArrayList<HistoryItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupUI()
    }

    private fun observeData() {
        viewModel.allHistoryItem.observe(requireActivity()) { data ->
            adapter.submitList(groupByDate(data.sortedBy { it.date }))
        }
    }

    fun groupByDate(historyList: List<HistoryItem>): List<List<HistoryItem>> {
        val groupedMap = historyList.groupBy { it.date }
        return groupedMap.values.toList()
    }

    private fun setupUI() = with(binding) {

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        historyRecycler.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val historyDao = AppDatabase.getDatabase(requireContext()).getHistoryDao()
        val repository = HistoryRepository(historyDao)
        viewModel = ViewModelProvider(this, HistoryViewModelFactory(repository))[HistoryViewModel::class.java]
        viewModel.getData()
    }
}