package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anton46.collectionitempicker.CollectionPicker
import site.pixeled.tagtool.model.Item
import site.pixeled.tagtool.model.Tag

class MyListFragment : Fragment() {

    private fun filterItems(
        view: View,
        items: Array<Item>,
        tags: Array<Tag>,
        itemList: RecyclerView,
        tagGroup: CollectionPicker
    ) {
        val itemIdToTagTypeIds = mutableMapOf<Int, Set<Int>>()
        items.forEach { item ->
            itemIdToTagTypeIds[item.id] =
                tags.filter { it.itemId == item.id }.map { it.tagTypeId }.toSet()
        }
        val selectedTagTypeIds =
            tagGroup.items.filter { it.isSelected }.map { it.id.toInt() }.toSet()
        val filteredItems: List<Item> =
            if (selectedTagTypeIds.isEmpty()) items.asList() else items.filter { item ->
                itemIdToTagTypeIds[item.id]!!.intersect(selectedTagTypeIds).isNotEmpty()
            }.sortedBy { it.name.lowercase() }
        itemList.adapter = ItemRecyclerViewAdapter(filteredItems) { row ->
            val bundle = Bundle()
            bundle.putInt("itemId", filteredItems[row].id)
            Navigation.findNavController(view)
                .navigate(R.id.action_myListFragment_to_itemFragment, bundle)
        }
    }

    private fun initTagGroup(view: View) {
        val tagGroup = view.findViewById<CollectionPicker>(R.id.MyListTags)
        ServiceClient.getTagTypes().thenApply { tagTypes ->
            tagTypes.forEach { tagType ->
                val item =
                    com.anton46.collectionitempicker.Item(tagType.id.toString(), tagType.name)
                tagGroup.items.add(item)
            }
            tagGroup.drawItemView()
        }
    }

    private fun initItemList(view: View, items: Array<Item>) {
        val itemList = view.findViewById<RecyclerView>(R.id.ItemList)
        itemList.layoutManager = LinearLayoutManager(context)
        val sortedItems = items.sortedBy { it.name.lowercase() }
        itemList.adapter = ItemRecyclerViewAdapter(sortedItems) { row ->
            val bundle = Bundle()
            bundle.putInt("itemId", sortedItems[row].id)
            Navigation.findNavController(view)
                .navigate(R.id.action_myListFragment_to_itemFragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_list, container, false)
        initTagGroup(view)

        val itemList = view.findViewById<RecyclerView>(R.id.ItemList)

        var cachedItems: Array<Item>? = null
        var cachedTags: Array<Tag>? = null
        ServiceClient.getItems().thenApply { items ->
            cachedItems = items
            initItemList(view, items)
        }
        ServiceClient.getTags().thenApply { tags ->
            cachedTags = tags
        }

        val tagGroup = view.findViewById<CollectionPicker>(R.id.MyListTags)
        tagGroup.setOnItemClickListener { _, _ ->
            cachedItems?.let { items ->
                cachedTags?.let { tags ->
                    filterItems(
                        view, items, tags, itemList, tagGroup
                    )
                }
            }
        }

        view.findViewById<Button>(R.id.HomeAgainTagList).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_myListFragment_to_welcomeUserFragment)
        }
        return view
    }
}