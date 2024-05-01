package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
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
                    val foundItem = items.find { item -> item.codeData == code }
                    foundItem?.let { item ->
                        val bundle = Bundle()
                        bundle.putInt("itemId", item.id)
                        Navigation.findNavController(scanView)
                            .navigate(R.id.action_scannerFragment_to_itemFragment, bundle)
                    }
                    if (foundItem == null) {
                        Navigation.findNavController(scanView)
                            .navigate(R.id.action_scannerFragment_to_errorNotFoundFragment)
                    }
                }
            }
            Navigation.findNavController(view)
                .navigate(R.id.action_welcomeUserFragment_to_scannerFragment)
        }
        view.findViewById<Button>(R.id.ManageTagsButton).setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_welcomeUserFragment_to_tagListFragment2)
        }

        view.findViewById<Button>(R.id.DeleteAccountButton).setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle("Delete Account")
            dialog.setMessage("Are you sure you want to delete your account?")
            dialog.setPositiveButton("Yes") { _, _ ->
                ServiceClient.deleteUser().thenApply {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_welcomeUserFragment_to_loginFragment)
                }
            }
            dialog.setNegativeButton("No", null)
            dialog.show()
        }
        view.findViewById<Button>(R.id.LogoutButton).setOnClickListener {
            AuthRequest.username = ""
            AuthRequest.password = ""
            Navigation.findNavController(view)
                .navigate(R.id.action_welcomeUserFragment_to_loginFragment)
        }
        return view
    }
}