package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.navigation.Navigation


class EasterEggFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_easter_egg, container, false)
        view.findViewById<Button>(R.id.EasterBackOne).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_easterEggFragment_to_errorNotFoundFragment)
        }
        view.findViewById<ImageButton>(R.id.Emobutton).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_easterEggFragment_to_baldFragment)
        }
        return view
    }
}