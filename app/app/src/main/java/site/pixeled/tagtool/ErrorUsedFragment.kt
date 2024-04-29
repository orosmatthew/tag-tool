package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class ErrorUsedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_error_used, container, false)
        view.findViewById<Button>(R.id.ScanAgain).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_errorUsedFragment_to_scannerFragment)
        }
        view.findViewById<Button>(R.id.ReturnHome).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_errorUsedFragment_to_welcomeUserFragment)
        }
        return view
    }
}