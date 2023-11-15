package uz.beko404.tictactoe.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.room.Entity
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.adapters.HistoryAdapter
import uz.beko404.tictactoe.databinding.FragmentHistoryBinding
import uz.beko404.tictactoe.models.History
import uz.beko404.tictactoe.utils.viewBinding

@Entity(tableName = "history")
class History : BaseFragment(R.layout.fragment_history) {
    private val binding by viewBinding { FragmentHistoryBinding.bind(it) }
    private val adapter by lazy { HistoryAdapter() }
    private val dataList = ArrayList<History>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupUI()
    }

    private fun loadData() {
        dataList.add(History(
            "Player",
            "Player",
            "1 : 0",
            "Player",
        ))

        dataList.add(History(
            "Player",
            "Player",
            "1 : 1",
            "Player",
        ))

        dataList.add(History(
            "Player",
            "Player",
            "0 : 1",
            "Player",
        ))

        dataList.add(History(
            "Player",
            "Player",
            "1 : 0",
            "Player",
        ))
    }

    private fun setupUI() = with(binding) {

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        adapter.submitList(dataList)
        historyRecycler.adapter = adapter
    }
}