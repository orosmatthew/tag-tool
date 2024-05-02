package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.anton46.collectionitempicker.CollectionPicker
import site.pixeled.tagtool.model.Item
import site.pixeled.tagtool.model.Tag
import site.pixeled.tagtool.model.TagType

class ItemFragment : Fragment() {

    private var itemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = arguments?.getInt("itemId")
    }

    private fun setupSaveButton(
        item: Item,
        buttonView: Button,
        descriptionView: EditText,
        tagGroupView: CollectionPicker
    ) {
        buttonView.setOnClickListener {
            ServiceClient.updateItem(
                id,
                item.name,
                descriptionView.text.toString(),
                item.codeData
            )
            ServiceClient.getTags().thenApply { currentTags ->
                val currentItemTags = currentTags.filter { t -> t.itemId == id }
                tagGroupView.items.forEach { tagGroupItem ->
                    val currentTag =
                        currentItemTags.find { t -> t.tagTypeId == tagGroupItem.id.toInt() }
                    if (tagGroupItem.isSelected && currentTag == null) {
                        ServiceClient.createTag(tagGroupItem.id.toInt(), id)
                    } else if (!tagGroupItem.isSelected && currentTag != null) {
                        ServiceClient.deleteTag(currentTag.id)
                    }
                }
                Toast.makeText(context, "Item updated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initTags(
        itemId: Int,
        tags: Array<Tag>,
        tagTypes: Array<TagType>,
        tagGroupView: CollectionPicker
    ) {
        val itemTags = tags.filter { t -> t.itemId == itemId }
        tagTypes.forEach { tagType ->
            val item = com.anton46.collectionitempicker.Item(
                tagType.id.toString(),
                tagType.name
            )
            item.isSelected = itemTags.find { t -> t.tagTypeId == tagType.id } != null
            tagGroupView.items.add(item)
        }
        tagGroupView.drawItemView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item, container, false)
        val descriptionView = view.findViewById<EditText>(R.id.SelectedItemDescription)
        val tagGroupView = view.findViewById<CollectionPicker>(R.id.ItemTagGroup)
        val saveButtonView = view.findViewById<Button>(R.id.SelectedItemEdit)
        val itemNameView = view.findViewById<TextView>(R.id.SelectedItemName)
        val deleteButtonView = view.findViewById<Button>(R.id.SelectedItemDelete)
        val backButtonView = view.findViewById<Button>(R.id.BackfromItem)

        itemId?.let { itemId ->
            ServiceClient.getItem(itemId).thenApply { itemNullable ->
                itemNullable?.let { item ->
                    itemNameView.text = item.name
                    descriptionView.setText(item.description ?: "")
                    setupSaveButton(item, saveButtonView, descriptionView, tagGroupView)
                }
            }

            ServiceClient.getTags().thenApply { tags ->
                ServiceClient.getTagTypes().thenApply { tagTypes ->
                    initTags(itemId, tags, tagTypes, tagGroupView)
                }
            }

            deleteButtonView.setOnClickListener {
                ServiceClient.deleteItem(itemId).thenApply {
                    view.findNavController().popBackStack()
                }
            }

            backButtonView.setOnClickListener {
                view.findNavController().navigate(R.id.action_itemFragment_to_myListFragment)
            }
        }
        return view
    }
}