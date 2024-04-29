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

//        val tagDrop = view.findViewById<Spinner>(R.id.AddItemDrop)
        ServiceClient.getTagTypes().thenApply { tagTypes ->
            val tagGroup = view.findViewById<CollectionPicker>(R.id.TagGroup)
            val items: MutableList<Item> = ArrayList()
            tagTypes.forEach { tagType ->
                items.add(Item(tagType.id.toString(), tagType.name))
            }
            tagGroup.items = items
            tagGroup.drawItemView()
        }
//            val adapter = ArrayAdapter(
//                view.context,
//                android.R.layout.simple_spinner_item,
//                tagTypes.map { t -> t.name })
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            tagDrop.setAdapter(adapter)
//
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            tagDrop.adapter = adapter
//
//            tagDrop.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
//                ) {
//                    val selectedItem = tagTypes[position]
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    // Handle the case when nothing is selected (optional)
//                }
//            }
//        }

        view.findViewById<Button>(R.id.AddItemScan).setOnClickListener {
            view.findNavController().navigate(R.id.action_addItemFragment_to_scannerFragment)
        }

        view.findViewById<Button>(R.id.AddItem).setOnClickListener {
            val name = view.findViewById<EditText>(R.id.ItemName).text.toString()
            val description = view.findViewById<EditText>(R.id.ItemDescription).text.toString()
            ServiceClient.createItem(name, description, scannedCode).thenApply {
                view.findNavController().popBackStack()
            }
        }
        return view
    }
}