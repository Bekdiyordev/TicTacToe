package uz.beko404.tictactoe.ui

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.navigation.fragment.findNavController
import uz.beko404.tictactoe.BaseFragment
import uz.beko404.tictactoe.R
import uz.beko404.tictactoe.databinding.FragmentHomeBinding
import uz.beko404.tictactoe.utils.viewBinding

class Home : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() = with(binding){

        multiplayer.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_game)
        }

        cpu.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_game)
        }

//        setting.setOnClickListener {
//            findNavController().navigate(R.id.action_home2_to_settings2)
//            constraintLayout.transitionToEnd()
//        }

//        constraintLayout.setTransitionListener(object : MotionLayout.TransitionListener {
//            override fun onTransitionStarted(
//                motionLayout: MotionLayout,
//                startId: Int,
//                endId: Int
//            ) {
//                // Transition boshlandi
//            }
//
//            override fun onTransitionChange(
//                motionLayout: MotionLayout,
//                startId: Int,
//                endId: Int,
//                progress: Float
//            ) {
//                // Transition o'zgarayapti
//            }
//
//            override fun onTransitionCompleted(
//                motionLayout: MotionLayout,
//                currentId: Int
//            ) {
//                // Agar animatsiya tugatilgan bo'lsa, animatsiyalarni o'zgartirish
//                when (currentId) {
//                    R.id.end -> {
//                        constraintLayout.transitionToState(R.id.history)
//                    }
//
//                    R.id.history -> constraintLayout.transitionToState(R.id.profile)
//
//                    R.id.profile -> constraintLayout.transitionToState(R.id.theme)
//
//                    R.id.theme -> constraintLayout.transitionToState(R.id.rate)
//
//                }
//    }
//
//            override fun onTransitionTrigger(
//                motionLayout: MotionLayout?,
//                triggerId: Int,
//                positive: Boolean,
//                progress: Float
//            ) {
//                TODO("Not yet implemented")
//            }
//
//        })

    }
}