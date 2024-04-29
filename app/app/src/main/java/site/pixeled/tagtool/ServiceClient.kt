@file:Suppress("unused")

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
import site.pixeled.tagtool.model.Note
import site.pixeled.tagtool.model.Tag
import site.pixeled.tagtool.model.TagType
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

    fun authUser(username: String, password: String): CompletableFuture<Int?> {
        val future = CompletableFuture<Int?>()
        val json = JSONObject()
        json.put("username", username)
        json.put("password", password)

        val request = JsonObjectRequest(Request.Method.POST, "$apiUrl/Auth", json, { res ->
            val userId = gsonParseApiResponse<AuthResponseData>(res).data.userId
            future.complete(userId)
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
        json.put("codeData", codeData ?: JSONObject.NULL)
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

    fun createNote(content: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("content", content)
        val request = AuthRequest(Request.Method.POST, "$apiUrl/Note", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("POST Note", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getNote(id: Int): CompletableFuture<Note?> {
        val future = CompletableFuture<Note?>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/Note/$id", null, { res ->
            val note = gsonParseApiResponse<Array<Note>>(res).data
            future.complete(note.firstOrNull())
        }, { err ->
            Log.e("GET Note", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun updateNote(
        id: Int?, content: String?
    ): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("id", id)
        json.put("content", content)
        val request = AuthRequest(Request.Method.PUT, "$apiUrl/Note/$id", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("PUT Note", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun deleteNote(id: Int): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val request = AuthRequest(Request.Method.DELETE, "$apiUrl/Note/$id", null, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("DELETE Note", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getNotes(): CompletableFuture<Array<Note>> {
        val future = CompletableFuture<Array<Note>>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/Notes", null, { res ->
            val notes = gsonParseApiResponse<Array<Note>>(res).data
            future.complete(notes)
        }, { err ->
            Log.e("GET Notes", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun createTagType(name: String): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("name", name)
        val request = AuthRequest(Request.Method.POST, "$apiUrl/TagType", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("POST TagType", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getTagType(id: Int): CompletableFuture<TagType?> {
        val future = CompletableFuture<TagType?>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/TagType/$id", null, { res ->
            val tagType = gsonParseApiResponse<Array<TagType>>(res).data
            future.complete(tagType.firstOrNull())
        }, { err ->
            Log.e("GET TagType", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun updateTagType(
        id: Int?, name: String?
    ): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("id", id)
        json.put("name", name)
        val request = AuthRequest(Request.Method.PUT, "$apiUrl/TagType/$id", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("PUT TagType", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun deleteTagType(id: Int): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val request = AuthRequest(Request.Method.DELETE, "$apiUrl/TagType/$id", null, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("DELETE TagType", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getTagTypes(): CompletableFuture<Array<TagType>> {
        val future = CompletableFuture<Array<TagType>>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/TagTypes", null, { res ->
            val tagTypes = gsonParseApiResponse<Array<TagType>>(res).data
            future.complete(tagTypes)
        }, { err ->
            Log.e("GET tagTypes", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun createTag(tagTypeId: Int): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("tagTypeId", tagTypeId)
        val request = AuthRequest(Request.Method.POST, "$apiUrl/Tag", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("POST Tag", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getTag(id: Int): CompletableFuture<Tag?> {
        val future = CompletableFuture<Tag?>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/Tag/$id", null, { res ->
            val tag = gsonParseApiResponse<Array<Tag>>(res).data
            future.complete(tag.firstOrNull())
        }, { err ->
            Log.e("GET Tag", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun updateTag(
        id: Int?, tagTypeId: Int?
    ): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val json = JSONObject()
        json.put("id", id)
        json.put("tagTypeId", tagTypeId)
        val request = AuthRequest(Request.Method.PUT, "$apiUrl/Tag/$id", json, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("PUT Tag", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun deleteTag(id: Int): CompletableFuture<Boolean> {
        val future = CompletableFuture<Boolean>()
        val request = AuthRequest(Request.Method.DELETE, "$apiUrl/Tag/$id", null, { res ->
            val status = gsonParseApiResponse<Unit>(res).status
            future.complete(status == 0)
        }, { err ->
            Log.e("DELETE Tag", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }

    fun getTags(): CompletableFuture<Array<Tag>> {
        val future = CompletableFuture<Array<Tag>>()
        val request = AuthRequest(Request.Method.GET, "$apiUrl/Tags", null, { res ->
            val tags = gsonParseApiResponse<Array<Tag>>(res).data
            future.complete(tags)
        }, { err ->
            Log.e("GET Tags", err.toString())
            future.completeExceptionally(err)
        })
        sendRequest(request)
        return future
    }
}