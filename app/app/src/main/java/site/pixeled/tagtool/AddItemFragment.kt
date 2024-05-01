package site.pixeled.tagtool

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.anton46.collectionitempicker.CollectionPicker
import com.anton46.collectionitempicker.Item


class AddItemFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_item, container, false)

        val codeTextView = view.findViewById<TextView>(R.id.AddItemCode)
        codeTextView.visibility = INVISIBLE
        var scannedCode: String? = null

        ScannerFragment.lastScanCode?.let { code ->
            scannedCode = code
            codeTextView.text = "Scanned: $code"
            codeTextView.visibility = VISIBLE
            ScannerFragment.lastScanCode = null
        }

        ScannerFragment.scanCallback = { _, scanView ->
            Navigation.findNavController(scanView).popBackStack()
        }

        val tagGroup = view.findViewById<CollectionPicker>(R.id.TagGroup)
        ServiceClient.getTagTypes().thenApply { tagTypes ->
            tagTypes.forEach { tagType ->
                tagGroup.items.add(Item(tagType.id.toString(), tagType.name))
            }
            tagGroup.drawItemView()
        }

        view.findViewById<Button>(R.id.AddItemScan).setOnClickListener {
            view.findNavController().navigate(R.id.action_addItemFragment_to_scannerFragment)
        }
        view.findViewById<Button>(R.id.BackhomefromAdd).setOnClickListener {
            view.findNavController().navigate(R.id.action_addItemFragment_to_welcomeUserFragment)
        }


        view.findViewById<Button>(R.id.AddItem).setOnClickListener {
            val name = view.findViewById<EditText>(R.id.ItemName).text.toString()
            val description = view.findViewById<EditText>(R.id.ItemDescription).text.toString()

            ServiceClient.createItem(name, description, scannedCode).thenApply { itemId ->
                itemId?.let {
                    val selectedIds =
                        tagGroup.items.filter { i -> i.isSelected }.map { i -> i.id.toInt() }
                    selectedIds.forEach { id ->
                        ServiceClient.createTag(id, it)
                    }
                }

                view.findNavController().popBackStack()
            }


        }
        return view
    }
}