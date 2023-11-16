package uz.beko404.tictactoe.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.beko404.tictactoe.databinding.HistoryCardItemBinding
import uz.beko404.tictactoe.models.HistoryItem
import uz.beko404.tictactoe.utils.HistoryItemView

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount(): Int = dif.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setContent(position)

    inner class ViewHolder(
        private val binding: HistoryCardItemBinding,
        val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun setContent(position: Int) {
            val data = dif.currentList[position]
            binding.apply {
                date.text = data[0].date
                for (item in data) {
                    val myCustomItemView = HistoryItemView(context)
                    myCustomItemView.setData(item.playerName, item.result, item.opponentName)
                    linear.addView(myCustomItemView)
                    if (item != data.last()) {
                        val line = LinearLayout(context)
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, 1)
                        line.layoutParams = params
                        line.setBackgroundColor(Color.parseColor("#E1E1E1"))
                        linear.addView(line)
                    }
                }
            }
        }
    }

    fun submitList(historyItem: List<List<HistoryItem>>) = dif.submitList(historyItem)

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<List<HistoryItem>>() {
            override fun areItemsTheSame(oldItem: List<HistoryItem>, newItem: List<HistoryItem>): Boolean =
                oldItem[0].date == newItem[0].date

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: List<HistoryItem>, newItem: List<HistoryItem>): Boolean =
                oldItem == newItem
        }
    }
}