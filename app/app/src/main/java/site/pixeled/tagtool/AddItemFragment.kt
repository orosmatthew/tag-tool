package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController


class AddItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)
        view.findViewById<Button>(R.id.AddItem).setOnClickListener {
            val name = view.findViewById<EditText>(R.id.ItemName).text.toString()
            val description = view.findViewById<EditText>(R.id.ItemDescription).text.toString()
            ServiceClient.createItem(name, description, null).thenApply {
                view.findNavController().popBackStack()
            }
        }
        return view
    }
}