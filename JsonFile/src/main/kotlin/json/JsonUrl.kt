package json

import Exceptions.NoUrlFoundException
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import utils.Url.Utils.readUrl
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URL


class JsonUrl(private var url: String) {
    companion object {

    }

    private var content = mutableMapOf<String, Any?>()
    private val gson = GsonBuilder().setPrettyPrinting().create()

    constructor(url: URL) : this(url.toString())

    init {
        if(!url.contains("http://")) {
            url = url prefix "http://"
        }

        val string = readUrl(url)
            ?: throw NoUrlFoundException("The specified URL is not valid: \n\t ERROR -> [ $url ]")
        content = gson.fromJson(string, Map::class.java) as MutableMap<String, Any?>
    }

    fun toJsonFileAndCreate(name: String, replace:Boolean = false): JsonFile {
        val file = JsonFile(name)
        if(!replace) {
            if(!file.exists()) file.create(content.toMutableMap() as HashMap<String, Any?>)
        }else file.create(content.toMutableMap() as HashMap<String, Any?>)

        return file
    }

    fun toJsonFileAndCreate(parent:String,name: String, replace:Boolean = false): JsonFile {
        val file = JsonFile(parent, name)
        if(!replace) {
            if(!file.exists()) file.create(content.toMutableMap() as HashMap<String, Any?>)
        }else file.create(content.toMutableMap() as HashMap<String, Any?>)

        return file
    }

    fun toJsonFileAndCreate(name: String): JsonFile {
        val file = JsonFile(name)
        if(!file.exists()) file.create(content.toMutableMap() as HashMap<String, Any?>)

        return file
    }

    fun toJsonFileAndCreate(parent: String, name: String): JsonFile {
        val file = JsonFile(parent, name)
        if(!file.exists()) file.create(content.toMutableMap() as HashMap<String, Any?>)

        return file
    }

    fun getContent(): HashMap<String, Any?> = content.toMutableMap() as HashMap<String, Any?>

    fun toJsonFile(fileName: String): JsonFile {
        val file = JsonFile(fileName)
        file.putAll(content.toMutableMap() as HashMap<String, Any?>)
        return file
    }

    fun toJsonFile(parent: String,fileName: String): JsonFile {
        val file = JsonFile(parent, fileName)
        file.putAll(content.toMutableMap() as HashMap<String, Any?>)
        return file
    }

    /**
     * Sets the specified value to the specified key.
     * @param key the key to set
     * @param value the value to set
     * @return True if the key was successfully set otherwise false
     */
    operator fun set(key: String, value: Any?): Boolean {
        return try {
            content[key] = value
            true
        }catch (e: java.lang.Exception) {
            false
        }

    }

    /**
     * Gets the specified class values from the specified key
     * @param key the key to get the class values from
     * @param classObject the classObject to return the class values from
     * @return The class object values
     */
    fun <T> getObject(key: String, classObject: Class<T>): Any? {
        val jsonString = gson.toJson(content[key], classObject)
        return try {
            gson.fromJson(jsonString, classObject)
        }catch (e: JsonIOException) {
            println("Error while reading class object from file")
            println("Error message: ${e.message}")
            null
        }
    }

    /**
     * Puts all the values into the file
     * @param map the map of values
     */
    fun putAll(map: Map<String, Any?>) {
        map.map { (k,v) ->
            content[k] = v
        }
    }

    /**
     * Removes the specified key
     * @param key the key to remove
     * @return true if the key was removed otherwise false
     **/
    fun deleteKey(key: String): Boolean {
        val first = content.remove(key)
        return first != null
    }

    /**
     * Gets a string value from the specified key
     * @param key the key to retrieve the value from
     * @return the value
     */
    fun getString(key: String): String = content[key].toString()

    /**
     * Gets an integer value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getInt(key: String): Int {
        return try {
            content[key].toString().toInt()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a short value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getShort(key: String): Short {
        return try {
            content[key].toString().toShort()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Big Integer value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getBigInteger(key: String): BigInteger {
        return try {
            content[key].toString().toBigInteger()
        }catch (e: NumberFormatException) {
            (-1).toBigInteger()
        }
    }

    /**
     * Gets a Byte value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getByte(key: String):Byte {
        return try {
            content[key].toString().toByte()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Big Decimal value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getBigDecimal(key: String): BigDecimal {
        return try {
            content[key].toString().toBigDecimal()
        }catch (e: NumberFormatException) {
            (-1).toBigDecimal()
        }
    }

    /**
     * Gets a Long value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getLong(key: String): Long {
        return try {
            content[key].toString().toLong()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Double value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getDouble(key: String): Double {
        return try {
            content[key].toString().toDouble()
        }catch (e: NumberFormatException) {
            -1.0
        }
    }

    /**
     * Checks if the file is empty
     * @return true if the file is empty otherwise false
     */
    fun isEmpty():Boolean {
        return content.isEmpty()
    }

    /**
     * This function empty the file
     *
     * REMEMBER: This function does not check if the file is empty!
     */
    fun empty() {
        content = hashMapOf()
    }

    /**
     * Gets a Boolean value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns false
     */
    fun getBoolean(key: String): Boolean {
        return try {
            content[key].toString().toBoolean()
        }catch (e: Exception) {
            false
        }
    }

    /**
     * Gets the string list from the json file
     * @param key the key to get the string list from
     * @return the string list from the json file
     */
    fun getStringList(key: String): List<String> {
        return try {
            content[key] as List<String>
        }catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Gets the specified list from the specified key.
     * @param key the key to get the list from
     * @return the list from the specified key
     */
    fun <T> getList(key: String): List<T> {
        return try {
            content[key] as List<T>
        }catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Return the contents of this file
     * @return the contents of this file
     */
    override fun toString(): String {
        return content.toString()
    }

    /**
     * Gets a value from the specified key
     * @return Any/Object value from the specified key
     */
    operator fun get(key: String):Any? {
        return try {
            content[key]
        }catch (e: Exception) {
            null
        }
    }

    /**
     * Checks if the specified key is a string
     * @param key the key to check
     * @return true if the specified key is a string
     */
    fun isString(key: String):Boolean {
        return content[key] is String
    }

    /**
     * Checks if the specified key is an integer
     * @param key the key to check
     * @return true if the specified key is an integer
     */
    fun isInt(key: String): Boolean {
        return content[key] is Int
    }

    /**
     * Checks if the specified key is a Double
     * @param key the key to check
     * @return true if the specified key is a Double
     */
    fun isDouble(key: String): Boolean {
        return content[key] is Double
    }

    /**
     * Checks if the specified key is a Boolean
     * @param key the key to check
     * @return true if the specified key is a Boolean
     */
    fun isBoolean(key: String): Boolean {
        return content[key] is Boolean
    }

    /**
     * Checks if the specified key is a List
     * @param key the key to check
     * @return true if the specified key is a List
     */
    fun isList(key: String): Boolean {
        return content[key] is List<*>
    }

    /**
     * Checks if the key is the class specified
     * @param key the key to check
     * @return true if the specified key is the class specified
     */
    fun isInstance(key: String, type: Class<*>): Boolean {
        return type.isInstance(content[key])
    }

    /**
     * Checks if the key isn't null
     * @param key the key to check
     * @return true if the specified key is not null
     */
    fun isNotNull(key:String):Boolean = content[key] != null

    /**
     * Checks if the key is null
     * @param key the key to check
     * @return true if the specified key is null
     */
    fun isNull(key:String):Boolean = content[key] == null
}

private infix fun String.prefix(s: String): String {
    return s + this
}
