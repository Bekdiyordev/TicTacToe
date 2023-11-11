package uz.beko404.tictactoe.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.models.OnBoarding

class OnBoardingAdapter(
    private val context: Context,
    private val dataList: MutableList<OnBoarding>
) : PagerAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun isViewFromObject(view: View, image: Any): Boolean {
        return view == image
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboarding_item, container, false)
        val data = dataList[position]
        view.findViewById<ImageView>(R.id.image).setImageResource(data.image)
        view.findViewById<TextView>(R.id.title).text = data.title
        view.findViewById<TextView>(R.id.desc).text = data.desc

        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View?)
    }
}