package com.epam.drill.semver

import kotlin.math.*

val REGEX = "v?(\\d+)\\.(\\d+)\\.(\\d+)(-[^\\s]+|)".toRegex()

val UNSPECIFIED = SemVer(major = 0, minor = 0, patch = 0, suffix = "unspecified")

data class SemVer(
    val major: Int,
    val minor: Int,
    val patch: Int,
    val suffix: String
) : Comparable<SemVer> {
    override fun compareTo(other: SemVer) = when {
        major != other.major -> (major - other.major)
        minor != other.minor -> minor - other.minor
        patch != other.patch -> patch - other.patch
        else -> nullsFirst<String>().compare(suffix, other.suffix)
    }.sign

    fun bumpMinor() = copy(minor = minor + 1, patch = 0, suffix = nextSuffix())

    fun bump() = when (suffix) {
        "" -> bumpMinor()
        else -> copy(suffix = nextSuffix())
    }

    override fun toString(): String {
        val optSuffix = if (suffix.isNotEmpty()) "-$suffix" else ""
        return "$major.$minor.$patch$optSuffix"
    }
}

fun String.toSemVer(): SemVer = REGEX.matchEntire(this)?.run {
    val (_, major, minor, patch, suffix) = groupValues
    SemVer(
        major = major.toInt(),
        minor = minor.toInt(),
        patch = patch.toInt(),
        suffix = suffix.ifEmpty { "-" }.substring(1)
    )
} ?: UNSPECIFIED

private fun SemVer.nextSuffix() = "${suffix.toIntOrNull()?.inc() ?: 0}"
