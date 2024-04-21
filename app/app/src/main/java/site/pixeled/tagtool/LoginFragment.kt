package site.pixeled.tagtool

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import org.json.JSONObject
import site.pixeled.tagtool.AuthRequest.Companion.password
import site.pixeled.tagtool.AuthRequest.Companion.username


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        ServiceClient.initQueue(requireActivity())
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.findViewById<Button>(R.id.LogInButton).setOnClickListener(View.OnClickListener {

            val username = view.findViewById<EditText>(R.id.Username).text.toString()
            val password = view.findViewById<EditText>(R.id.Password).text.toString()

            ServiceClient.authUser(username, password).thenApply { isValid ->
                if (isValid) {
                    AuthRequest.username = username
                    AuthRequest.password = password
                    val toast =
                        Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT)
                    toast.show()
                } else {
                    val toast =
                        Toast.makeText(requireContext(), "Invalid Login!", Toast.LENGTH_SHORT)
                    toast.show()
                }

            }
        })
        return view
    }
}


