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

class TagListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tag_list, container, false)
        view.findViewById<Button>(R.id.HomeAgainTagList).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_tagListFragment_to_welcomeUserFragment)
        }
        view.findViewById<Button>(R.id.Addtag).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_tagListFragment_to_addingTagsFragment)
        }
        val tagList = view.findViewById<RecyclerView>(R.id.tag_list)
        ServiceClient.getTagTypes().thenApply {tags ->
            tagList.layoutManager = LinearLayoutManager(context)
            tagList.adapter = TagRecyclerViewAdapter(tags) { row ->
                val bundle = Bundle()
                bundle.putInt("tagId", tags[row].id)
                bundle.putString("tagName", tags[row].name)
                Navigation.findNavController(view)
                    .navigate(R.id.action_tagListFragment_to_tagEditsFragment, bundle)
            }
        }
        view.findViewById<Button>(R.id.HomeAgainTagList).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_tagListFragment_to_welcomeUserFragment)
        }
        view.findViewById<Button>(R.id.Addtag).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_tagListFragment_to_addingTagsFragment)
        }

        return view
    }

}