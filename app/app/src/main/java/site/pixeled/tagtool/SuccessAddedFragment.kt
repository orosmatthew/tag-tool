package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class SuccessAddedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_success_added, container, false)
        view.findViewById<Button>(R.id.HomeSuccess).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_successAddedFragment_to_welcomeUserFragment)
        }
        view.findViewById<Button>(R.id.AddAnother).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_successAddedFragment_to_addItemFragment)
        }
        return view
    }
}