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

class MyListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_list, container, false)
        view.findViewById<Button>(R.id.HomeAgainList).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_myListFragment_to_welcomeUserFragment)
        }
        val itemList = view.findViewById<RecyclerView>(R.id.item_list);
        ServiceClient.getItems().thenApply { items ->
            itemList.layoutManager = LinearLayoutManager(context)
            itemList.adapter = ItemRecyclerViewAdapter(items, null)
        }
        return view
    }
}