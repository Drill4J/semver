package com.epam.drill.semver

import kotlin.test.*

class GitDescribeToSemVerTest {
    @Test
    fun simpleVersion() {
        val semVer = "1.2.3".gitDescribeToSemVer()
        assertEquals("1.2.3", semVer.toString())
    }

    @Test
    fun tagVersion_ZeroDistance() {
        val semVer = "v1.2.3-0-g15312a5".gitDescribeToSemVer()
        assertEquals("1.2.3", semVer.toString())
    }

    @Test
    fun tagVersion_NextSuffix() {
        val semVer = "v1.2.3-1-g15312a5".gitDescribeToSemVer()
        assertEquals("1.3.0-0", semVer.toString())
    }

    @Test
    fun tagVersion_WithSuffix_ZeroDistance() {
        val semVer = "v1.2.3-1-1-g15312a5".gitDescribeToSemVer()
        assertEquals("1.2.3-2", semVer.toString())
    }
}
