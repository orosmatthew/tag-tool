package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController


class ItemFragment : Fragment() {

    private var itemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = arguments?.getInt("itemId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        val description = view.findViewById<EditText>(R.id.SelectedItemDescription)
        itemId?.let { id ->
            ServiceClient.getItem(id).thenApply { item ->
                item?.let {
                    view.findViewById<TextView>(R.id.SelectedItemName).text = it.name
                    description.setText(it.description ?: "")
                    view.findViewById<Button>(R.id.SelectedItemEdit).setOnClickListener {
                        ServiceClient.updateItem(id, item.name, description.text.toString(), null)
                    }
                }
            }
            view.findViewById<Button>(R.id.SelectedItemDelete).setOnClickListener {
                ServiceClient.deleteItem(id).thenApply {
                    view.findNavController().popBackStack()
                }
            }
            view?.findViewById<Button>(R.id.BackfromItem)?.setOnClickListener {
                view?.findNavController()?.navigate(R.id.action_itemFragment_to_myListFragment)
            }
        }
        return view
    }
}