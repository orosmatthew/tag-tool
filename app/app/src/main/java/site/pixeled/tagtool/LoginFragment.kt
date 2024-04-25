package site.pixeled.tagtool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        ServiceClient.initQueue(requireActivity())
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<Button>(R.id.LogInButton).setOnClickListener {

            val username = view.findViewById<EditText>(R.id.Username).text.toString()
            val password = view.findViewById<EditText>(R.id.Password).text.toString()

            ServiceClient.authUser(username, password).thenApply { userId ->
                userId?.let {
                    AuthRequest.username = username
                    AuthRequest.password = password
                    AuthRequest.userId = it
                    Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_welcomeUserFragment)
                }
                if (userId == null) {
                    val toast =
                        Toast.makeText(requireContext(), "Invalid Login!", Toast.LENGTH_SHORT)
                    toast.show()
                }

            }
        }
        return view
    }
}


