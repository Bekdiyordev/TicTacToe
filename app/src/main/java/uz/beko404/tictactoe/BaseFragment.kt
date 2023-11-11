package uz.beko404.tictactoe

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class BaseFragment(private val layoutRes: Int) : Fragment() {

    private lateinit var loadingDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun showProgress() {
        if (::loadingDialog.isInitialized && !loadingDialog.isShowing)
            loadingDialog.show()
    }

    fun hideProgress() {
        if (::loadingDialog.isInitialized && loadingDialog.isShowing)
            loadingDialog.dismiss()
    }
}