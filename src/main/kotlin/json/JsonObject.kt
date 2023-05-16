package json

import com.google.gson.GsonBuilder
import java.util.Objects

class JsonObject(private var objects: Any) {
    constructor():this("")

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun toJson(): String {
        return gson.toJson(objects)
    }

    inline fun <reified T> fromJson(string: String): T {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(string, T::class.java)
    }

    inline fun <reified T> fromJson(): T {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(toJson(), T::class.java)
    }

    fun <T> fromJson(clazz: Class<T>): T {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(toJson(), clazz)
    }

    fun <T> fromJson(string: String, clazz: Class<T>): T {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.fromJson(string, clazz)
    }

    override fun toString(): String {
        return toJson()
    }
}