package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class ErrorNotFoundFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_error_not_found, container, false)
        view.findViewById<Button>(R.id.easterButton).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_errorNotFoundFragment_to_easterEggFragment)
        }
        view.findViewById<Button>(R.id.AddItemError).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_errorNotFoundFragment_to_addItemFragment)
        }
        view.findViewById<Button>(R.id.ScanAgainError).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_errorNotFoundFragment_to_scannerFragment)
        }
        view.findViewById<Button>(R.id.HomeFromError).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_errorNotFoundFragment_to_welcomeUserFragment)
        }
        return view
    }

}