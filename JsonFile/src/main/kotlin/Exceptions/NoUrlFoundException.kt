package Exceptions

import kotlin.reflect.KClass

class NoUrlFoundException(override val message: String?) : Exception(message) {
}