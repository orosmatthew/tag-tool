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
import com.anton46.collectionitempicker.CollectionPicker
import com.anton46.collectionitempicker.Item


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
        val tagGroup = view.findViewById<CollectionPicker>(R.id.ItemTagGroup)
        itemId?.let { id ->

            ServiceClient.getItem(id).thenApply { item ->
                item?.let {
                    view.findViewById<TextView>(R.id.SelectedItemName).text = it.name
                    description.setText(it.description ?: "")
                    view.findViewById<Button>(R.id.SelectedItemEdit).setOnClickListener {
                        ServiceClient.updateItem(id, item.name, description.text.toString(), null)
                        ServiceClient.getTags().thenApply { currentTags ->
                            val currentItemTags = currentTags.filter { t -> t.itemId == id }
                            tagGroup.items.forEach { tagGroupItem ->
                                val currentTag =
                                    currentItemTags.find { t -> t.tagTypeId == tagGroupItem.id.toInt() }
                                if (tagGroupItem.isSelected && currentTag == null) {
                                    ServiceClient.createTag(tagGroupItem.id.toInt(), id)
                                } else if (!tagGroupItem.isSelected && currentTag != null) {
                                    ServiceClient.deleteTag(currentTag.id)
                                }
                            }
                        }
                    }
                }
            }

            ServiceClient.getTags().thenApply { tags ->
                val itemTags = tags.filter { t -> t.itemId == id }

                ServiceClient.getTagTypes().thenApply { tagTypes ->
                    tagTypes.forEach { tagType ->
                        val item = Item(tagType.id.toString(), tagType.name)
                        item.isSelected = itemTags.find { t -> t.tagTypeId == tagType.id } != null
                        tagGroup.items.add(item)
                    }
                    tagGroup.drawItemView()
                }
            }

            view.findViewById<Button>(R.id.SelectedItemDelete).setOnClickListener {
                ServiceClient.deleteItem(id).thenApply {
                    view.findNavController().popBackStack()
                }
            }
            view.findViewById<Button>(R.id.BackfromItem).setOnClickListener {
                view.findNavController().navigate(R.id.action_itemFragment_to_myListFragment)
            }
        }
        return view
    }
}