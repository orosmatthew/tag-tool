package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation

class BaldFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bald, container, false)
        view.findViewById<Button>(R.id.BackBald).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_baldFragment_to_errorNotFoundFragment)
        }
        return view
    }


}
