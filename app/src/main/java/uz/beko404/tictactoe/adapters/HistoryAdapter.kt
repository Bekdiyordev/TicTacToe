package uz.beko404.tictactoe.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.beko404.tictactoe.databinding.HistoryCardItemBinding
import uz.beko404.tictactoe.models.History
import uz.beko404.tictactoe.utils.HistoryItem

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val dif = AsyncListDiffer(this, ITEM_DIFF)
    var position = 0

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, parent.context, parent)
    }

    override fun getItemCount(): Int = dif.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.setContent(position)

    inner class ViewHolder(
        private val binding: HistoryCardItemBinding,
        val context: Context,
        val parent: ViewGroup
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun setContent(position: Int) {
            binding.apply {
                for (i in 0 until 4) {
                    val myCustomItemView = HistoryItem(context)
                    myCustomItemView.setData("Player $i", "1 : 0", "Player $i")
                    linear.addView(myCustomItemView)
                    if (i != 3) {
                        val line = LinearLayout(context)
                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            1)
//                        params.weight = 1f //
//                        params.height = 0.5.toInt()
                        line.layoutParams = params
                        line.setBackgroundColor(Color.parseColor("#E1E1E1"))
                        linear.addView(line)
                    }
                }

            }
        }
    }

    fun submitList(history: List<History>) = dif.submitList(history)

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<History>() {
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean =
                oldItem.date == newItem.date

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean =
                oldItem == newItem
        }
    }
}