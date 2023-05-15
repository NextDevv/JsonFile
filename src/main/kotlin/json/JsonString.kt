package json

import com.google.gson.GsonBuilder

/**
 * Converts Json Strings to JsonFile
 *
 * @author Next
 */
class JsonString(private var string: String) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    constructor():this("")

    fun setString(value: String) {
        this.string = value
    }

    fun pretty():JsonString {
        val j = gson.fromJson(string, Map::class.java)
        return JsonString(gson.toJson(j))
    }

    fun prettyStr():String {
        val j = gson.fromJson(string, Map::class.java)
        return gson.toJson(j)
    }

    fun pretty(string: String):JsonString {
        val j = gson.fromJson(string, Map::class.java)
        return JsonString(gson.toJson(j))
    }

    fun prettyStr(string: String):String {
        val j = gson.fromJson(string, Map::class.java)
        return gson.toJson(j)
    }

    fun convert():JsonFile {
        val json = JsonFile()
        val map = gson.fromJson(string, Map::class.java)
        json.putAll(map as Map<String, Any?>)

        return json
    }

    fun convert(string: String):JsonFile {
        val json = JsonFile()
        val map = gson.fromJson(string, Map::class.java)
        json.putAll(map as Map<String, Any?>)

        return json
    }

    override fun toString():String = string
}