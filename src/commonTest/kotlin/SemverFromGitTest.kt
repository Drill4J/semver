package com.epam.drill.semver

import kotlin.test.*

class SemverFromGitTest {
    @Test
    fun prereleaseFromGit_NoAbbrev() {
        val semVer = "1.2.0".prereleaseFromGit()
        assertEquals("1.2.0", "$semVer")
        val semVerSuffixed = "1.2.0-1".prereleaseFromGit()
        assertEquals("1.2.0-1", "$semVerSuffixed")
    }

    @Test
    fun patchFromGit_NoAbbrev() {
        val semVer = "1.2.3".patchFromGit()
        assertEquals("1.2.3", "$semVer")
    }

    @Test
    fun prereleaseFromGit_ZeroDistance() {
        val semVer = "v1.2.0-0-g15312a5".prereleaseFromGit()
        assertEquals("1.2.0", "$semVer")
    }

    @Test
    fun patchFromGit_ZeroDistance() {
        val semVer = "1.2.3-0-g15312a5".patchFromGit()
        assertEquals("1.2.3", "$semVer")
    }

    @Test
    fun prereleaseFromGit_Increment() {
        val semVer = "v1.2.0-1-g15312a5".prereleaseFromGit()
        assertEquals("1.3.0-0", "$semVer")
    }

    @Test
    fun prereleaseFromGit_WithSuffix_ZeroDistance() {
        val semVer = "v1.2.0-1-0-g15312a5".prereleaseFromGit()
        assertEquals("1.2.0-1", "$semVer")
    }

    @Test
    fun patchFromGit_Increment() {
        val semVer = "1.2.3-1-g15312a5".patchFromGit()
        assertEquals("1.2.4", "$semVer")
    }
}
