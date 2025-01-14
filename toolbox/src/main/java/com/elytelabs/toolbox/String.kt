package com.elytelabs.toolbox

import java.util.Locale


fun String.truncate(maxLength: Int): String {
    return if (length > maxLength) substring(0, maxLength) else this
}

fun String.countOccurrences(substring: String): Int {
    return this.split(substring).size - 1
}

fun String.isEmailValid(): Boolean {
    val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
    return matches(emailRegex)
}

fun String.isNumeric(): Boolean {
    return this.matches("-?\\d+(\\.\\d+)?".toRegex())
}

fun String.removeWhitespace(): String {
    return replace("\\s".toRegex(), "")
}

fun String.toTitleCase(): String {
    return split(" ").joinToString(" ") {
        it.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.ROOT) else char.toString()
        }
    }
}

fun String.camelCaseToUnderscore(): String {
    return replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase(Locale.ROOT)
}

fun String.capitalizeEachWord(): String {
    return split(" ").joinToString(" ") {
        it.replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase(Locale.ROOT) else char.toString()
        }
    }
}

fun String.camelCaseToSnakeCase(): String {
    return replace(Regex("([a-z])([A-Z])"), "$1_$2").lowercase(Locale.ROOT)
}

fun String.maskEmail(): String {
    val parts = split("@")
    return "${parts.first().take(2)}...@${parts.last()}"
}