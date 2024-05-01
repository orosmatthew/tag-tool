package site.pixeled.tagtool

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class AddingTagsFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adding_tags, container, false)

        view.findViewById<Button>(R.id.SaveNewTag).setOnClickListener{
            val newTag = view.findViewById<EditText>(R.id.TagAdd).text.toString()
            ServiceClient.createTagType(newTag).thenApply { res->
                if(res)
                {
                    val toast = Toast.makeText(requireContext(), "Tag Created Successfully!", Toast.LENGTH_SHORT)
                    toast.show()
                }

                else
                {
                    val toast = Toast.makeText(requireContext(), "Creation Failed!", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }

        }
        return view;
    }
}