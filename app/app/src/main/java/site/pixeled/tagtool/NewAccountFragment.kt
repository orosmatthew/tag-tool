package site.pixeled.tagtool

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation

class NewAccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_new_account, container, false)

        view.findViewById<Button>(R.id.SignInnew).setOnClickListener {

            val username = view.findViewById<EditText>(R.id.UsernameCreate).text.toString()
            val password = view.findViewById<EditText>(R.id.NewPassword1).text.toString()
            val confirmation = view.findViewById<EditText>(R.id.NewPassword2).text.toString()

            if (password == confirmation) {
                ServiceClient.createUser(username, password).thenApply { successful ->
                    if (!successful) {
                        val toast = Toast.makeText(
                            requireContext(), "User already exists!", Toast.LENGTH_LONG
                        )
                        toast.show()
                    } else {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_newAccountFragment_to_welcomeUserFragment)
                    }

                }

            } else {
                val toast =
                    Toast.makeText(requireContext(), "Passwords do not match!", Toast.LENGTH_LONG)
                toast.show()
            }

        }
        return view
    }
}