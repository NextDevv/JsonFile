package json

import com.google.gson.GsonBuilder

class JsonBuilder {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun prettify(value: String):String {
        val j = gson.fromJson(value, Map::class.java)
        return gson.toJson(j)
    }

    /**
     * Converts a map to JSON representation
     *
     * @param value the map to convert
     * @return the JSON representation
     */
    fun toJson(value: Map<*, *>): String {
        return gson.toJson(value)
    }

    @JvmName("fromJson2")
    /**
     * Returns a map representation of the JSON values
     *
     * @param value the JSON values
     * @return a map representation of the JSON values
     */
    fun <K, V> fromJson(value: String): Map<K, V> {
        val map = gson.fromJson(value, Map::class.java).toMutableMap()
        map.forEach { (k,v) ->
            if(v is Double) {
                val str = v.toString().split(".")
                if(str[1] == "0")
                    map[k] = v.toInt()
            }
        }

        return map as MutableMap<K, V>
    }

    /**
     * Returns a map representation of the JSON values
     *
     * @param value the JSON values
     * @return a map representation of the JSON values
     */
    fun fromJson(value: String): Map<*, *> {
        val map = gson.fromJson(value, Map::class.java).toMutableMap()
        map.forEach { (k,v) ->
            if(v is Double) {
                val str = v.toString().split(".")
                if(str[1] == "0"){
                    map[k] = v.toInt()
                }
            }
        }
        return map
    }

    /**
     * Filters the JSON values by the specified type
     *
     * @param T the type of the JSON values
     * @param value the JSON values
     * @return a map representation of the JSON values
     */
    inline fun <reified T> filter(value: String): Map<String, T> {
        val map = fromJson(value)
        val filtered = mutableMapOf<String, T>()
        map.forEach { (k,v) ->
            if(v is T) {
                filtered[k.toString()] = v
            }
        }
        return filtered
    }
}