package utils

import Exceptions.NoUrlFoundException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class Url {
    companion object Utils {
        fun readUrl(url: String): String? {
            var reader: BufferedReader? = null
            return try {
                val u = URL(url)
                reader = BufferedReader(InputStreamReader(u.openStream()))
                val buffer = StringBuffer()
                var read: Int
                val chars = CharArray(1024)
                while (reader.read(chars).also { read = it } != -1) buffer.append(chars, 0, read)
                buffer.toString()
            }catch (e: Exception) {
                throw NoUrlFoundException("The specified URL is not valid: \n\t ERROR -> [ $url ]")
            } finally {
                reader?.close()
            }
        }
    }
}