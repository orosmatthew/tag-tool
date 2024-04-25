package site.pixeled.tagtool

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.util.Base64

class AuthRequest
    (
    method: Int,
    url: String?,
    jsonRequest: JSONObject?,
    listener: Response.Listener<JSONObject>?,
    errorListener: Response.ErrorListener?
) : JsonObjectRequest(method, url, jsonRequest, listener, errorListener) {

    companion object {
        var username = ""
        var password = ""
        var userId: Int? = null
    }

    override fun getHeaders(): MutableMap<String, String> {
        val headers = HashMap<String, String>()

        val credential = String.format("%s:%s", username, password)
        val encode = Base64.encodeToString(credential.toByteArray(), 0)

        headers["Authorization"] = "Basic $encode"
        return headers
    }
}