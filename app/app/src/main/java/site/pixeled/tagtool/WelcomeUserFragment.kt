package site.pixeled.tagtool

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation


class WelcomeUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome_user, container, false)
        view.findViewById<Button>(R.id.MyListButton).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_welcomeUserFragment_to_myListFragment)
        }
        view.findViewById<Button>(R.id.AddNew).setOnClickListener {
            ScannerFragment.lastScanCode = null
            Navigation.findNavController(view)
                .navigate(R.id.action_welcomeUserFragment_to_addItemFragment)
        }
        view.findViewById<Button>(R.id.ScanCodeButton).setOnClickListener {
            ScannerFragment.scanCallback = { code, scanView ->
                ServiceClient.getItems().thenApply { items ->
                    items.find { item -> item.codeData == code }?.let { item ->
                        val bundle = Bundle()
                        bundle.putInt("itemId", item.id)
                        Navigation.findNavController(scanView)
                            .navigate(R.id.action_scannerFragment_to_itemFragment, bundle)
                    }
                }
            }
            Navigation.findNavController(view)
                .navigate(R.id.action_welcomeUserFragment_to_scannerFragment)
        }
       view.findViewById<Button>(R.id.ManageTagsButton).setOnClickListener{
           Navigation.findNavController(view).navigate(R.id.action_welcomeUserFragment_to_tagListFragment2)
       }
        return view
    }
}