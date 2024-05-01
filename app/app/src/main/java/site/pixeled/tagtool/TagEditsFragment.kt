package site.pixeled.tagtool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class TagEditsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tag_edits, container, false)
        arguments?.let {
            val tagId = it.getInt("tagId")
            val tagName = it.getString("tagName")
            view.findViewById<EditText>(R.id.TagName).setText(tagName)

            view.findViewById<Button>(R.id.SaveTag).setOnClickListener {
                val newName = view.findViewById<EditText>(R.id.TagName).text.toString()
                ServiceClient.updateTagType(tagId, newName).thenApply { res ->
                    if (res) {
                        val toast =
                            Toast.makeText(requireContext(), "Tag Updated!", Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        val toast =
                            Toast.makeText(requireContext(), "Update Failed!", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }

            }

            view.findViewById<Button>(R.id.DeleteTag).setOnClickListener {
                ServiceClient.deleteTagType(tagId).thenApply { res ->
                    if (res) {
                        val toast =
                            Toast.makeText(requireContext(), "Tag Deleted!", Toast.LENGTH_SHORT)
                        toast.show()
                    } else {
                        val toast =
                            Toast.makeText(requireContext(), "Delete Failed!", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                }
                view.findViewById<EditText>(R.id.TagName).setText("")
                val toast = Toast.makeText(requireContext(), "Tag Deleted!", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        view.findViewById<Button>(R.id.backtoTags).setOnClickListener {
            view.findNavController().navigate(R.id.action_tagEditsFragment_to_tagListFragment)
        }


        return view
    }
}