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

    // reified to keep generic info at compile-time which also requires it to be inline
    private inline fun <reified T> gsonParseApiResponse(json: JSONObject): ApiResponse<T> {
        // magic to handle lack of generic type usage directly in gson
        val type = object : TypeToken<ApiResponse<T>>() {}.type
        return Gson().fromJson(json.toString(), type)
    }

    private fun gsonParseApiResponse(json: JSONObject): ApiResponse<Unit> {
        // magic to handle lack of generic type usage directly in gson
        val type = object : TypeToken<ApiResponse<Unit>>() {}.type
        return Gson().fromJson(json.toString(), type)
    }

    fun authUser(username: String, password: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)

        val request = JsonObjectRequest(Request.Method.POST,
            "https://mopsdev.bw.edu/~moros20/tag-tool/rest.php/Auth",
            json,
            { res ->
                val userId = gsonParseApiResponse<AuthResponseData>(res).data.userId
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
                val item = gsonParseApiResponse<Item>(res).data
                future.complete(item)
            },
            { err ->
                Log.e("Get failed", err.toString())
                future.completeExceptionally(err)
            })
        sendRequest(request)
        return future
    }

    fun createItem(name: String, description: String?, codeData: String?): CompletableFuture<Int> {
        val future = CompletableFuture<Int>()
        val json = JSONObject()
        json.put("name", name)
        json.put("description", description)
        json.put("codeData", codeData)
        val request = AuthRequest(Request.Method.POST,
            "https://mopsdev.bw.edu/~moros20/tag-tool/reset.php/Item",
            json,
            { res ->
                val status = gsonParseApiResponse(res).status
                future.complete(status)
            },
            { err ->
                Log.e("Post failed", err.toString())
                future.completeExceptionally(err)
            })
        sendRequest(request)
        return future
    }

    fun deleteItem(id: Int): CompletableFuture<Int> {
        val future = CompletableFuture<Int>()
        val request = AuthRequest(Request.Method.DELETE,
            "https://mopsdev.bw.edu/~moros20/tag-tool/reset.php/Item/$id",
            null,
            { res ->
                val status = gsonParseApiResponse(res).status
                future.complete(status)
            },
            { err ->
                Log.e("Delete failed", err.toString())
                future.completeExceptionally(err)
            })
        sendRequest(request)
        return future
    }

    fun updateItem(
        id: Int,
        name: String?,
        description: String?,
        codeData: String?
    ): CompletableFuture<Int> {
        val future = CompletableFuture<Int>()
        val json = JSONObject()
        json.put("id", id)
        json.put("name", name)
        json.put("description", description)
        json.put("codeData", codeData)
        val request = AuthRequest(Request.Method.PUT,
            "https://mopsdev.bw.edu/~moros20/tag-tool/reset.php/Item/$id",
            json,
            { res ->
                val status = gsonParseApiResponse(res).status
                future.complete(status)
            },
            { err ->
                Log.e("Put failed", err.toString())
                future.completeExceptionally(err)
            })
        sendRequest(request)
        return future
    }

    fun getItem(): CompletableFuture<Array<Item>> {
        val future = CompletableFuture<Array<Item>>()
        val request = AuthRequest(Request.Method.PUT,
            "https://mopsdev.bw.edu/~moros20/tag-tool/reset.php/Items",
            null,
            { res ->
                val items = gsonParseApiResponse<Array<Item>>(res).data
                future.complete(items)
            },
            { err ->
                Log.e("Put failed", err.toString())
                future.completeExceptionally(err)
            })
        sendRequest(request)
        return future
    }
}
