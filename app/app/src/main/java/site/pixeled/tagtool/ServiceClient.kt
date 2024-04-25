package site.pixeled.tagtool

import android.app.Activity
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import site.pixeled.tagtool.model.ApiResponse
import site.pixeled.tagtool.model.AuthResponseData
import site.pixeled.tagtool.model.Item
import java.util.concurrent.CompletableFuture

object ServiceClient {
    private var apiUrl = "https://mopsdev.bw.edu/~moros20/tag-tool/rest.php"
    private lateinit var queue: RequestQueue
    private var initialized = false

    fun initQueue(mainActivity: Activity) {
        if (!initialized) {
            queue = Volley.newRequestQueue(mainActivity)
            initialized = true
        }
    }

    private fun sendRequest(request: Request<JSONObject>) {
        queue.add(request)
    }

    // reified to keep generic info at runtime which also requires it to be inline
    private inline fun <reified T> gsonParseApiResponse(json: JSONObject): ApiResponse<T> {
        // magic to handle lack of generic type usage directly in gson
        val type = object : TypeToken<ApiResponse<T>>() {}.type
        return Gson().fromJson(json.toString(), type)
    }

    fun authUser(username: String, password: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)

        val request = JsonObjectRequest(Request.Method.POST, "$apiUrl/Auth", json, { res ->
            val userId = gsonParseApiResponse<AuthResponseData>(res).data.userId
            future.complete(userId != null)
        }, { err ->
            Log.e("POST Auth", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getItem(id: Int): CompletableFuture<Item?> {
        val future = CompletableFuture<Item?>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/Item/$id", null, { res ->
            val item = gsonParseApiResponse<Array<Item>>(res).data
            future.complete(item.firstOrNull())
        }, { err ->
            Log.e("GET Item", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun createItem(
        name: String, description: String?, codeData: String?
    ): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("name", name)
        json.put("description", description)
        json.put("codeData", codeData)
        val request = AuthRequest(Request.Method.POST, "$apiUrl/Item", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("POST Item", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun deleteItem(id: Int): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val request = AuthRequest(Request.Method.DELETE, "$apiUrl/Item/$id", null, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("DELETE Item", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun updateItem(
        id: Int, name: String?, description: String?, codeData: String?
    ): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("id", id)
        json.put("name", name)
        json.put("description", description)
        json.put("codeData", codeData)
        val request = AuthRequest(Request.Method.PUT, "$apiUrl/Item/$id", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("PUT Item", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getItems(): CompletableFuture<Array<Item>> {
        val future = CompletableFuture<Array<Item>>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/Items", null, { res ->
            val items = gsonParseApiResponse<Array<Item>>(res).data
            future.complete(items)
        }, { err ->
            Log.e("GET Items", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }
}
