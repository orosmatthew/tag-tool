package site.pixeled.tagtool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class AddingTagsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_adding_tags, container, false)
        val saveButtonView = view.findViewById<Button>(R.id.SaveNewTag)
        val tagTextView = view.findViewById<EditText>(R.id.TagAdd)

        saveButtonView.setOnClickListener {
            val newTag = tagTextView.text.toString()
            ServiceClient.createTagType(newTag).thenApply { res ->
                if (res) {
                    val toast = Toast.makeText(
                        requireContext(), "Tag Created Successfully!", Toast.LENGTH_SHORT
                    )
                    toast.show()
                } else {
                    val toast =
                        Toast.makeText(requireContext(), "Creation Failed!", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        }
        return view
    }
}