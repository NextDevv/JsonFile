package json
import com.fasterxml.jackson.databind.JsonMappingException
import yaml.YamlFile
import com.google.gson.GsonBuilder
import int
import java.io.*
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import kotlin.io.path.Path

@Suppress("unused", "MemberVisibilityCanBePrivate", "DuplicatedCode", "UNCHECKED_CAST")
/**
 * Created by NextDev on 07/11/2022
 *
 * An easy way to create and access JSON files
 *
 * @see JsonUrl
 * @see JsonString
 * @author NextDev
 */
class JsonFile() {

    companion object Methods{
        private val files = hashMapOf<String, JsonFile>()

        operator fun get(name: String): JsonFile? {
            return files[name]
        }

        /**
         * Gets all the files in the cache that are created or loaded
         * @return Return a list of all the files in the cache that are created or loaded
         */
        fun getAllFiles(): List<JsonFile> {
            return files.values.toList()
        }
    }

    private var name = ""
    private var path = ""
    private var parent = ""
    private var f = System.getProperty("file.separator")
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private var content = mutableMapOf<String, Any?>()

    fun getParent():String {
        return parent
    }

    @JvmName("getContent2")
    fun getContent():MutableMap<String, Any?> = content

    /**
     * Gets the file
     */
    fun getFile():File? {
        return try {
            File(path)
        }catch (e: NullPointerException) {
            null
        }
    }

    init {
        files[name] = this
        if(exists()) {
            load {

            }
            save()
            println(content)
        }
    }

    constructor(name: String) : this() {
        val file = File(name)
        this@JsonFile.name = file.name
        this@JsonFile.path = file.path
        this@JsonFile.parent = file.parent
    }

    /**
     * Creates a json.json with the given name and specified parent path.
     */
    constructor(parent: String, name: String) : this(name) {
        if(name.endsWith(".json"))
            this@JsonFile.name = name
        else this@JsonFile.name = "${name}.json"

        this@JsonFile.path = "$parent$f${this@JsonFile.name}"
        this@JsonFile.parent = parent

        try {
            if(!Files.exists(Path(parent)))
                Files.createDirectories(Path(parent))
        }catch (e: Exception) {
            println("[json] Something went wrong!")
            println("[json] Error message: ${e.message}")
        }

        if(exists()) reload()
    }

    constructor(file: File) : this(file.path) {
        this@JsonFile.path = file.name
        this@JsonFile.parent = file.parent

        val dirs = File(this@JsonFile.parent)
        if(!dirs.exists())
            dirs.mkdirs()
    }

    /**
     * Saves the contents of the file
     */
    fun save():JsonFile {
        val writer = Files.newBufferedWriter(Path(path), StandardCharsets.UTF_8)
        val json = GsonBuilder().setPrettyPrinting().create().toJson(content)
        writer.write(json)
        writer.flush()
        writer.close()
        return this@JsonFile
    }

    /**
     * Sets the json parent folder
     *
     * @param parent path to the parent
     */
    fun setParent(parent: String) {
        this@JsonFile.parent = parent
        this@JsonFile.path = "$parent${f}$name"
    }

    /**
     * Reloads the content and the JSON file
     */
    fun reload():JsonFile {
        load {}
        save()
        return this@JsonFile
    }

    fun run(function: (JsonFile) -> Unit?):JsonFile {
        function(this@JsonFile)
        return this@JsonFile
    }

    /**
     * Gets the JSON file name
     * @return the JSON file name
     */
    fun getName() = name

    fun load(function: (JsonFile) -> Unit):JsonFile {
        val reader = Files.newBufferedReader(Path(path))
        val map:Map<*, *> = gson.fromJson(reader, Map::class.java)

        content = (map as Map<String, Any?>).toMutableMap()
        function(this)
        return this@JsonFile
    }

    /**
     * Returns the absolute path of the file
     * @return the absolute path of the file
     */
    fun getAbsolutePath(): String {
        return try {
            File(path).absolutePath
        }catch (e:NullPointerException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Gets the path of the JSON file
     * @return the path of the JSON file
     */
    fun getPath(): String {
        return try {
            File(path).path
        }catch (e: NullPointerException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Creates a new JSON file with the given name and path, if there's already a file with the
     * same name, the function will override it
     * @param content The default content that will be written to the file
     * @return Return true if the file was successfully created, false otherwise
     */
    fun create(content: HashMap<String, Any?> = HashMap()): Boolean {
        try {
            if(!Files.exists(Path(parent)))
                Files.createDirectories(Path(parent))
        }catch (e: Exception) {
            println("[json] Something went wrong!")
            println("[json] Error message: ${e.message}")
        }

        val file = File(path)
        if(!file.exists()) file.createNewFile()

        try {
            PrintWriter(FileWriter(file)).use {
                val jsonString = gson.toJson(content) ?: return false
                it.write(jsonString)
            }

            load {

            }
            save()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * Creates a new JSON file with the given name and path, if there's already a file with the
     * same name, the function will override it
     * @return Return true if the file was successfully created, false otherwise
     */
    fun create(): Boolean {
        try {
            if(!Files.exists(Path(parent)))
                Files.createDirectories(Path(parent))
        }catch (e: Exception) {
            println("[json] Something went wrong!")
            println("[json] Error message: ${e.message}")
        }

        val file = File(path)
        if(!file.exists()) file.createNewFile()

        try {
            PrintWriter(FileWriter(file)).use {
                val jsonString = gson.toJson(mapOf<String, Any?>()) ?: return false
                it.write(jsonString)
            }

            load {

            }
            save()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
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
     * Sets the value of the given key as the JsonObject
     *
     * @param jsonObject The JsonObject to set
     */
    fun setObject(key:String, jsonObject: JsonObject) {
        val string = jsonObject.toJson()
        val map = JsonBuilder().fromJson(string)
        content[key] = map
    }

    /**
     * Gets the specified class values from the specified key
     * @param key the key to get the class values from
     * @param clazz the classObject to return the class values from
     * @return The class object values
     */
    fun <T> getObject(key: String, clazz: Class<T>): T? {
        val jsonString = gson.toJson(content[key])
        return try {
            gson.fromJson(jsonString, clazz)
        }catch (e: Exception) {
            println("[json] Error while reading class object from file $name")
            println("[json] Error message: ${e.message}")
            null
        }
    }

    fun getObject(key: String): JsonObject {
        return try {
            JsonObject(content[key] as Map<*,*>)
        }catch (e: Exception) {
            JsonObject(JsonNull.getNull())
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
     * Puts all the values into the file
     * @param map the map of values
     */
    fun addAll(map: Map<String, Any?>) {
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
            content[key].int()
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
            Integer.parseInt(content[key] as String).toShort()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Big Integer value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getBigInteger(key: String):BigInteger {
        return try {
            Integer.parseInt(content[key] as String).toBigInteger()
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
            Integer.parseInt(content[key] as String).toByte()
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
            Integer.parseInt(content[key] as String).toBigDecimal()
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
            Integer.parseInt(content[key] as String).toLong()
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
            Integer.parseInt(content[key] as String).toDouble()
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
     * Check if the key exists in the json file
     * @param key the key to check
     */
    fun hasKey(key: String): Boolean {
        return content.containsKey(key)
    }

    /**
     * Checks if the JSON file exists
     * @return true if the file exists otherwise false
     */
    fun exists():Boolean {
        return try {
            File(this.path).exists()
        }catch (e: NullPointerException) {
            false
        }
    }

    /**
     * Return the contents of this file
     * @return the contents of this file
     */
    override fun toString(): String {
        return gson.toJson(content)
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
     * Remove key from the JSON file
     * @param key the key to remove
     */
    operator fun minus(key: String): Any? {
        return content.remove(key)
    }

    /**
     * Compare two JSON files
     */
    override operator fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(!other::class.java.isAssignableFrom(this.javaClass)) return false
        val otherFile = other as JsonFile
        if(path!= otherFile.path) return false
        return true
    }

    /**
     * Check if the key exists in the JSON file
     * @param key the key to check
     */
    operator fun contains(key: String): Boolean {
        return content.containsKey(key)
    }

    /**
     * Gets a range of keys from the JSON file
     */
    operator fun rangeTo(range: IntRange):HashMap<String, Any?> {
        val map = HashMap<String, Any?>()
        val list = content.toList()
        range.forEach { i ->
            map[list[i].first] = list[i].second
        }
        return map
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
     * Gets from a specified key a map
     * @param key the key to get the map from
     */
    fun getMap(key: String): Map<String, Any> {
        return try {
            content[key] as Map<String, Any>
        }catch (e: Exception) {
            emptyMap()
        }
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

    /**
     * Deletes the JSON file
     */
    fun delete():Boolean {
        val file = (try { File(path) }catch (e:java.lang.Exception) {null}) ?: return false
        files.remove(file.nameWithoutExtension)
        return file.delete()
    }

    /**
     * Converts the JsonFile to a JsonString
     *
     * @return The json string
     */
    fun jsonString():JsonString {
        val json = gson.toJson(content)
        return JsonString(json)
    }

    /**
     * Transforms the JSON file to a [YamlFile] without any errors
     *
     * @param name The name of the file you want to give
     * @param save If true, saves the file
     * @param replace If true, replaces the file
     *
     * @return The [YamlFile]
     */
    fun toYamlFile(name:String = this.name, save:Boolean = true, replace:Boolean = true): YamlFile {
        val yaml = YamlFile(parent, name.replace(".json", ""))
        if(yaml.exists() && !replace)
            throw FileAlreadyExistsException(path)
        else if(yaml.exists() && replace) {
            yaml.delete()
            yaml.create(this.content)
        }

        if(!replace) yaml.create(this.content)

        if(save)
            yaml.save()
        return yaml
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + parent.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }

    fun load(): JsonFile {
        load {  }
        return this@JsonFile
    }
}