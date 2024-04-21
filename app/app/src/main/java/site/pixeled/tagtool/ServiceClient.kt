package site.pixeled.tagtool
import android.app.Activity
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import site.pixeled.tagtool.Models.AuthRes
import site.pixeled.tagtool.Models.Item
import java.util.concurrent.CompletableFuture

object ServiceClient {
    private lateinit var queue: RequestQueue

    public fun initQueue(mainActivity: Activity) {
        queue = Volley.newRequestQueue(mainActivity)
    }

    private fun sendRequest(request: Request<JSONObject>) {
        queue.add(request)
    }

    fun authUser(username: String, password: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject();
        json.put("username", username);
        json.put("password", password);

        val request = JsonObjectRequest(Request.Method.POST,
            "https://mopsdev.bw.edu/~moros20/tag-tool/rest.php/Auth",
            json,
            { res ->
                val userId = Gson().fromJson(res.get("data").toString(), AuthRes::class.java).userId
                future.complete(userId != null)
            },
            { err ->
                Log.e("Auth failed", err.toString())
                future.completeExceptionally(err)
            })
        sendRequest(request)
        return future
    }

    fun getItem(id: Int): CompletableFuture<Item> {
        val future = CompletableFuture<Item>()
        val request = AuthRequest(Request.Method.GET,
            "https://mopsdev.bw.edu/~moros20/tag-tool/rest.php/Item/$id",
            null,
            { res ->
                val item = Gson().fromJson(res.get("data").toString(), Item::class.java)
                future.complete(item)
            },
            { err ->
                Log.e("Auth failed", err.toString())
                future.completeExceptionally(err)
            })
        return future
    }
}
