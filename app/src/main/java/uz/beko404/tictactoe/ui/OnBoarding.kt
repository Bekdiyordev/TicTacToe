package uz.beko404.tictactoe.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.adapters.OnBoardingAdapter
import uz.beko404.tictactoe.databinding.FragmentOnBoardingBinding
import uz.beko404.tictactoe.models.OnBoarding
import uz.beko404.tictactoe.utils.viewBinding


class OnBoarding : BaseFragment(R.layout.fragment_on_boarding) {
    private val binding by viewBinding { FragmentOnBoardingBinding.bind(it) }
    private var pagerItems: MutableList<OnBoarding> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPageContent()
        setupUI()
    }

    private fun setupUI() = with(binding) {

        viewPager.addOnPageChangeListener(makePageListener())

        viewPager.adapter = OnBoardingAdapter(requireContext(), pagerItems)
        viewPager.offscreenPageLimit = pagerItems.size
        indicator.attachTo(binding.viewPager)

        back.setOnClickListener {
            backPage()
        }

        next.setOnClickListener {
            nextPage()
        }

    }

    private fun nextPage() = with(binding) {
        back.isEnabled = true
        if (viewPager.currentItem == 2) {
            findNavController().navigate(R.id.action_onBoarding_to_home2)
        } else {
            viewPager.currentItem++
        }
    }

    private fun backPage() = with(binding) {
        if (viewPager.currentItem == 0) {
            back.isEnabled = false
        } else {
            back.isEnabled = true
            viewPager.currentItem--
        }
    }

    private fun makePageListener(): ViewPager.OnPageChangeListener {
        return object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) { }

            override fun onPageSelected(position: Int) {
                binding.back.isEnabled = position != 0
            }
        }
    }

    private fun loadPageContent() {
        pagerItems.clear()
        pagerItems.add(
            OnBoarding(
                R.drawable.ic_onboarding_1,
                getString(R.string.title1),
                getString(R.string.desc1)
            )
        )
        pagerItems.add(
            OnBoarding(
                R.drawable.ic_onboarding_2,
                getString(R.string.title2),
                getString(R.string.desc2)
            )
        )
        pagerItems.add(
            OnBoarding(
                R.drawable.ic_onboarding_3,
                getString(R.string.title3),
                getString(R.string.desc3)
            )
        )
    }
}