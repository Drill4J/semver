package com.epam.drill.semver

import kotlin.test.*

class SemVerTest {
    @Test
    fun toSemVer_Base_SemVer() {
        val semVer = "1.2.3".toSemVer()
        assertEquals(1, semVer.major)
        assertEquals(2, semVer.minor)
        assertEquals(3, semVer.patch)
    }

    @Test
    fun toSemVer_Suffixed_Semver() {
        val semVer = "1.2.3-suffix".toSemVer()
        assertEquals(1, semVer.major)
        assertEquals(2, semVer.minor)
        assertEquals(3, semVer.patch)
        assertEquals("suffix", semVer.suffix)
    }

    @Test
    fun toSemVer_ParseToString() {
        assertEquals("1.2.3", "1.2.3".toSemVer().toString())
        assertEquals("1.2.3-suffix", "1.2.3-suffix".toSemVer().toString())
    }

    @Test
    fun comparing_Base() {
        assertEquals(0, compareValues("1.2.3".toSemVer(), "1.2.3".toSemVer()))
        assertTrue {  "1.2.3".toSemVer() > "1.2.2".toSemVer() }
        assertTrue {  "1.2.11".toSemVer() > "1.2.2".toSemVer() }
        assertTrue {  "2.0.0".toSemVer() > "1.99.99".toSemVer() }
    }

    @Test
    fun comparing_WithSuffixes() {
        assertEquals("1.2.3", "1.2.3".toSemVer().toString())
        assertEquals("1.2.3-suffix", "1.2.3-suffix".toSemVer().toString())
    }
}
