package com.epam.drill.semver

import kotlin.math.*

val REGEX = "v?(\\d+)\\.(\\d+)\\.(\\d+)(-[^\\s]+|)".toRegex()

val UNSPECIFIED = SemVer(major = 0, minor = 0, patch = 0, suffix = "unspecified")

val GIT_DESCRIBE_REGEX = "([^\\s]+)-(\\d+)-g([0-9a-f]{3,})$".toRegex()

fun String.prereleaseFromGit(): SemVer = parseGitDescribe(SemVer::nextPrerelease)

fun String.patchFromGit(): SemVer = parseGitDescribe(SemVer::nextPatch)

fun String.toSemVer(): SemVer = REGEX.matchEntire(this)?.run {
    val (_, major, minor, patch, suffix) = groupValues
    SemVer(
        major = major.toInt(),
        minor = minor.toInt(),
        patch = patch.toInt(),
        suffix = suffix.ifEmpty { "-" }.substring(1)
    )
} ?: UNSPECIFIED

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
        else -> suffix.compareTo(other.suffix)
    }.sign

    fun nexMajor() = copy(major = major + 1, minor = 0, patch = 0, suffix = "")

    fun nextMinor() = copy(minor = minor + 1, patch = 0, suffix = "")

    fun nextPatch() = copy(patch = patch + 1, suffix = "")

    fun nextPrerelease() = when (suffix) {
        "" -> (::nextMinor)().copy(suffix = suffix.nextSuffix())
        else -> copy(suffix = suffix.nextSuffix())
    }

    override fun toString(): String {
        val optSuffix = if (suffix.isNotEmpty()) "-$suffix" else ""
        return "$major.$minor.$patch$optSuffix"
    }
}

private fun String.parseGitDescribe(
    nextVer: (SemVer) -> SemVer
): SemVer = GIT_DESCRIBE_REGEX.matchEntire(this)?.run {
    val (_, ver, num) = groupValues
    val semVer = ver.toSemVer()
    if (num.toInt() > 0) nextVer(semVer) else semVer
} ?: toSemVer()

private fun String.nextSuffix() = "${toIntOrNull()?.inc() ?: 0}"
