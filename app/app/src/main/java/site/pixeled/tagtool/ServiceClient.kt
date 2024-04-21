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
                val status = res.getInt("status")
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
                val status = res.getInt("status")
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
                val status = res.getInt("status")
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
                val items = Gson().fromJson(res.get("data").toString(), Array<Item>::class.java)
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
